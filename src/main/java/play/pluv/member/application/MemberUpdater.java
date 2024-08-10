package play.pluv.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.member.domain.NickName;
import play.pluv.member.domain.repository.MemberRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberUpdater {

  private final MemberRepository memberRepository;

  public void updateNickName(final Long memberId, final NickName nickName) {
    memberRepository.readById(memberId)
        .updateNickName(nickName);
  }

  public void unregister(final Long memberId) {
    memberRepository.deleteById(memberId);
  }
}
