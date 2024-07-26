package play.pluv.login.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.login.domain.SocialLoginId;
import play.pluv.login.domain.SocialLoginIdRepository;
import play.pluv.member.application.NickNameGenerator;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.NickName;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Component
@RequiredArgsConstructor
public class RegisterUpdater {

  private final SocialLoginIdRepository socialLoginIdRepository;
  private final NickNameGenerator nickNameGenerator;

  @Transactional
  public Member registerNewMember(final OAuthMemberInfo memberInfo) {
    final NickName nickName = nickNameGenerator.generateNickName();
    final Member member = new Member(nickName);

    final SocialLoginId loginId = SocialLoginId.builder()
        .oauthMemberInfo(memberInfo)
        .member(member)
        .build();
    socialLoginIdRepository.save(loginId);

    return member;
  }
}
