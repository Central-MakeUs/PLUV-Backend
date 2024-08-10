package play.pluv.member.domain;

import static lombok.AccessLevel.PROTECTED;
import static play.pluv.member.exception.MemberExceptionType.NICK_NAME_LENGTH_IS_OVER;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.member.exception.MemberException;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class NickName {

  private static final int MAX_LENGTH = 10;

  private String nickName;

  public NickName(final String nickName) {
    validateLength(nickName);
    this.nickName = nickName;
  }

  private void validateLength(final String nickName) {
    if (nickName.length() > MAX_LENGTH) {
      throw new MemberException(NICK_NAME_LENGTH_IS_OVER);
    }
  }
}
