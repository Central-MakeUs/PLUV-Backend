package play.pluv.transfer_context.application.dto;

import play.pluv.transfer_context.domain.TransferProgress;

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
