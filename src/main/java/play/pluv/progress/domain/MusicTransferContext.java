package play.pluv.progress.domain;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import play.pluv.feed.domain.Feed;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
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

  public History toHistory(final Long feedId) {
    return History.builder()
        .totalSongCount(transferFailMusics.size() + transferredMusics.size())
        .transferredSongCount(transferredMusics.size())
        .thumbNailUrl(thumbNailUrl)
        .source(source)
        .destination(destination)
        .title(title)
        .memberId(memberId)
        .feedId(feedId)
        .build();
  }

  public List<TransferFailMusic> extractTransferFailMusics(final Long historyId) {
    return transferFailMusics.stream()
        .map(music -> music.toTransferFailMusic(historyId))
        .toList();
  }

  public List<TransferredMusic> extractTransferredMusics(final Long historyId) {
    return transferredMusics.stream()
        .map(music -> music.toTransferredMusic(historyId))
        .toList();
  }

  public TransferProgress currentProgress() {
    return new TransferProgress(willTransferMusicCount, transferredMusics.size());
  }

  public void addTransferredMusics(final List<TransferredMusicInContext> transferredMusics) {
    this.transferredMusics.addAll(transferredMusics);
  }

  public Feed createFeed(final String creatorName) {
    final String artistNames = extractArtistNames(transferredMusics);

    return Feed.builder()
        .viewable(true)
        .memberId(memberId)
        .title(title)
        .creatorName(creatorName)
        .artistNames(artistNames)
        .thumbNailUrl(thumbNailUrl)
        .build();
  }

  private String extractArtistNames(final List<TransferredMusicInContext> transferredMusics) {
    return transferredMusics.stream()
        .map(TransferredMusicInContext::getArtistNames)
        .collect(joining(","));
  }
}
