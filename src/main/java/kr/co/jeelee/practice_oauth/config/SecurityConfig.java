package kr.co.jeelee.practice_oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/login", "/user").permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth -> oauth
					.defaultSuccessUrl("/user", true)
					.failureUrl("/user?error=true")
			);

		return http.build();
	}

}