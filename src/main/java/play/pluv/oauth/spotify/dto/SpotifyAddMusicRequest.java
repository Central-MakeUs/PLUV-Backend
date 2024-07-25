package play.pluv.oauth.spotify.dto;

import java.util.List;
import java.util.function.Function;
import play.pluv.music.domain.MusicId;

public record SpotifyAddMusicRequest(
    List<String> uris
) {

  private static final Function<String, String> SPOTIFY_MUSIC_URI_CONVERT
      = (uri) -> String.format("spotify:track:%s", uri);

  public static SpotifyAddMusicRequest from(final List<MusicId> uris) {
    final List<String> convertedUris = uris.stream()
        .map(MusicId::id)
        .map(SPOTIFY_MUSIC_URI_CONVERT)
        .toList();
    return new SpotifyAddMusicRequest(convertedUris);
  }
}
