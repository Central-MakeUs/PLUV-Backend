package play.pluv.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.NickName;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.support.ApplicationTest;

class MemberServiceTest extends ApplicationTest {

  @Autowired
  private MemberService memberService;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  void 닉네임을_업데이트한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final String changeNickName = "변경된 닉네임";

    memberService.updateNickname(member.getId(), changeNickName);

    final NickName actual = memberRepository.readById(member.getId()).getNickName();
    assertThat(actual.getNickName())
        .isEqualTo(changeNickName);
  }
}