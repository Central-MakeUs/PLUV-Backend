package play.pluv.playlist.domain;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Builder
@ToString
public class PlayListMusic {

  @Getter
  private final String title;
  @Getter
  private final List<String> artistNames;
  //Nullable
  private final String isrcCode;
  @Getter
  private final String imageUrl;

  public Optional<String> getIsrcCode() {
    return Optional.ofNullable(isrcCode);
  }
}
