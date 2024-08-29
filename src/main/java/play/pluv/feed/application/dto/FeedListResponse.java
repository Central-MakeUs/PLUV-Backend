package play.pluv.feed.application.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import play.pluv.feed.domain.Feed;

public record FeedListResponse(
    Long id,
    String title,
    String thumbNailUrl,
    String artistNames,
    String creatorName,
    @JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    LocalDate transferredAt,
    Integer totalSongCount
) {

  private static FeedListResponse from(final Feed feed) {
    return new FeedListResponse(
        feed.getId(), feed.getTitle(), feed.getThumbNailUrl(), feed.getArtistNames(),
        feed.getCreatorName(), feed.getCreatedAt().toLocalDate(), feed.getSongCount()
    );
  }

  public static List<FeedListResponse> createList(final List<Feed> feeds) {
    return feeds.stream()
        .map(FeedListResponse::from)
        .toList();
  }
}
