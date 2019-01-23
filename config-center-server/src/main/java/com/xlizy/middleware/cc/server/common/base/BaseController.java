package com.xlizy.middleware.cc.server.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author xlizy
 * @date 2018/5/25
 */
@Component
@Slf4j
public abstract class BaseController {

    /**
     * 统一错误处理
     *
     * ajax请求返回json信息
     * 普通同步请求跳转至错误页
     * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(HttpServletRequest request, HttpServletResponse response, final Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        ModelAndView mv = new ModelAndView("error/error");
        if (ex != null) {
            try {
                if(!(ex instanceof MethodArgumentNotValidException)){
                    ex.printStackTrace();
                }
                log.error("系统处理业务发生异常:{}",ex.getMessage());
                response.setContentType("application/json;charset=UTF-8");
                if(ex instanceof MethodArgumentNotValidException){
                    MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
                    List<ObjectError> errors = c.getBindingResult().getAllErrors();
                    JSONArray validErrors = new JSONArray();
                    errors.stream().forEach(x -> validErrors.add(x.getDefaultMessage()));
                    response.getWriter().write(JSON.toJSONString(Results.failure().code(-1100).msg(errors.get(0).getDefaultMessage()).errorMsgDetails(sw.toString())));
                }else{
                    response.getWriter().write(JSON.toJSONString(Results.failure().msg("系统处理错误,请联系管理员").errorMsgDetails(sw.toString())));
                }
                return null;
            } catch (IOException e1) {
                StringWriter _sw = new StringWriter();
                PrintWriter _pw = new PrintWriter(sw);
                e1.printStackTrace(_pw);
                log.error("Controller统一错误处理器报错:"+_sw.toString());
            }
        }
        return mv;
    }
}
