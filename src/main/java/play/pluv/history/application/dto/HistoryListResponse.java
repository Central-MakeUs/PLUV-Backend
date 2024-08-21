package play.pluv.history.application.dto;

import java.time.LocalDate;
import java.util.List;
import play.pluv.history.domain.History;

public record HistoryListResponse(
    Long id,
    Integer totalSongCount,
    LocalDate transferredDate,
    String title,
    String imageUrl
) {

  public static List<HistoryListResponse> createList(final List<History> histories) {
    return histories.stream()
        .map(HistoryListResponse::from)
        .toList();
  }

  private static HistoryListResponse from(final History history) {
    return new HistoryListResponse(
        history.getId(), history.getTotalSongCount(), history.getCreatedAt().toLocalDate(),
        history.getTitle(), history.getThumbNailUrl()
    );
  }
}
