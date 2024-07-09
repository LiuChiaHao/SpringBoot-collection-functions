package com.myproject.Collection.security;

import com.myproject.Collection.controller.LoginController;
import com.myproject.Collection.service.JWTServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


/*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}123456")
                .roles("employee")
                .build();
        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}456789")
                .roles("employee", "manager")
                .build();
        return new InMemoryUserDetailsManager(john, mary);
    }*/

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
            logger.info("Get data source with Jdbc : {}", dataSource);

            JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
            theUserDetailsManager
                    .setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");

            theUserDetailsManager
                    .setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");

            return theUserDetailsManager;
        }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        logger.info("HTTP security filter chain");
        http.authorizeHttpRequests(configurer ->
                        configurer

                                //.requestMatchers(HttpMethod.POST, "/authenticateProcess").permitAll()
                               // .requestMatchers(HttpMethod.GET, "/staffList").authenticated()


                                .requestMatchers(HttpMethod.GET, "/api/staffs").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/staffs/**").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/staffFindById").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/staffFindById/**").hasRole("EMPLOYEE")

                                .requestMatchers(HttpMethod.POST, "/api/addStaff").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/updateStaff").hasRole("MANAGER")

                                .requestMatchers(HttpMethod.DELETE, "/api/deleteStaff/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )


                .formLogin(form ->
                        form
                                .loginPage("/loginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/staffList", true)
                                .permitAll()
                ).logout(logout -> logout.permitAll());

        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter(UserDetailsManager userDetailsManager, JWTServiceImplement theJWTServiceImplement) {
        return new JwtRequestFilter(userDetailsManager, theJWTServiceImplement);
    }

}
