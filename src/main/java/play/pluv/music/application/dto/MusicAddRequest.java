package play.pluv.music.application.dto;

import java.util.List;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;

public record MusicAddRequest(
    String playListName,
    String destinationAccessToken,
    List<String> musicIds
) {

  public List<MusicId> extractMusicIds(final MusicStreaming destination) {
    return musicIds.stream()
        .map(id -> new MusicId(destination, id))
        .toList();
  }
}
