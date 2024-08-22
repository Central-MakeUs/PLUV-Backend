package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.PROTECTED;
import static play.pluv.history.exception.HistoryExceptionType.HISTORY_NOT_OWNER;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.base.BaseEntity;
import play.pluv.feed.domain.Feed;
import play.pluv.history.exception.HistoryException;
import play.pluv.playlist.domain.MusicStreaming;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class History extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String title;
  private String thumbNailUrl;
  private Integer transferredSongCount;
  private Integer totalSongCount;
  private Long memberId;
  @Enumerated(EnumType.STRING)
  private MusicStreaming source;
  @Enumerated(EnumType.STRING)
  private MusicStreaming destination;

  public History(final Long id, final String title, final String thumbNailUrl,
      final Integer transferredSongCount,
      final Integer totalSongCount, final Long memberId, final MusicStreaming source,
      final MusicStreaming destination, final LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.thumbNailUrl = thumbNailUrl;
    this.transferredSongCount = transferredSongCount;
    this.totalSongCount = totalSongCount;
    this.memberId = memberId;
    this.source = source;
    this.destination = destination;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  @Builder
  public History(final String title, final String thumbNailUrl, final Integer transferredSongCount,
      final Integer totalSongCount, final Long memberId, final MusicStreaming source,
      final MusicStreaming destination) {
    this.title = title;
    this.thumbNailUrl = thumbNailUrl;
    this.transferredSongCount = transferredSongCount;
    this.totalSongCount = totalSongCount;
    this.memberId = memberId;
    this.source = source;
    this.destination = destination;
  }

  public void validateOwner(final Long memberId) {
    if (!Objects.equals(memberId, this.memberId)) {
      throw new HistoryException(HISTORY_NOT_OWNER);
    }
  }

  public Feed createFeed(
      final String creatorName, final List<TransferredMusic> transferredMusics
  ) {
    final String artistNames = extractArtistNames(transferredMusics);

    return Feed.builder()
        .viewable(true)
        .historyId(id)
        .memberId(memberId)
        .title(title)
        .creatorName(creatorName)
        .artistNames(artistNames)
        .thumbNailUrl(thumbNailUrl)
        .build();
  }

  private String extractArtistNames(final List<TransferredMusic> transferredMusics) {
    return transferredMusics.stream()
        .map(TransferredMusic::getArtistNames)
        .collect(joining(","));
  }
}
