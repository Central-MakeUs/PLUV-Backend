package play.pluv.login.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.login.domain.SocialLoginId;
import play.pluv.login.domain.SocialLoginIdRepository;
import play.pluv.member.application.NickNameGenerator;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.NickName;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Component
@RequiredArgsConstructor
@Transactional
public class RegisterUpdater {

  private final SocialLoginIdRepository socialLoginIdRepository;
  private final NickNameGenerator nickNameGenerator;
  private final MemberRepository memberRepository;

  public Member registerNewMember(final OAuthMemberInfo memberInfo) {
    final NickName nickName = nickNameGenerator.generateNickName();
    final Member member = new Member(nickName);

    final SocialLoginId loginId = SocialLoginId.builder()
        .oauthMemberInfo(memberInfo)
        .member(member)
        .build();
    socialLoginIdRepository.save(loginId);

    return memberRepository.save(member);
  }

  public void addOtherLoginSource(final Long memberId, final OAuthMemberInfo memberInfo) {
    if (socialLoginIdRepository.existsByOauthMemberInfo(memberInfo)) {
      return;
    }
    final Member member = memberRepository.readById(memberId);

    final SocialLoginId loginId = SocialLoginId.builder()
        .oauthMemberInfo(memberInfo)
        .member(member)
        .build();

    socialLoginIdRepository.save(loginId);
  }
}
