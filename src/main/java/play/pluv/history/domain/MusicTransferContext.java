package play.pluv.history.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.playlist.domain.MusicStreaming;

@Getter
@RequiredArgsConstructor
public class MusicTransferContext {

  private final Long memberId;
  private final List<TransferFailMusicInContext> transferFailMusics;
  private List<TransferredMusic> transferredMusics;
  private MusicStreaming source;
  private MusicStreaming destination;
  private String thumbNailUrl;
  private String title;

  public History toHistory() {
    return History.builder()
        .totalSongCount(transferFailMusics.size() + transferredMusics.size())
        .transferredSongCount(transferredMusics.size())
        .thumbNailUrl(thumbNailUrl)
        .source(source)
        .destination(destination)
        .title("title")
        .memberId(memberId)
        .build();
  }
}
