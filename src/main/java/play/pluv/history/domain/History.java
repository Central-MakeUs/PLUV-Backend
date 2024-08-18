package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.base.BaseEntity;
import play.pluv.playlist.domain.MusicStreaming;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
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
}
