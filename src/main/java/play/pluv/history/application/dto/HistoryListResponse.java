package play.pluv.history.application.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.Comparator.comparing;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import play.pluv.history.domain.History;

public record HistoryListResponse(
    Long id,
    Integer transferredSongCount,
    @JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    LocalDate transferredDate,
    String title,
    String imageUrl
) {

  public static List<HistoryListResponse> createList(final List<History> histories) {
    return histories.stream()
        .map(HistoryListResponse::from)
        .sorted(comparing(HistoryListResponse::transferredDate).reversed())
        .toList();
  }

  private static HistoryListResponse from(final History history) {
    return new HistoryListResponse(
        history.getId(), history.getTransferredSongCount(), history.getCreatedAt().toLocalDate(),
        history.getTitle(), history.getThumbNailUrl()
    );
  }
}
