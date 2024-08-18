package play.pluv.history.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

@Getter
@RequiredArgsConstructor
public class MusicTransferContext {

  private final Long memberId;
  private final List<PlayListMusic> playListMusics;
  private Integer transferredSongCount;
  private MusicStreaming source;
  private MusicStreaming destination;
  private String thumbNailUrl;

  public History toHistory() {
    return History.builder()
        .totalSongCount(playListMusics.size())
        .transferredSongCount(transferredSongCount)
        .thumbNailUrl(thumbNailUrl)
        .source(source)
        .destination(destination)
        .title("title")
        .memberId(memberId)
        .build();
  }
}
