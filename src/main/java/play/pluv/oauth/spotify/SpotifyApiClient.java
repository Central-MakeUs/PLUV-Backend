package play.pluv.oauth.spotify;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import play.pluv.oauth.spotify.dto.SpotifyAccessTokenResponse;
import play.pluv.oauth.spotify.dto.SpotifyAddMusicRequest;
import play.pluv.oauth.spotify.dto.SpotifyCreatePlayListRequest;
import play.pluv.oauth.spotify.dto.SpotifyCreatePlayListResponse;
import play.pluv.oauth.spotify.dto.SpotifyPlayListMusicResponses;
import play.pluv.oauth.spotify.dto.SpotifyPlayListResponses;
import play.pluv.oauth.spotify.dto.SpotifySearchMusicResponse;
import play.pluv.oauth.spotify.dto.SpotifyUserResponse;

public interface SpotifyApiClient {

  @GetExchange(url = "https://api.spotify.com/v1/me/playlists")
  SpotifyPlayListResponses getPlayList(@RequestHeader("Authorization") final String accessToken);

  @GetExchange(url = "https://api.spotify.com/v1/me")
  SpotifyUserResponse getUserProfile(@RequestHeader("Authorization") final String accessToken);

  @PostExchange(url = "https://api.spotify.com/v1/users/{userId}/playlists")
  SpotifyCreatePlayListResponse createPlayList(
      @RequestHeader("Authorization") final String accessToken,
      @PathVariable final String userId,
      @RequestBody final SpotifyCreatePlayListRequest request
  );

  //최대 100개까지만 한 번에 가능 //추후 webflux로 바꾸기
  @PostExchange(url = "https://api.spotify.com/v1/playlists/{playListId}/tracks")
  void addMusics(
      @RequestHeader("Authorization") final String accessToken,
      @PathVariable final String playListId,
      @RequestBody final SpotifyAddMusicRequest request
  );

  @PostExchange(url = "https://accounts.spotify.com/api/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
  SpotifyAccessTokenResponse getAccessToken(
      @RequestParam final MultiValueMap<String, String> params);

  @GetExchange(url = "https://api.spotify.com/v1/search")
  SpotifySearchMusicResponse searchMusic(
      @RequestHeader("Authorization") final String accessToken,
      @RequestParam final MultiValueMap<String, String> params
  );

  @GetExchange(url = "https://api.spotify.com/v1/playlists/{playListId}/tracks?market=KR&locale=ko")
  SpotifyPlayListMusicResponses getMusics(
      @PathVariable final String playListId,
      @RequestHeader("Authorization") final String accessToken
  );
}
