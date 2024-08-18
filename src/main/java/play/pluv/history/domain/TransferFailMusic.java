package play.pluv.history.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import play.pluv.base.BaseEntity;

@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class TransferFailMusic extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Long historyId;
  private String title;
  private String imageUrl;
  private String artistNames;

  @Builder
  public TransferFailMusic(
      final Long historyId, final String title, final String imageUrl, final String artistNames
  ) {
    this.historyId = historyId;
    this.title = title;
    this.imageUrl = imageUrl;
    this.artistNames = artistNames;
  }
}
