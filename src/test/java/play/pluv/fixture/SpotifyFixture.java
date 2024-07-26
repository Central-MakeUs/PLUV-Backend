package play.pluv.fixture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import play.pluv.oauth.spotify.SpotifyApiClient;
import play.pluv.oauth.spotify.dto.SpotifyAccessTokenResponse;
import play.pluv.oauth.spotify.dto.SpotifyMusic;
import play.pluv.oauth.spotify.dto.SpotifyMusic.Album;
import play.pluv.oauth.spotify.dto.SpotifyMusic.Artist;
import play.pluv.oauth.spotify.dto.SpotifyMusic.ExternalId;
import play.pluv.oauth.spotify.dto.SpotifyPlayListResponses;
import play.pluv.oauth.spotify.dto.SpotifyPlayListResponses.SpotifyPlayListResponse;
import play.pluv.oauth.spotify.dto.SpotifyPlayListResponses.SpotifyPlayListResponse.TrackOverviewResponse;
import play.pluv.oauth.spotify.dto.SpotifySearchMusicResponse;
import play.pluv.oauth.spotify.dto.SpotifySearchMusicResponse.Track;
import play.pluv.oauth.spotify.dto.SpotifyUserResponse;
import play.pluv.oauth.spotify.dto.ThumbNailResponse;

public class SpotifyFixture {

  public static final String USER_ID = "1234";

  public static void mockingClient(final SpotifyApiClient spotifyApiClient) {
    when(spotifyApiClient.getAccessToken(any()))
        .thenReturn(new SpotifyAccessTokenResponse("accessToken"));

    final SpotifyPlayListResponses playList = 플레이리스트_조회_결과();
    when(spotifyApiClient.getPlayList(any())).thenReturn(playList);

    final SpotifySearchMusicResponse searchMusic = 음악검색_조회_결과();
    when(spotifyApiClient.searchMusic(any(), any())).thenReturn(searchMusic);

    when(spotifyApiClient.getUserProfile(any()))
        .thenReturn(new SpotifyUserResponse(USER_ID));
  }

  private static SpotifySearchMusicResponse 음악검색_조회_결과() {
    final Album album = new Album(List.of(new ThumbNailResponse(100, 100, "href")));
    final List<Artist> artists = List.of(new Artist("IU"));
    return new SpotifySearchMusicResponse(
        new Track(List.of(
            new SpotifyMusic("Good Day", "goodDayId", album, new ExternalId("KRA381001057"),
                artists)))
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
