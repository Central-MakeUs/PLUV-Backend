package play.pluv.music.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.transfer_context.domain.TransferFailMusicInContext;

public record MusicAddRequest(
    @NotBlank String playListName,
    @NotBlank String destinationAccessToken,
    @NotNull List<String> musicIds,
    @Valid @NotNull List<TransferFailMusicRequest> transferFailMusics,
    @NotBlank String thumbNailUrl,
    @NotBlank String source
) {

  public MusicStreaming getSource() {
    return MusicStreaming.from(source);
  }

  public List<TransferFailMusicInContext> getTransferFailMusics() {
    return transferFailMusics.stream()
        .map(TransferFailMusicRequest::toTransferFailMusic)
        .toList();
  }

  public List<MusicId> extractMusicIds(final MusicStreaming destination) {
    return musicIds.stream()
        .map(id -> new MusicId(destination, id))
        .toList();
  }

  public record TransferFailMusicRequest(
      @NotBlank String title,
      @NotBlank String artistName,
      @NotBlank String imageUrl
  ) {

    private TransferFailMusicInContext toTransferFailMusic() {
      return new TransferFailMusicInContext(title, imageUrl, artistName);
    }
  }
}
