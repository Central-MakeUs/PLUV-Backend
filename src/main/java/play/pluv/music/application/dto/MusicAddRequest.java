package play.pluv.music.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;

public record MusicAddRequest(
    @NotBlank String playListName,
    @NotBlank String destinationAccessToken,
    @NotBlank List<String> musicIds
) {

  public List<MusicId> extractMusicIds(final MusicStreaming destination) {
    return musicIds.stream()
        .map(id -> new MusicId(destination, id))
        .toList();
  }
}
