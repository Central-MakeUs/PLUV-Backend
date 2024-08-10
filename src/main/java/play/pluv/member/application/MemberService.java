package play.pluv.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.member.domain.NickName;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberUpdater memberUpdater;

  @Transactional
  public void updateNickname(final Long memberId, final String nickName) {
    memberUpdater.updateNickName(memberId, new NickName(nickName));
  }
}
