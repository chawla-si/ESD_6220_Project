package com.esd.esd_6200.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable());

	    http.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/books/secure/**",
	                             "/api/reviews/secure/**",
	                             "/api/messages/secure/**",
	                             "/api/admin/secure/**")
	            .authenticated()
	            .anyRequest().permitAll()
	    );

	    http.oauth2ResourceServer(oauth2 -> oauth2
	            .jwt(Customizer.withDefaults())
	            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
	    );

	    http.cors(Customizer.withDefaults());

	    http.setSharedObject(ContentNegotiationStrategy.class,
	            new HeaderContentNegotiationStrategy());

	    return http.build();
	}
}

