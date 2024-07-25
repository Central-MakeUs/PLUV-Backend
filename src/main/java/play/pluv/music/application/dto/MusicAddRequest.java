package play.pluv.music.application.dto;

import java.util.List;
import play.pluv.music.domain.MusicId;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListId;

public record MusicAddRequest(
    String destinationAccessToken,
    List<String> musicIds,
    String playListId,
    MusicStreaming destination
) {

  public List<MusicId> getMusicIds() {
    return musicIds.stream()
        .map(id -> new MusicId(destination, id))
        .toList();
  }

  public PlayListId getPlayListId() {
    return new PlayListId(playListId, destination);
  }
}
