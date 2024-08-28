package play.pluv.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.login.domain.SocialLoginId;
import play.pluv.login.domain.SocialLoginIdRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.support.ApplicationTest;

class LoginServiceTest extends ApplicationTest {

  @Autowired
  private LoginService loginService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private SocialLoginIdRepository socialLoginIdRepository;

  @Test
  void 회원가입하고_토큰을_반환한다() {
    final String authCode = "authCode";

    final Long id = loginService.createToken(SPOTIFY, authCode);

    assertThat(memberRepository.findById(id)).isNotEmpty();
  }

  @Test
  void 유저가_로그인한_방법들을_반환한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final SocialLoginId apple = new SocialLoginId(member, new OAuthMemberInfo("abcd", APPLE));
    socialLoginIdRepository.save(apple);
    final SocialLoginId youtube = new SocialLoginId(member, new OAuthMemberInfo("abcd", YOUTUBE));
    socialLoginIdRepository.save(youtube);

    final List<MusicStreaming> actual = loginService.getLoginTypes(member.getId());
    final List<MusicStreaming> expected = List.of(APPLE, YOUTUBE);

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}