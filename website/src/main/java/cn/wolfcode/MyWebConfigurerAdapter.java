package cn.wolfcode;

import cn.wolfcode.p2p.base.web.interceptor.LoginInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置mvc登录拦截器
 *
 */
@Component
public class MyWebConfigurerAdapter extends WebMvcConfigurerAdapter {

    //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
