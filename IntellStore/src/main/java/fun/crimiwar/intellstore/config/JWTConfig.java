package fun.crimiwar.intellstore.config;

import fun.crimiwar.intellstore.security.JWT.JWTInterceptor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JWTConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        校验jwt
        registry.addInterceptor(new JWTInterceptor())
                //添加拦截路径
                .addPathPatterns("/**")//所有
                //添加放行路径
                .excludePathPatterns("/**/login")
                .excludePathPatterns("/**/sign")//登录注册
                ;
    }

}
