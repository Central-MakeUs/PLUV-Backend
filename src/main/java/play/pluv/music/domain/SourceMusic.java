package play.pluv.music.domain;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class SourceMusic {

  @Getter
  private final String name;
  @Getter
  private final String artistName;
  private final String isrcCode;

  public Optional<String> getIsrcCode(){
    return Optional.ofNullable(isrcCode);
  }
}
