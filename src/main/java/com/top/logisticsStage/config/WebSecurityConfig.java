package com.top.logisticsStage.config;

import com.top.logisticsStage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.
                        authorizeRequests().antMatchers("/index").authenticated().
                        and().authorizeRequests().antMatchers("/error").permitAll().
                        and().formLogin().loginPage("/api/login").
                        and().formLogin().successHandler(new MyLoginSuccessHandler()).
                        and().formLogin().failureHandler(new MyLoginFailureHandler()).
                        and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess").
                        invalidateHttpSession(true).
                        addLogoutHandler(new MyLogoutHandle()).logoutSuccessHandler(new MyLogoutSuccessHandle()).
                        and().authorizeRequests().anyRequest().permitAll().
                        and().headers().frameOptions().sameOrigin().
                        and().csrf().disable();
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //通过用户名，查找数据库中密码
            com.top.logisticsStage.domain.User user = userService.getUserByAccount(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户名未找到");
            }
            String password = user.getPassword();
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String passwordAfterEncoder = passwordEncoder.encode(password);
            System.out.println(username + "/" + passwordAfterEncoder);

            return User.withUsername(username).password(passwordAfterEncoder).roles("").build();};
    }

}
