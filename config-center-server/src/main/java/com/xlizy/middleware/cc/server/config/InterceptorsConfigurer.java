package com.xlizy.middleware.cc.server.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.common.utils.IdGenerateUtil;
import com.xlizy.middleware.cc.server.common.utils.RequestUtils;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * SpringMVC拦截器
 * @author xlizy
 * @date 2018/6/7
 */
@Configuration
@Slf4j
public class InterceptorsConfigurer implements WebMvcConfigurer {

    /**扫描路径*/
    private static final String URL_PATTERNS = "/**" ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            //在请求处理之前进行调用（Controller方法调用之前）
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                //如果 (handler instanceof HandlerMethod) 为true 表示调用的是controller里定义的接口
                if(handler instanceof HandlerMethod){
                    String ipAddr = RequestUtils.getIpAddr(request);
                    MDC.put(CoreUtil.TRACE_ID, IdGenerateUtil.getUUID());
                    MDC.put(CoreUtil.REQUEST_IP, ipAddr);
                    JSONObject requestInfo = new JSONObject();
                    requestInfo.put("Accept",request.getHeader("Accept"));
                    requestInfo.put("Accept-Encoding",request.getHeader("Accept-Encoding"));
                    requestInfo.put("Accept-Language",request.getHeader("Accept-Language"));
                    requestInfo.put("Connection",request.getHeader("Connection"));
                    requestInfo.put("Cache-Control",request.getHeader("Cache-Control"));
                    requestInfo.put("Content-Length",request.getHeader("Content-Length"));
                    requestInfo.put("Content-Type",request.getHeader("Content-Type"));
                    requestInfo.put("Cookie",request.getHeader("Cookie"));
                    requestInfo.put("Host",request.getHeader("Host"));
                    requestInfo.put("Origin",request.getHeader("Origin"));
                    requestInfo.put("Referer",request.getHeader("Referer"));
                    requestInfo.put("User-Agent",request.getHeader("User-Agent"));
                    requestInfo.put("X-Requested-With",request.getHeader("X-Requested-With"));
                    requestInfo.put("Request URL",request.getRequestURL());
                    requestInfo.put("Request URI",request.getRequestURI());
                    requestInfo.put("Request Method",request.getMethod());
                    requestInfo.put("Request RealIP",ipAddr);

                    Map<String,String[]> param = request.getParameterMap();
                    if(param != null){
                        Set<String> ks = param.keySet();
                        if(ks.size() > 0){
                            JSONArray jps = new JSONArray();
                            for (String k : ks) {
                                JSONObject p = new JSONObject();
                                p.put(k,param.get(k));
                                jps.add(p);
                            }
                            requestInfo.put("Form Data",jps);
                        }

                    }
                    log.info("requestInfo:{}",requestInfo.toJSONString());
                }
                return true;
            }

            //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
            @Override
            public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
                MDC.clear();
            }

            //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
            @Override
            public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

            }
        }).addPathPatterns(URL_PATTERNS);

    }
}
