package play.pluv.oauth.spotify.dto;

import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;

public record SpotifySearchMusicResponse(
    Track tracks
) {

  public Optional<DestinationMusic> toMusic() {
    return tracks.toMusic();
  }

  public record Track(
      List<SpotifyMusic> items
  ) {

    public Optional<DestinationMusic> toMusic() {
      if (items.isEmpty()) {
        return Optional.empty();
      }

      final SpotifyMusic spotifyMusic = items.get(0);

      return Optional.of(DestinationMusic.builder()
          .musicId(new MusicId(SPOTIFY, spotifyMusic.id()))
          .imageUrl(spotifyMusic.getImageUrl())
          .artistNames(spotifyMusic.getArtistNames())
          .title(spotifyMusic.name())
          .isrcCode(spotifyMusic.getIsrcCode())
          .build()
      );
    }

  }
}
