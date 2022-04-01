package com.sgenne.timetracking.security;

import com.sgenne.timetracking.security.filter.JWTAuthenticationFilter;
import com.sgenne.timetracking.security.filter.JWTAuthorizationFilter;
import com.sgenne.timetracking.user.Role;
import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserController;
import com.sgenne.timetracking.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

import static com.sgenne.timetracking.api.API.URL_PREFIX;
import static com.sgenne.timetracking.user.Role.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_URL = URL_PREFIX + "/login";


    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this::getUserDetailsByUsername);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthorizationFilter jwtAuthorizationFilter =
                new JWTAuthorizationFilter();

        JWTAuthenticationFilter jwtAuthenticationFilter =
                new JWTAuthenticationFilter(authenticationManagerBean());

        jwtAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(LOGIN_URL + "/**").permitAll();
        http.authorizeRequests().antMatchers(UserController.ROOT_URL + "/**").hasAuthority(USER.name());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private UserDetails getUserDetailsByUsername(String username) {
        User user = userService.getUserByUsername(username);
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
