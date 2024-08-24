package play.pluv.feed.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import play.pluv.base.BaseEntity;

@ToString
@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Feed extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Long memberId;
  private String title;
  private String creatorName;
  private String artistNames;
  private String thumbNailUrl;
  private Boolean viewable;

  @Builder
  public Feed(final Long memberId, final String title,
      final String creatorName, final String artistNames,
      final String thumbNailUrl) {
    this.memberId = memberId;
    this.title = title;
    this.creatorName = creatorName;
    this.artistNames = artistNames;
    this.thumbNailUrl = thumbNailUrl;
    this.viewable = true;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Feed(
      final Long id, final Long memberId, final String title,
      final String creatorName, final String artistNames, final String thumbNailUrl
  ) {
    this.id = id;
    this.memberId = memberId;
    this.title = title;
    this.creatorName = creatorName;
    this.artistNames = artistNames;
    this.thumbNailUrl = thumbNailUrl;
    this.viewable = true;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
