package com.egg.MiMaridoTeLoHace.Security;

import com.egg.MiMaridoTeLoHace.Services.AdminService;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurity extends  WebSecurityConfigurerAdapter{
    
    @Autowired
    public ProviderService providerService;
    
    @Autowired
    public CustomerService customerService;
    
    @Autowired
    public AdminService adminService;

    @Qualifier("customerService")
    @Autowired
    @Qualifier("providerService") //VALENTIN - Solusion temporal para el tema de los BEAN (Se puede modificar entre admin o provider o customer)
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(providerService)
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
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
                

    }
}
