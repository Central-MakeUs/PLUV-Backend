package play.pluv.oauth.spotify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.ThumbNailResponse;

public record SpotifySearchMusicResponse(
    Track tracks
) {

  private record Track(
      List<SpotifyMusic> items
  ) {

  }

  @JsonNaming(SnakeCaseStrategy.class)
  private record SpotifyMusic(
      String name,
      String id,
      Album album,
      ExternalId externalIds,
      List<Artist> artists
  ) {

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
