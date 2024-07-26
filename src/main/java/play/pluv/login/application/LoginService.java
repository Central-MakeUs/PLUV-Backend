package play.pluv.login.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.member.domain.Member;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.oauth.application.SocialLoginClientComposite;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final RegisterReader registerReader;
  private final RegisterUpdater registerUpdater;
  private final SocialLoginClientComposite socialLoginClientComposite;

  public Long createToken(final MusicStreaming serverType, final String authCode) {
    final OAuthMemberInfo memberInfo = socialLoginClientComposite
        .fetchMemberInfo(serverType, authCode);

    final Member member = registerReader.findByOAuthMemberInfo(memberInfo)
        .orElseGet(() -> registerUpdater.registerNewMember(memberInfo));

    return member.getId();
  }
}
