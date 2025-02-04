package kr.co.jeelee.practice_oauth.controller;

import java.util.Map;

import kr.co.jeelee.practice_oauth.entity.Member;
import kr.co.jeelee.practice_oauth.service.AuthService;
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

	private final AuthService authService;

	@GetMapping(value = "/login/discord")
	public ResponseEntity<?> login() {
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/oauth2/authorization/discord").build();
	}

	@GetMapping(value = "/me")
	public ResponseEntity<?> me() {
		Member member = authService.getMe();
		return ResponseEntity.ok().body(member);
	}

}
