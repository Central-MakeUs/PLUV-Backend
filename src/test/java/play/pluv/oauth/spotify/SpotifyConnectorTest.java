package play.pluv.oauth.spotify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static play.pluv.playlist.domain.PlayListProvider.SPOTIFY;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.ThumbNailResponse;
import play.pluv.oauth.spotify.SpotifyPlayListResponses.SpotifyPlayListResponse.TrackOverviewResponse;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.support.ApplicationTest;

class SpotifyConnectorTest extends ApplicationTest {

  @Autowired
  private SpotifyApiClient spotifyApiClient;
  @Autowired
  private SpotifyConnector spotifyConnector;

  @Test
  void 정상적으로_PlayList_객체를_반환한다() {
    final SpotifyPlayListResponses responses = new SpotifyPlayListResponses(List.of(
        new SpotifyPlayListResponse("uniqueId1", List.of(new ThumbNailResponse(50, 50, "url1"))
            , new TrackOverviewResponse("href1", 30), "플레이리스트 1"),
        new SpotifyPlayListResponse("uniqueId2", List.of(new ThumbNailResponse(50, 50, "url2"))
            , new TrackOverviewResponse("href2", 20), "플레이리스트 2")
    ));
    mockingApiClient(responses);
    final List<PlayList> expected = List.of(
        new PlayList(new PlayListId("uniqueId1", SPOTIFY), "플레이리스트 1", "url1", 30),
        new PlayList(new PlayListId("uniqueId2", SPOTIFY), "플레이리스트 2", "url2", 20)
    );

    final List<PlayList> actual = spotifyConnector.getPlayList("authCode");

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  private void mockingApiClient(final SpotifyPlayListResponses responses) {
    final String accessToken = "accessToken";
    when(spotifyApiClient.getAccessToken(any())).thenReturn(
        new SpotifyAccessTokenResponse(accessToken))
    ;
    when(spotifyApiClient.getPlayList(any())).thenReturn(responses);
  }
}