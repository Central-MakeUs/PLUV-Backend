package play.pluv.fixture;

import play.pluv.member.domain.Member;
import play.pluv.member.domain.NickName;
import play.pluv.member.domain.repository.MemberRepository;

public class MemberEntityFixture {

  public static Member 멤버_홍혁준(final MemberRepository memberRepository) {
    return memberRepository.save(new Member(new NickName("홍혁준")));
  }
}
