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
import play.pluv.oauth.spotify.SpotifySearchMusicResponse;
import play.pluv.oauth.spotify.SpotifySearchMusicResponse.Album;
import play.pluv.oauth.spotify.SpotifySearchMusicResponse.Artist;
import play.pluv.oauth.spotify.SpotifySearchMusicResponse.ExternalId;
import play.pluv.oauth.spotify.SpotifySearchMusicResponse.SpotifyMusic;
import play.pluv.oauth.spotify.SpotifySearchMusicResponse.Track;

public class SpotifyFixture {

  public static void mockingClient(final SpotifyApiClient spotifyApiClient) {
    when(spotifyApiClient.getAccessToken(any()))
        .thenReturn(new SpotifyAccessTokenResponse("accessToken"));

    final SpotifyPlayListResponses playList = 플레이리스트_조회_결과();
    when(spotifyApiClient.getPlayList(any())).thenReturn(playList);

    final SpotifySearchMusicResponse searchMusic = 음악검색_조회_결과();
    when(spotifyApiClient.searchMusic(any(), any())).thenReturn(searchMusic);
  }

  private static SpotifySearchMusicResponse 음악검색_조회_결과() {
    final Album album = new Album(List.of(new ThumbNailResponse(100, 100, "href")));
    final List<Artist> artists = List.of(new Artist("IU"));
    return new SpotifySearchMusicResponse(
        new Track(List.of(
            new SpotifyMusic("Good Day", "goodDayId", album, new ExternalId("KRA381001057"), artists)))
    );
  }

  private static SpotifyPlayListResponses 플레이리스트_조회_결과() {
    return new SpotifyPlayListResponses(List.of(
        new SpotifyPlayListResponse("uniqueId1", List.of(new ThumbNailResponse(50, 50, "url1"))
            , new TrackOverviewResponse("href1", 30), "플레이리스트 1"),
        new SpotifyPlayListResponse("uniqueId2", List.of(new ThumbNailResponse(50, 50, "url2"))
            , new TrackOverviewResponse("href2", 20), "플레이리스트 2")
    ));
  }
}
