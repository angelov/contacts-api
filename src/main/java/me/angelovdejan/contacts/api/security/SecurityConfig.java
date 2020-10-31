package me.angelovdejan.contacts.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/login").permitAll()
                    .antMatchers("/swagger-ui.html").permitAll()
                    .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                    .antMatchers("/swagger-resources/**").permitAll()
                    .antMatchers("/v2/api-docs").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/users/").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .apply(jwtConfigurer);
    }
}
