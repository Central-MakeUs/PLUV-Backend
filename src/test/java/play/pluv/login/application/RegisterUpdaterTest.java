package play.pluv.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.MemberRepository;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.support.ApplicationTest;

class RegisterUpdaterTest extends ApplicationTest {

  @Autowired
  private RegisterUpdater registerUpdater;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  void 회원가입한다() {
    final OAuthMemberInfo oAuthMemberInfo = new OAuthMemberInfo("1234", SPOTIFY);

    final Member member = registerUpdater.registerNewMember(oAuthMemberInfo);

    assertThat(memberRepository.existsById(member.getId()))
        .isTrue();
  }
}