package play.pluv.oauth.google;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import play.pluv.oauth.google.dto.GoogleIdTokenResponse;
import play.pluv.oauth.google.dto.GoogleOAuthResponse;
import play.pluv.oauth.google.dto.YoutubeAddMusicRequest;
import play.pluv.oauth.google.dto.YoutubeCreatePlayListRequest;
import play.pluv.oauth.google.dto.YoutubeCreatePlayListResponse;
import play.pluv.oauth.google.dto.YoutubeMusicResponses;
import play.pluv.oauth.google.dto.YoutubePlayListResponses;
import play.pluv.oauth.google.dto.YoutubeSearchMusicResponses;

public interface GoogleApiClient {

  @GetExchange("https://oauth2.googleapis.com/tokeninfo")
  GoogleIdTokenResponse verifyIdToken(@RequestParam("id_token") final String idToken);

  @PostExchange(url = "https://oauth2.googleapis.com/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
  GoogleOAuthResponse fetchToken(@RequestParam final MultiValueMap<String, String> params);

  @GetExchange("https://www.googleapis.com/youtube/v3/playlists?part=snippet&mine=true&maxResults=50")
  YoutubePlayListResponses getPlayList(@RequestHeader("Authorization") final String accessToken);

  @GetExchange("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50")
  YoutubeMusicResponses getPlayListItems(
      @RequestHeader("Authorization") final String accessToken,
      @RequestParam final String playlistId, @RequestParam(required = false) final String pageToken
  );

  @GetExchange(url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&type=video")
  YoutubeSearchMusicResponses searchMusic(
      @RequestHeader("Authorization") final String accessToken, @RequestParam final String q
  );

  @PostExchange(url = "https://www.googleapis.com/youtube/v3/playlists?part=snippet")
  YoutubeCreatePlayListResponse createPlayList(
      @RequestHeader("Authorization") final String accessToken,
      @RequestBody final YoutubeCreatePlayListRequest request
  );

  @PostExchange(url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet")
  void addMusic(
      @RequestHeader("Authorization") final String accessToken,
      @RequestBody final YoutubeAddMusicRequest request
  );
}
