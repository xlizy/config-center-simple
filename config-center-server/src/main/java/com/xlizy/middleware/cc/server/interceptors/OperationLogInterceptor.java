package com.xlizy.middleware.cc.server.interceptors;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.entity.CcOperation;
import com.xlizy.middleware.cc.server.mapper.CcOperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 记录操作日志的AOP
 * @author xlizy
 * @date 2018/3/26
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class OperationLogInterceptor {

    @Autowired
    CcOperationMapper ccOperationMapper;

    @Around("@annotation(com.xlizy.middleware.cc.server.annotations.OperationLog) && @annotation(operationLog)")
    public Object log(ProceedingJoinPoint pjp, OperationLog operationLog) throws Throwable {
        CcOperation operation;
        Integer logId = null;
        try {
            operation = new CcOperation();
            operation.setBizType(operationLog.bizType());
            operation.setStartTime(new Date());
            if(StringUtils.isEmpty(operationLog.remark())){
                operation.setRemark(operationLog.bizType().getDes());
            }else{
                operation.setRemark(operationLog.remark());
            }
            operation.setTraceId(MDC.get(CoreUtil.TRACE_ID));
            operation.setIp(MDC.get(CoreUtil.REQUEST_IP));
            ccOperationMapper.insertSelective(operation);
            logId = operation.getId();
        } catch (Exception e) {
            log.error("操作日志记录失败,msg:{}",e.getMessage());
            e.printStackTrace();
        }
        Object obj = pjp.proceed();
        try {
            CcOperation uLog = new CcOperation();
            uLog.setId(logId);
            uLog.setEndTime(new Date());
            ccOperationMapper.updateByPrimaryKeySelective(uLog);
        } catch (Exception e) {
            log.error("操作日志记录失败,msg:{}",e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

}
