package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.base.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class History extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String title;

  private String thumbNailUrl;

  private int transferredSongCount;

  private int totalSongCount;

  private Long memberId;

  @Builder
  public History(
      final String title, final String thumbNailUrl, final int transferredSongCount,
      final int totalSongCount, final Long memberId
  ) {
    this.title = title;
    this.thumbNailUrl = thumbNailUrl;
    this.transferredSongCount = transferredSongCount;
    this.totalSongCount = totalSongCount;
    this.memberId = memberId;
  }
}
