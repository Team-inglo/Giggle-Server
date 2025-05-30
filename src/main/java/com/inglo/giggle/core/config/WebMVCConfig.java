package com.inglo.giggle.core.config;

import com.inglo.giggle.core.interceptor.pre.HttpAccountIDInterceptor;
import com.inglo.giggle.core.resolver.HttpAccountIDArgumentResolver;
import com.inglo.giggle.core.resolver.HttpRoleArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {

    private final HttpAccountIDInterceptor httpAccountIDInterceptor;
    private final HttpAccountIDArgumentResolver httpAccountIDArgumentResolver;
    private final HttpRoleArgumentResolver httpRoleArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(httpAccountIDArgumentResolver);
        resolvers.add(httpRoleArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(httpAccountIDInterceptor)
                .addPathPatterns("/**");
    }
}
