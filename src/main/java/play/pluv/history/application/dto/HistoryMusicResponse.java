package play.pluv.history.application.dto;

import java.util.List;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;

public record HistoryMusicResponse(
    String title,
    String imageUrl,
    String artistNames
) {

  public static HistoryMusicResponse from(final TransferredMusic transferredMusic) {
    return new HistoryMusicResponse(
        transferredMusic.getTitle(), transferredMusic.getImageUrl(),
        transferredMusic.getArtistNames()
    );
  }

  public static HistoryMusicResponse from(final TransferFailMusic transferFailMusic) {
    return new HistoryMusicResponse(
        transferFailMusic.getTitle(), transferFailMusic.getImageUrl(),
        transferFailMusic.getArtistNames()
    );
  }

  public static List<HistoryMusicResponse> createListFromTransferFail(
      final List<TransferFailMusic> transferFailMusics
  ) {
    return transferFailMusics.stream()
        .map(HistoryMusicResponse::from)
        .toList();
  }

  public static List<HistoryMusicResponse> createListFromTransferred(
      final List<TransferredMusic> transferredMusics
  ) {
    return transferredMusics.stream()
        .map(HistoryMusicResponse::from)
        .toList();
  }
}
