package play.pluv.oauth.spotify.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record SpotifyMusic(
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

  public record Album(
      List<ThumbNailResponse> images
  ) {

  }

  public record ExternalId(
      String isrc
  ) {

  }

  public record Artist(
      String name
  ) {

  }
}
