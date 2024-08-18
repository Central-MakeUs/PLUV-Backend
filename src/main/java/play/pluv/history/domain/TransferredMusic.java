package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import play.pluv.base.BaseEntity;
import play.pluv.music.domain.MusicId;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString
public class TransferredMusic extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Long historyId;
  private String title;
  private String imageUrl;
  private String artistNames;
  @Embedded
  private MusicId musicId;

  @Builder
  public TransferredMusic(final Long historyId, final String title, final String imageUrl,
      final String artistNames,
      final MusicId musicId) {
    this.historyId = historyId;
    this.title = title;
    this.imageUrl = imageUrl;
    this.artistNames = artistNames;
    this.musicId = musicId;
  }
}
