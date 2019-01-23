package com.xlizy.middleware.cc.server.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * CMS平台需要的过滤器,设置一些会话参数
 * @author xlizy
 * @date 2018/3/13
 */
@Configuration
public class CoreFilter {

    /** 本地、开发环境标志 */
    private static final Collection MINI_ACTIVE = new ArrayList<String>(){{add("local");add("dev");}};

    /**扫描路径*/
    private static final Collection URL_PATTERNS = new ArrayList<String>(){{add("/*");}};

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Bean
    public FilterRegistrationBean commonParameters(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new javax.servlet.Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) {}

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                if(servletResponse instanceof HttpServletResponse){
                    HttpServletResponse response = (HttpServletResponse)servletResponse;
                    response.setHeader("Access-Control-Allow-Origin","*");
                }
                if(servletRequest instanceof HttpServletRequest){
                    HttpServletRequest request = (HttpServletRequest) servletRequest;
                    servletRequest.setAttribute("CP_CTXPATH",request.getContextPath());
                    servletRequest.setAttribute("CP_CURRENTDATETIME",System.currentTimeMillis());
                    //如果不是开发或本地环境，则静态资源后面追加.min后缀
                    servletRequest.setAttribute("CP_SUFFIX",(!MINI_ACTIVE.contains(springProfilesActive) ? ".min" : ""));


                }
                filterChain.doFilter(servletRequest,servletResponse);
            }

            @Override
            public void destroy() {}
        });
        registrationBean.setOrder(16);
        registrationBean.setUrlPatterns(URL_PATTERNS);
        return registrationBean;
    }
}
