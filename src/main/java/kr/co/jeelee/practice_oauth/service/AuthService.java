package kr.co.jeelee.practice_oauth.service;

import kr.co.jeelee.practice_oauth.JwtProvider;
import kr.co.jeelee.practice_oauth.dto.CustomOAuth2User;
import kr.co.jeelee.practice_oauth.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;

    public Member getMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final CustomOAuth2User oAuth2User = Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .filter(CustomOAuth2User.class::isInstance)
                .map(CustomOAuth2User.class::cast)
                .orElseThrow(() -> new RuntimeException("No CustomOAuth2User found"));

        return oAuth2User.member();
    }

    public List<SimpleGrantedAuthority> getAuthorities(final String token) {
        return jwtProvider.getAuthorities(token);
    }

}
