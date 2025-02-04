package kr.co.jeelee.practice_oauth.config;

import kr.co.jeelee.practice_oauth.handler.CustomAuthenticationSuccessHandler;
import kr.co.jeelee.practice_oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final AuthenticationFilter authenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(authenticationFilter, OAuth2LoginAuthenticationFilter.class)
			.sessionManagement(session ->
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/login", "/user").permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth -> oauth
					.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
					.successHandler(customAuthenticationSuccessHandler)
			);

		return http.build();
	}

}