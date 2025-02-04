package kr.co.jeelee.practice_oauth.dto;

import kr.co.jeelee.practice_oauth.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public record CustomOAuth2User(
        Member member,
        OAuth2UserInfo oAuth2UserInfo
) implements OAuth2User {

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo.attributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getName() {
        return member().getId();
    }

    public static CustomOAuth2User of(Member member) {
        return new CustomOAuth2User(member, null);
    }

}
