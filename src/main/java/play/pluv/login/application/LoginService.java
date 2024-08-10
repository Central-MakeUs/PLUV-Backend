package play.pluv.login.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.member.domain.Member;
import play.pluv.oauth.application.SocialLoginClientComposite;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.playlist.domain.MusicStreaming;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final RegisterReader registerReader;
  private final RegisterUpdater registerUpdater;
  private final SocialLoginClientComposite socialLoginClientComposite;

  @Transactional
  public Long createToken(final MusicStreaming serverType, final String key) {
    final OAuthMemberInfo memberInfo = socialLoginClientComposite
        .fetchMemberInfo(serverType, key);

    final Member member = registerReader.findByOAuthMemberInfo(memberInfo)
        .orElseGet(() -> registerUpdater.registerNewMember(memberInfo));

    return member.getId();
  }

  @Transactional
  public void addOtherLoginWay(
      final MusicStreaming serverType, final Long memberId, final String key
  ) {
    final OAuthMemberInfo memberInfo = socialLoginClientComposite
        .fetchMemberInfo(serverType, key);

    registerUpdater.addOtherLoginSource(memberId, memberInfo);
  }
}
