package play.pluv.member.application;

import org.springframework.stereotype.Component;
import play.pluv.member.domain.NickName;

@Component
public class NickNameGenerator {

  private static final String FORMAT = "플러버%s";
  private static int NICK_NAME_IDENTIFIER = 1;

  public NickName generateNickName() {
    return new NickName(String.format(FORMAT, NICK_NAME_IDENTIFIER++));
  }
}
