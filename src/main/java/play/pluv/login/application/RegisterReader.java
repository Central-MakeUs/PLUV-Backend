package play.pluv.login.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.login.domain.SocialLoginId;
import play.pluv.login.domain.SocialLoginIdRepository;
import play.pluv.member.domain.Member;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterReader {

  private final SocialLoginIdRepository socialLoginIdRepository;

  public Optional<Member> findByOAuthMemberInfo(final OAuthMemberInfo memberInfo) {
    return socialLoginIdRepository.findByOAuthMemberInfo(memberInfo)
        .map(SocialLoginId::getMember);
  }
}
