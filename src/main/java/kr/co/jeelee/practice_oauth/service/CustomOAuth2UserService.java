package kr.co.jeelee.practice_oauth.service;

import kr.co.jeelee.practice_oauth.dto.CustomOAuth2User;
import kr.co.jeelee.practice_oauth.dto.OAuth2UserInfo;
import kr.co.jeelee.practice_oauth.dto.OAuthAttributes;
import kr.co.jeelee.practice_oauth.entity.Member;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = Member.of(oAuth2UserInfo);

        System.out.println(member);

        return new CustomOAuth2User(member, oAuth2UserInfo);
    }
}
