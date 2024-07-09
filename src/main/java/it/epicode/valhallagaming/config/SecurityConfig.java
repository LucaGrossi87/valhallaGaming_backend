package it.epicode.valhallagaming.config;

import it.epicode.valhallagaming.filter.JwtRequestFilter;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Data
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/bookings/lanbooking").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/bookings/boardbookingclose").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/bookings/bookingbyid").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/bookings/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/lans").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/lans/available").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/boards").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/boards/available").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/boards/open").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/boards/byseats").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/bookings/bookingsbydate").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/bookings/openbookings").permitAll()
                .anyRequest().authenticated()
        ).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
