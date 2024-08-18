package play.pluv.transfer_context.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.playlist.domain.MusicStreaming;

@Getter
@Builder
public class MusicTransferContext {

  private final Long memberId;
  private final List<TransferFailMusicInContext> transferFailMusics;
  private final Integer willTransferMusicCount;
  private final String thumbNailUrl;
  private final String title;
  private final MusicStreaming source;
  private final MusicStreaming destination;
  private final List<TransferredMusicInContext> transferredMusics = new ArrayList<>();

  public History toHistory() {
    return History.builder()
        .totalSongCount(transferFailMusics.size() + transferredMusics.size())
        .transferredSongCount(transferredMusics.size())
        .thumbNailUrl(thumbNailUrl)
        .source(source)
        .destination(destination)
        .title(title)
        .memberId(memberId)
        .build();
  }

  public List<TransferFailMusic> extractTransferFailMusics(final Long historyId) {
    return transferFailMusics.stream()
        .map(music -> music.toTransferFailMusic(historyId))
        .toList();
  }

  public TransferProgress currentProgress() {
    return new TransferProgress(willTransferMusicCount, transferredMusics.size());
  }

  public void addTransferredMusics(final List<TransferredMusicInContext> transferredMusics) {
    this.transferredMusics.addAll(transferredMusics);
  }
}
