package play.pluv.history.application.dto;

import java.util.List;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;

public record MusicResponse(
    String title,
    String imageUrl,
    String artistNames,
    String isrcCode
) {

  public static MusicResponse from(final TransferredMusic transferredMusic) {
    return new MusicResponse(
        transferredMusic.getTitle(), transferredMusic.getImageUrl(),
        transferredMusic.getArtistNames(), transferredMusic.getIsrcCode()
    );
  }

  public static MusicResponse from(final TransferFailMusic transferFailMusic) {
    return new MusicResponse(
        transferFailMusic.getTitle(), transferFailMusic.getImageUrl(),
        transferFailMusic.getArtistNames(), null
    );
  }

  public static List<MusicResponse> createListFromTransferFail(
      final List<TransferFailMusic> transferFailMusics
  ) {
    return transferFailMusics.stream()
        .map(MusicResponse::from)
        .toList();
  }

  public static List<MusicResponse> createListFromTransferred(
      final List<TransferredMusic> transferredMusics
  ) {
    return transferredMusics.stream()
        .map(MusicResponse::from)
        .toList();
  }
}
