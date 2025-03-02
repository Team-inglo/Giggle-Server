package com.inglo.giggle.security.config;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.filter.ExceptionFilter;
import com.inglo.giggle.security.filter.GlobalLoggerFilter;
import com.inglo.giggle.security.filter.JsonWebTokenAuthenticationFilter;
import com.inglo.giggle.security.handler.common.DefaultAccessDeniedHandler;
import com.inglo.giggle.security.handler.common.DefaultAuthenticationEntryPoint;
import com.inglo.giggle.security.handler.login.DefaultLoginFailureHandler;
import com.inglo.giggle.security.handler.login.DefaultLoginSuccessHandler;
import com.inglo.giggle.security.handler.logout.DefaultLogoutProcessHandler;
import com.inglo.giggle.security.handler.logout.DefaultLogoutSuccessHandler;
import com.inglo.giggle.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultLoginSuccessHandler defaultLoginSuccessHandler;
    private final DefaultLoginFailureHandler defaultLoginFailureHandler;

    private final DefaultLogoutProcessHandler defaultLogoutProcessHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(configurer -> configurer

                        // 인증이 필요없는 URL
                        .requestMatchers(
                                Constants.REGEX_NO_NEED_AUTH_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).permitAll()

                        // USER 권한이 필요한 URL
                        .requestMatchers(
                                Constants.REGEX_USER_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).hasAnyRole(ESecurityRole.USER.toString())

                        // OWNER 권한이 필요한 URL
                        .requestMatchers(
                                Constants.REGEX_OWNER_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).hasAnyRole(ESecurityRole.OWNER.toString())

                        // ADMIN 권한이 필요한 URL
                        .requestMatchers(
                                Constants.REGEX_ADMIN_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).hasAnyRole(ESecurityRole.ADMIN.toString())

                        // USER, OWNER 권한이 필요한 URL
                        .requestMatchers(
                                Constants.REGEX_USER_OWNER_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).hasAnyRole(ESecurityRole.USER.toString(), ESecurityRole.OWNER.toString())

                        // USER, OWNER, ADMIN 권한이 필요한 URL
                        .requestMatchers(
                                Constants.REGEX_USER_OWNER_ADMIN_URLS
                                        .stream()
                                        .map(pattern -> new RegexRequestMatcher(pattern, null))
                                        .toArray(RegexRequestMatcher[]::new)
                        ).hasAnyRole(ESecurityRole.USER.toString(), ESecurityRole.OWNER.toString(), ESecurityRole.ADMIN.toString())

                        // 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/v1/auth/login")
                        .usernameParameter("serial_id")
                        .passwordParameter("password")
                        .successHandler(defaultLoginSuccessHandler)
                        .failureHandler(defaultLoginFailureHandler)
                )

                .logout(configurer -> configurer
                        .logoutUrl("/v1/auth/logout")
                        .addLogoutHandler(defaultLogoutProcessHandler)
                        .logoutSuccessHandler(defaultLogoutSuccessHandler)
                )

                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                )

                .addFilterBefore(
                        new JsonWebTokenAuthenticationFilter(
                                authenticateJsonWebTokenUseCase,
                                jsonWebTokenUtil
                        ),
                        LogoutFilter.class
                )

                .addFilterBefore(
                        new ExceptionFilter(),
                        JsonWebTokenAuthenticationFilter.class
                )

                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        ExceptionFilter.class
                )

                .getOrBuild();
    }
}

