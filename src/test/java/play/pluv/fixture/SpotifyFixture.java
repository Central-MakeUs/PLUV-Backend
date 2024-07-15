package play.pluv.fixture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import play.pluv.oauth.spotify.SpotifyAccessTokenResponse;
import play.pluv.oauth.spotify.SpotifyApiClient;
import play.pluv.oauth.spotify.SpotifyPlayListResponses;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.ThumbNailResponse;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.TrackOverviewResponse;

public class SpotifyFixture {

  public static void mockingClient(final SpotifyApiClient spotifyApiClient) {
    final SpotifyPlayListResponses responses = new SpotifyPlayListResponses(List.of(
        new SpotifyPlayListResponse("uniqueId1", List.of(new ThumbNailResponse(50, 50, "url1"))
            , new TrackOverviewResponse("href1", 30), "플레이리스트 1"),
        new SpotifyPlayListResponse("uniqueId2", List.of(new ThumbNailResponse(50, 50, "url2"))
            , new TrackOverviewResponse("href2", 20), "플레이리스트 2")
    ));
    when(spotifyApiClient.getAccessToken(any())).thenReturn(
        new SpotifyAccessTokenResponse("accessToken"))
    ;
    when(spotifyApiClient.getPlayList(any())).thenReturn(responses);
  }
}
