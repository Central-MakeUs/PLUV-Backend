package play.pluv.oauth.apple.dto;

import play.pluv.music.domain.DestinationMusics;

public record AppleSearchMusicResponses(
    AppleMusicSearchResult results
) {

  public DestinationMusics toDestinationMusics() {
    return results.songs().toDestinationMusics();
  }

  private record AppleMusicSearchResult(
      AppleMusicSongs songs
  ) {

  }

}
