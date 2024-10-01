package play.pluv.feed.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.base.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class FeedBookmark extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @ManyToOne
  @Getter
  private Feed feed;
  private Long memberId;

  public FeedBookmark(final Feed feed, final Long memberId) {
    this.feed = feed;
    this.memberId = memberId;
  }
}
