package kr.co.jeelee.practice_oauth.dto;

import java.util.Map;

public record OAuth2UserInfo(
        String provider, String id,
        Map<String, Object> attributes
) {
    public static OAuth2UserInfo of(
            final String provider, final String id,
            final Map<String, Object> attributes
    ) {
        return new OAuth2UserInfo(provider, id, attributes);
    }
}
