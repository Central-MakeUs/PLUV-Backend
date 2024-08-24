package play.pluv.feed.application.dto;

import java.time.LocalDate;
import play.pluv.feed.domain.Feed;

public record FeedDetailResponse(
    Long id,
    Integer songCount,
    String title,
    String imageUrl,
    String creatorName,
    Boolean isBookMarked,
    LocalDate createdAt
) {

  public static FeedDetailResponse from(final Feed feed, final Boolean isBookMarked) {
    return new FeedDetailResponse(
        feed.getId(), feed.getSongCount(), feed.getTitle(), feed.getThumbNailUrl(),
        feed.getCreatorName(), isBookMarked, feed.getCreatedAt().toLocalDate()
    );
  }
}
