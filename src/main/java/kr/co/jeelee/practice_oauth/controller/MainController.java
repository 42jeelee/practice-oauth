package kr.co.jeelee.practice_oauth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MainController {

	@GetMapping(value = "/login")
	public ResponseEntity<?> login() {
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/oauth2/authorization/discord").build();
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> user(
		@AuthenticationPrincipal OAuth2User oAuth2User
	) {
		Map<String, Object> map = oAuth2User.getAttributes();
		return ResponseEntity.ok().body(map);
	}

}
