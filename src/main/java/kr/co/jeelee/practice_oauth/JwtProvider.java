package kr.co.jeelee.practice_oauth;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.co.jeelee.practice_oauth.dto.CustomOAuth2User;
import kr.co.jeelee.practice_oauth.dto.OAuthAttributes;
import kr.co.jeelee.practice_oauth.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

	public List<SimpleGrantedAuthority> getAuthorities(final String token) {
		String authorities = jwtDecoder.decode(token).getClaimAsString("Authorities");

		return Arrays.stream(authorities.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public OAuth2AuthenticationToken getAuthenticationToken(final String token) {
		Member member = Member.of(jwtDecoder.decode(token).getSubject(), "sample", "discord");
		CustomOAuth2User customOAuth2User = CustomOAuth2User.of(member);

		return new OAuth2AuthenticationToken(
				customOAuth2User,
				getAuthorities(token),
				member.getProvider()
		);
	}

}
