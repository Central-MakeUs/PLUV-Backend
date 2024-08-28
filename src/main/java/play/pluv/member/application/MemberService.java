package play.pluv.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.member.domain.NickName;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberUpdater memberUpdater;
  private final MemberReader memberReader;

  @Transactional
  public void updateNickname(final Long memberId, final String nickName) {
    memberUpdater.updateNickName(memberId, new NickName(nickName));
  }

  @Transactional
  public void unregister(final Long memberId) {
    memberUpdater.unregister(memberId);
  }

  @Transactional(readOnly = true)
  public String getNickName(final Long memberId) {
    return memberReader.readNickName(memberId).getNickName();
  }
}
