package com.luban.config;

import com.luban.service.MyUserDetailService;
import com.luban.service.MyUserDetailService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Hyy
 * @title: WebSecurityConfig
 * @description: TODO
 * @date 2020/12/22
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private MyUserDetailService2 userDetailService2;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("hyy")
//                .password(passwordEncoder().encode("111"))
//                .authorities("admin")
//                .and()
//                .withUser("hyy2")
//                .password(passwordEncoder().encode("222"))
//                .authorities("admin");
        auth.userDetailsService(userDetailService).and()
                .userDetailsService(userDetailService2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
