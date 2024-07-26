package play.pluv.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static play.pluv.member.exception.MemberExceptionType.NICK_NAME_LENGTH_IS_OVER;

import org.junit.jupiter.api.Test;
import play.pluv.member.exception.MemberException;

class NickNameTest {

  @Test
  void 닉네임이_10자_초과면_예외처리한다() {
    final String nickName = "a".repeat(11);

    assertThatThrownBy(() -> new NickName(nickName))
        .isInstanceOf(MemberException.class)
        .hasMessage(NICK_NAME_LENGTH_IS_OVER.getMessage());
  }
}