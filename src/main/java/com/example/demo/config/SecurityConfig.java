package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// Removed incorrect import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Auth.AuthEntryPointJwt;
import com.example.demo.filter.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource) {
    //     return new JdbcUserDetailsManager(dataSource);
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Cho phép truy cập không cần xác thực
                        .requestMatchers("/signin","/register").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
        );
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                        .sameOrigin()
                )
        );
        http.csrf(csrf -> csrf.disable());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcUserDetailsManager mgr = new JdbcUserDetailsManager(dataSource);

    // Câu query để load user (nếu bạn chưa đổi)
    mgr.setUsersByUsernameQuery(
      "SELECT Username, Password, enabled " +
      "FROM Users " +
      "WHERE Username = ?"
    );

    // Câu query để load authorities qua Id_User
    mgr.setAuthoritiesByUsernameQuery(
      "SELECT u.Username AS username, a.authority " +
      "FROM authorities a " +
      "JOIN Users u ON a.Id_User = u.Id_User " +
      "WHERE u.Username = ?"
    );

    return mgr;
}


 

    // @Bean
    // public CommandLineRunner initData(UserDetailsService userDetailsService) {
    //     return args -> {
    //         JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
    //         UserDetails user3 = User.withUsername("user3")
    //                 .password(passwordEncoder().encode("password3"))
    //                 .roles("DIRECTOR")
    //                 .build();
    //         UserDetails admin2 = User.withUsername("admin2")
    //                 .password(passwordEncoder().encode("adminPass2"))
    //                 .roles("ADMIN")
    //                 .build();

    //         JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    //         userDetailsManager.createUser(user3);
    //         userDetailsManager.createUser(admin2);
    //     };
    // }


    

    
}
