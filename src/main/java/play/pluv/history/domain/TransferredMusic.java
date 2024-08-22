package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import play.pluv.base.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString
public class TransferredMusic extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Long historyId;
  @Getter
  private String title;
  @Getter
  private String imageUrl;
  @Getter
  private String artistNames;
  @Embedded
  private HistoryMusicId musicId;

  @Builder
  public TransferredMusic(final Long historyId, final String title, final String imageUrl,
      final String artistNames,
      final HistoryMusicId musicId) {
    this.historyId = historyId;
    this.title = title;
    this.imageUrl = imageUrl;
    this.artistNames = artistNames;
    this.musicId = musicId;
  }
}
