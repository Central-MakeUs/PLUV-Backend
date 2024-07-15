package play.pluv.playlist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@RequiredArgsConstructor
public class PlayList {

  private final PlayListId playListId;
  @Getter
  private final String name;
  @Getter
  private final String thumbNailUrl;
  @Getter
  private final Integer songCount;

  public PlayListProvider getSource() {
    return playListId.playListProvider();
  }
}
