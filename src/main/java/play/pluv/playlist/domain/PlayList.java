package play.pluv.playlist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import play.pluv.music.domain.MusicStreaming;

@Builder
@ToString
@Getter
@RequiredArgsConstructor
public class PlayList {

  private final PlayListId playListId;
  private final String name;
  private final String thumbNailUrl;
  private final Integer songCount;
}
