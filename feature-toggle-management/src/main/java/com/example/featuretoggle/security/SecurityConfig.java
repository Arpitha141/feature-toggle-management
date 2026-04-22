package com.example.featuretoggle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		.csrf(AbstractHttpConfigurer :: disable)
		// Required to allow H2 Console to render in a frame
		.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
		.authorizeHttpRequests(auth -> auth 
				.requestMatchers(
						"/v3/api-docs/**",
	                    "/swagger-ui/**",
	                    "/swagger-ui.html",
	                    "/actuator/health",
	                    "/actuator/info",
	                    "/h2-console/**"
						).permitAll()
				.requestMatchers("/features/**").authenticated()
				.anyRequest().authenticated()
				)
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123") // {noop} denotes plain text for assignment simplicity
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

}
