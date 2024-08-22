package play.pluv.feed.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.base.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Feed extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Long historyId;
  private Long memberId;
  private String title;
  private String creatorName;
  private String artistNames;
  private String thumbNailUrl;
  private Boolean viewable;

  @Builder
  public Feed(final Long historyId, final Long memberId, final String title,
      final String creatorName, final String artistNames,
      final String thumbNailUrl, final Boolean viewable) {
    this.historyId = historyId;
    this.memberId = memberId;
    this.title = title;
    this.creatorName = creatorName;
    this.artistNames = artistNames;
    this.thumbNailUrl = thumbNailUrl;
    this.viewable = viewable;
  }
}
