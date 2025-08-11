package play.pluv.oauth.spotify.dto;

import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record SpotifyPlayListMusicResponses(
    List<SpotifyPlayListItem> items
) {

  public List<PlayListMusic> toMusics() {
    return items.stream()
        .map(SpotifyPlayListItem::toMusic)
        .toList();
  }

  public record SpotifyPlayListItem(
      SpotifyMusic track
  ) {

    public PlayListMusic toMusic() {
      return new PlayListMusic(
          track.name(),
          track.getArtistNames(),
          track.getImageUrl(),
          track.getIsrcCode()
      );
    }
  }
}
