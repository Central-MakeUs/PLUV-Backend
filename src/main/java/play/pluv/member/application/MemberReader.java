package play.pluv.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

  private final MemberRepository memberRepository;

  public Member readById(final Long memberId) {
    return memberRepository.readById(memberId);
  }
}
