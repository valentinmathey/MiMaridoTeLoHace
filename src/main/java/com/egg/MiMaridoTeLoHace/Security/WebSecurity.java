package com.egg.MiMaridoTeLoHace.Security;

import com.egg.MiMaridoTeLoHace.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UserService userService;    
    

    @Autowired
    @Qualifier("userService") //VALENTIN - Solusion temporal para el tema de los BEAN (Se puede modificar entre admin o provider o customer)
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
     @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/admin/*").hasRole("ADMIN")
                        .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                        .permitAll()
                .and().formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                .and().csrf()
                        .disable();
                

    }
}
