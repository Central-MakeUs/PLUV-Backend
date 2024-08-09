package play.pluv.oauth.spotify.dto;

import java.util.List;
import play.pluv.music.domain.DestinationMusic;

public record SpotifySearchMusicResponse(
    Track tracks
) {

  public List<DestinationMusic> toDestinationMusics() {
    return tracks.toMusics();
  }

  public record Track(
      List<SpotifyMusic> items
  ) {

    public List<DestinationMusic> toMusics() {
      return items.stream()
          .map(SpotifyMusic::toDestinationMusic)
          .toList();
    }
  }
}
