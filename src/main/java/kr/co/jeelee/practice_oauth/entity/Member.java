package kr.co.jeelee.practice_oauth.entity;

import kr.co.jeelee.practice_oauth.dto.OAuth2UserInfo;
import lombok.Getter;

@Getter
public class Member {
    private static int count = 0;

    private String id;
    private String name;

    private String providerId;
    private String provider;

    Member(String id, String name, String provider) {
        this.id = "user_" + count;
        this.name = name;
        this.providerId = id;
        this.provider = provider;
        count++;
    }

    public static Member of(String id, String name, String provider) {
        return new Member(id, name, provider);
    }

    public static Member of(OAuth2UserInfo userInfo) {
        return of(
                userInfo.id(),
                "userInfo.name_" + count,
                userInfo.provider()
        );
    }

    @Override
    public String toString() {
        return String.format("[%s] %s, (%s : %s)", this.id, this.name, this.providerId, this.provider);
    }
}
