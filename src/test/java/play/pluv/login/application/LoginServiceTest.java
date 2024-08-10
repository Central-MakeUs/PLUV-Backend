package play.pluv.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.support.ApplicationTest;

class LoginServiceTest extends ApplicationTest {

  @Autowired
  private LoginService loginService;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  void 회원가입하고_토큰을_반환한다() {
    final String authCode = "authCode";

    final Long id = loginService.createToken(SPOTIFY, authCode);

    assertThat(memberRepository.findById(id)).isNotEmpty();
  }
}