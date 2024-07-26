package play.pluv.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.MemberFixture.멤버_홍혁준;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.login.domain.SocialLoginId;
import play.pluv.login.domain.SocialLoginIdRepository;
import play.pluv.member.domain.Member;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.support.ApplicationTest;

class RegisterReaderTest extends ApplicationTest {

  @Autowired
  private RegisterReader registerReader;
  @Autowired
  private SocialLoginIdRepository socialLoginIdRepository;

  @Nested
  class oauthMemberInfo로_가입된_멤버를_조회한다 {

    @Test
    void 가입된_멤버가_없는_경우() {
      final OAuthMemberInfo oAuthMemberInfo = new OAuthMemberInfo("1234", SPOTIFY);

      final Optional<Member> member = registerReader.findByOAuthMemberInfo(oAuthMemberInfo);

      assertThat(member).isEmpty();
    }

    @Test
    void 가입된_멤버가_있는_경우() {
      final OAuthMemberInfo oAuthMemberInfo = new OAuthMemberInfo("1234", SPOTIFY);
      final SocialLoginId socialLoginId = new SocialLoginId(멤버_홍혁준(), oAuthMemberInfo);
      socialLoginIdRepository.save(socialLoginId);

      final Optional<Member> member = registerReader.findByOAuthMemberInfo(oAuthMemberInfo);

      assertThat(member).isNotEmpty();
    }
  }

}