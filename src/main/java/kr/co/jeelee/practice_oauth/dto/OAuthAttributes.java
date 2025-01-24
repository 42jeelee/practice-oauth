package kr.co.jeelee.practice_oauth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum OAuthAttributes {
    DISCORD("discord", (userNameAttributeName, attributes) -> OAuth2UserInfo.of(
            "discord",
            attributes.get(userNameAttributeName).toString(),
            attributes
    )),

    ;

    private final String registrationId;
    private final BiFunction<String, Map<String, Object>, OAuth2UserInfo> of;

    public static OAuth2UserInfo of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> provider.registrationId.equals(registrationId))
                .findFirst()
                .map(provider -> provider.of.apply(userNameAttributeName, attributes))
                .orElseThrow(() -> new IllegalArgumentException("Invalid registration id " + registrationId));
    }

}
