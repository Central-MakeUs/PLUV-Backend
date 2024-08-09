package play.pluv.oauth.spotify.dto;

import java.util.List;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;

public record SpotifySearchMusicResponse(
    Track tracks
) {

  public DestinationMusics toDestinationMusics() {
    return new DestinationMusics(tracks.toMusics());
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
