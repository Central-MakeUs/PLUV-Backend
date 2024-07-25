package play.pluv.music.application.dto;

import java.util.List;
import play.pluv.music.domain.MusicId;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListId;

public record MusicAddRequest(
    String destinationAccessToken,
    List<String> musicIds,
    String playListId
) {

  public List<MusicId> extractMusicIds(final MusicStreaming destination) {
    return musicIds.stream()
        .map(id -> new MusicId(destination, id))
        .toList();
  }

  public PlayListId extractPlayListId(final MusicStreaming destination) {
    return new PlayListId(playListId, destination);
  }
}
