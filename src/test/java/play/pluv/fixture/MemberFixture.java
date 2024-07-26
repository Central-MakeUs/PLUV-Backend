package play.pluv.fixture;

import play.pluv.member.domain.Member;
import play.pluv.member.domain.NickName;

public class MemberFixture {

  public static Member 멤버_홍혁준() {
    return new Member(new NickName("홍혁준"));
  }
}
