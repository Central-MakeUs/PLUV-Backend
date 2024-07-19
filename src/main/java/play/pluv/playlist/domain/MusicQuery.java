package play.pluv.playlist.domain;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MusicQuery {

  @Getter
  private final String name;
  @Getter
  private final String artistName;
  private final String isrcCode;

  public Optional<String> getIsrcCode(){
    return Optional.ofNullable(isrcCode);
  }
}
