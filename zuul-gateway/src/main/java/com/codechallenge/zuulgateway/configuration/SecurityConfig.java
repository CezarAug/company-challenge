package com.codechallenge.zuulgateway.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * An in memory security configuration.
 * For a production ready solution this needs to be switched over to a proper auth service responsible for generating oAuth2 tokens
 */
//@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
        UserDetails user = userBuilder
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = userBuilder
                .username("admin")
                .password("admin")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }


    @Configuration
    @Order(1)
    public static class ItineraryApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/itinerary-service/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .httpBasic()
                    .and()
                    .formLogin().disable();
        }
    }

    @Configuration
    @Order(2)
    public static class CityApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/city-service/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }

}
