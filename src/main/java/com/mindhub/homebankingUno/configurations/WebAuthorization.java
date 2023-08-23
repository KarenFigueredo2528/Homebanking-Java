package com.mindhub.homebankingUno.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/clients", "/api/logout").permitAll()
                .antMatchers("/web/admiPages/**", "/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/assets/**").hasAuthority("CLIENT")
                .antMatchers("/web/register.html", "/web/index.js",
                        "/web/style/style.css", "/web/images").permitAll()
                .antMatchers("/rest", "/h2-console/").hasAuthority("ADMIN");


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
