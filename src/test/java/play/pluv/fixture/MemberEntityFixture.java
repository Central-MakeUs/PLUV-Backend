package play.pluv.fixture;

import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.member.domain.NickName;

public class MemberEntityFixture {

  public static Member 멤버_홍혁준(final MemberRepository memberRepository) {
    return new Member(new NickName("홍혁준"));
  }
}
