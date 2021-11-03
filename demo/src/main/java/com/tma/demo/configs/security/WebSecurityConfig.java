package com.tma.demo.configs.security;

import com.tma.demo.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //We don't need CSRF for this example
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/v1/user/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll().and()
//                .formLogin()
////                .loginPage("/login")
////                .permitAll()
//                .usernameParameter("email").passwordParameter("password")
//                .successHandler(jwtAuthenticationEntryPoint)
//                .failureHandler(jwtAuthenticationEntryPoint)
//                .and().logout().deleteCookies("remember-me", "JSESSIONID").permitAll().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
                and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        /*
         * antMatchers(["url]).hasAuthority("role").(maybe duplicate when has more than one role)
         * anyRequest().authenticated().
         * and().
         * formLogin("URL login").permitAll().
         * and().
         * logout().permitAll();
         *
         */
    }

    @Bean
    public ForbiddenException accessDeniedHandler() {
        return new ForbiddenException("Forbidden Error. You don't have permission to access on this server.");
    }


}