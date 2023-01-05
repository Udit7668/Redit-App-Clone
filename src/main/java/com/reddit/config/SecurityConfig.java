package com.reddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests()
<<<<<<< HEAD
                .antMatchers("/api/auth/**","/form/**","/addSubreddit","/showSubreddit")
=======
                .antMatchers("/api/auth/**","/form/**","/home/**")
>>>>>>> 3ef27045c17784a2d758f5fe2f43b925e6b4d0cb
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/form/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/form/sign-up")
                .and()
                .logout()
                .permitAll();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
