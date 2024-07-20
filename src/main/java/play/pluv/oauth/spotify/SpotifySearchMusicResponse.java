package play.pluv.oauth.spotify;

import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.ThumbNailResponse;

public record SpotifySearchMusicResponse(
    Track tracks
) {

  public Optional<DestinationMusic> toMusic() {
    return tracks.toMusic();
  }

  private record Track(
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
          .name(spotifyMusic.name())
          .isrcCode(spotifyMusic.getIsrcCode())
          .build()
      );
    }

  }

  @JsonNaming(SnakeCaseStrategy.class)
  private record SpotifyMusic(
      String name,
      String id,
      Album album,
      ExternalId externalIds,
      List<Artist> artists
  ) {

    public String getImageUrl() {
      return album.images().get(0).url();
    }

    public List<String> getArtistNames() {
      return artists.stream()
          .map(Artist::name)
          .toList();
    }

    public String getIsrcCode() {
      return externalIds().isrc();
    }
  }

  private record Album(
      List<ThumbNailResponse> images
  ) {

  }

  private record ExternalId(
      String isrc
  ) {

  }

  private record Artist(
      String name
  ) {

  }
}
