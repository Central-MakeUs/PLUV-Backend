package play.pluv.progress.application.dto;

import play.pluv.progress.domain.TransferProgress;

public record ProgressResponse(
    Integer willTransferMusicCount,
    Integer transferredMusicCount
) {

  public static ProgressResponse from(final TransferProgress progress) {
    return new ProgressResponse(
        progress.getWillTransferMusicCount(),
        progress.getTransferredMusicCount()
    );
  }
}
