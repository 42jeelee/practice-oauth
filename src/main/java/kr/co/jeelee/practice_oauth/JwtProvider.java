package kr.co.jeelee.practice_oauth;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;

	public String generateToken(final Authentication authentication) {
		Instant now = Instant.now();
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
			.issuer("admin")
			.issuedAt(now)
			.expiresAt(now.plusSeconds(3600))
			.subject(authentication.getName())
			.claim("Authorities", authorities)
			.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
	}

	public String getSubject(final String token) {
		return jwtDecoder.decode(token).getSubject();
	}

}
