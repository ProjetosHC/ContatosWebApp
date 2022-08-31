package com.npi.contatosweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MinhaConfig {

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImp();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(authenticationProvider());
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN")
                                    .antMatchers("/usuario/**").hasRole("USUARIO")
                                    .antMatchers("/**").permitAll().and().formLogin()
                                    .loginPage("/entrar")
                                    // .loginProcessingUrl("/fzr-login")
                                    .defaultSuccessUrl("/usuario/index")
                                    // .failureUrl("/login-fail")
                                    .and().csrf().disable()
                                    .logout().logoutSuccessUrl("/entrar");

        return http.build();
    }

}
