package play.pluv.history.application.dto;

import play.pluv.history.domain.History;

public record HistoryDetailResponse(
    Long id,
    Integer totalSongCount,
    Integer transferredSongCount,
    String title,
    String imageUrl,
    String source,
    String destination
) {

  public static HistoryDetailResponse from(final History history) {
    return new HistoryDetailResponse(
        history.getId(),
        history.getTotalSongCount(),
        history.getTransferredSongCount(),
        history.getTitle(),
        history.getThumbNailUrl(),
        history.getSource().getName(),
        history.getDestination().getName()
    );
  }
}
