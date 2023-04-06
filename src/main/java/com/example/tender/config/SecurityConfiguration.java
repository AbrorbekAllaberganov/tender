package com.example.tender.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CorsFilter;
import com.example.tender.config.cors.CORSFilter;
import com.example.tender.config.cors.CustomCorsFilter;
import com.example.tender.security.JwtAuthenticationEntryPoint;
import com.example.tender.security.JwtConfigurer;
import com.example.tender.security.JwtTokenProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider, JwtAuthenticationEntryPoint jwtAuthEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .headers().frameOptions().and().and()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources", "/swagger-resources/**", "/v2/**", "/csrf").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Description("This CORS Filter is of type org.springframework.web.filter.CorsFilter")
    @Primary
    @Profile("customCorsFilter")
    @Bean
    public CorsFilter corsFilter(){
        return new CustomCorsFilter();
    }


    /**
     * Generic GenericFilterBean CORS Filter
     * @return
     */
    @Description("This CORS Filter is of type FilterRegistrationBean")
    @Profile("corsFilterBean")
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {

        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(new CORSFilter());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
