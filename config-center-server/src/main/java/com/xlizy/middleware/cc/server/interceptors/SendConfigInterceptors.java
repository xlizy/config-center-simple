package com.xlizy.middleware.cc.server.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.annotations.SendConfig;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.NettyServerService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AOP拦截-针对SendConfig
 * @author xlizy
 * @date 2018/5/28
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class SendConfigInterceptors {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    EnvService envService;

    @Autowired
    AppService appService;

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    NettyServerService nettyServerService;

    /** SPEL解析器 */
    private static ExpressionParser parser = new SpelExpressionParser();

    /**
     * 对用SendConfig注解修饰的方法进行拦截
     * */
    @Around("@annotation(com.xlizy.middleware.cc.server.annotations.SendConfig) && @annotation(sendConfig)")
    public Object sendConfig(ProceedingJoinPoint pjp, SendConfig sendConfig) throws Throwable {
        //获得参数值列表
        Object[] arguments = pjp.getArgs();
        //获得方法名
        String methodName = pjp.getSignature().getName();
        //获得类名
        String targetName = pjp.getTarget().getClass().getName();

        //调用业务方法
        Object obj = pjp.proceed();

        //捕捉异常防止对业务造成影响
        try {
            JSONObject object = new JSONObject();

            log.info("触发" + targetName + "." + methodName);
            String[] paramNames = ParamNameMap.get(targetName+"+"+methodName);
            log.info("形参:{}",JSON.toJSONString(paramNames));
            if (paramNames == null){
                //反射得到形参名称
                paramNames = ReflectParamNames.getNames(targetName, methodName);
                ParamNameMap.put(targetName+"+"+methodName, paramNames);
                log.info("通过反射获得形参:{}",JSON.toJSONString(paramNames));
            }

            if(sendConfig.appId().length() > 0){
                log.info("appId不是空,appId:{}",sendConfig.envId());
                //spring EL 表达式
                String app = getKey(sendConfig.appId(), paramNames, arguments);
                log.info("spel解析结果:{}",app);
                if(StringUtils.isNotEmpty(app)){
                    CcApp ccApp = appService.getApp(Integer.parseInt(app));
                    log.info("ccApp,{}",ccApp.getId());
                    List<CcEnv> envList = envService.getEnvsByAppId(ccApp.getId());
                    log.info("envList,{}",envList);
                    envList.forEach(ccEnv -> {
                        object.put("app",ccApp.getName());
                        object.put("env",ccEnv.getEnv());
                        object.put("version",ccEnv.getVersion());
                        object.put("cluster",ccEnv.getCluster());
                        object.put("sendConfigType",SendConfigType.EDIT.name());
                        kafkaTemplate.send("config-center_send-config",object.toJSONString());
                        //nettyServerService.sendConfig(ccApp.getName(),ccEnv.getEnv(),ccEnv.getVersion(),ccEnv.getCluster());
                    });
                }

            }else if(sendConfig.envId().length() > 0){
                log.info("envId不是空,envId:{}",sendConfig.envId());
                //spring EL 表达式
                String env = getKey(sendConfig.envId(), paramNames, arguments);
                log.info("spel解析结果:{}",env);
                if(StringUtils.isNotEmpty(env)){
                    CcEnv ccEnv = envService.getEnv(Integer.parseInt(env));
                    log.info("ccEnv,{}",ccEnv.getId());
                    CcApp ccApp = appService.getApp(ccEnv.getAppId());
                    log.info("ccApp,{}",ccApp.getId());

                    object.put("app",ccApp.getName());
                    object.put("env",ccEnv.getEnv());
                    object.put("version",ccEnv.getVersion());
                    object.put("cluster",ccEnv.getCluster());
                    object.put("sendConfigType",SendConfigType.EDIT.name());
                    kafkaTemplate.send("config-center_send-config",object.toJSONString());
                    //nettyServerService.sendConfig(ccApp.getName(),ccEnv.getEnv(),ccEnv.getVersion(),ccEnv.getCluster());
                }
            }else if(sendConfig.prodId().length() > 0){
                log.info("prodId不是空,prodId:{}",sendConfig.prodId());
                //spring EL 表达式
                String prop = getKey(sendConfig.prodId(), paramNames, arguments);
                log.info("spel解析结果:{}",prop);
                if(StringUtils.isNotEmpty(prop)){
                    CcProperties ccProperties = propertiesService.getProperties(Integer.parseInt(prop));
                    log.info("ccProperties,{}",ccProperties.getId());
                    CcEnv ccEnv = envService.getEnv(ccProperties.getEnvId());
                    log.info("ccEnv,{}",ccEnv.getId());
                    CcApp ccApp = appService.getApp(ccEnv.getAppId());
                    log.info("ccApp,{}",ccApp.getId());

                    object.put("app",ccApp.getName());
                    object.put("env",ccEnv.getEnv());
                    object.put("version",ccEnv.getVersion());
                    object.put("cluster",ccEnv.getCluster());
                    object.put("sendConfigType",SendConfigType.EDIT.name());
                    kafkaTemplate.send("config-center_send-config",object.toJSONString());
                    //nettyServerService.sendConfig(ccApp.getName(),ccEnv.getEnv(),ccEnv.getVersion(),ccEnv.getCluster());
                }
            }
        } catch (Exception e) {
            log.error("切面处理发生错误,errMsg:{}",e.getMessage());
        }

        return obj;
    }

    /**
     * 解析spel表达式,返回真实的结果
     * */
    public static String getKey(String key,String[] paramNames, Object[] arguments) {
        try {
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();
            int length = paramNames.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    context.setVariable(paramNames[i], arguments[i]);
                }
            }
            return expression.getValue(context, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 为了获得方法形参名字列表
     * */
    @Slf4j
    static class ReflectParamNames {
        private  static ClassPool pool = ClassPool.getDefault();

        static{
            ClassClassPath classPath = new ClassClassPath(ReflectParamNames.class);
            pool.insertClassPath(classPath);
        }

        public static String[] getNames(String className,String methodName) {
            CtClass cc = null;
            try {
                cc = pool.get(className);
                CtMethod cm = cc.getDeclaredMethod(methodName);
                // 使用javaassist的反射方法获取方法的参数名
                MethodInfo methodInfo = cm.getMethodInfo();
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                if (attr == null){
                    return new String[0];
                }

                int begin = 0;

                String[] paramNames = new String[cm.getParameterTypes().length];
                int count = 0;
                int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;

                for (int i = 0; i < attr.tableLength(); i++){
                    //  为什么 加这个判断，发现在windows 跟linux执行时，参数顺序不一致，通过观察，实际的参数是从this后面开始的
                    if (attr.variableName(i).equals("this")){
                        begin = i;
                        break;
                    }
                }

                for (int i = begin+1; i <= begin+paramNames.length; i++){
                    paramNames[count] = attr.variableName(i);
                    count++;
                }
                return paramNames;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    if(cc != null) {
                        cc.detach();
                    }
                } catch (Exception e2) {
                    log.error(e2.getMessage());
                }


            }
            return new String[0];
        }
    }

    static class ParamNameMap {
        private static ConcurrentHashMap<String, String[]> map = new ConcurrentHashMap<>();

        public static void put(String key, String[] paramNames) {
            map.putIfAbsent(key, paramNames);
        }

        public static String[] get(String key) {
            return map.get(key);
        }

    }



}
