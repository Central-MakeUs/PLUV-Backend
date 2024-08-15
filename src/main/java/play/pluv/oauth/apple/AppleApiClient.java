package play.pluv.oauth.apple;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import play.pluv.oauth.apple.dto.ApplePlayListMusicResponses;
import play.pluv.oauth.apple.dto.ApplePlayListResponses;
import play.pluv.oauth.apple.dto.AppleSearchMusicResponses;
import play.pluv.oauth.apple.dto.AppleTokenResponse;

public interface AppleApiClient {

  @PostExchange(url = "https://appleid.apple.com/auth/oauth2/v2/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
  AppleTokenResponse fetchToken(@RequestParam final MultiValueMap<String, String> params);

  @GetExchange("https://api.music.apple.com/v1/me/library/playlists?l=ko")
  ApplePlayListResponses getPlayList(
      @RequestHeader("Authorization") final String developerToken,
      @RequestHeader("Music-User-Token") final String musicUserToken
  );

  @GetExchange("https://api.music.apple.com/v1/me/library/playlists/{id}/tracks?l=ko")
  ApplePlayListMusicResponses getMusics(
      @RequestHeader("Authorization") final String developerToken,
      @RequestHeader("Music-User-Token") final String musicUserToken,
      @PathVariable final String id
  );

  @GetExchange("https://api.music.apple.com/v1/catalog/kr/search?types=songs&l=ko")
  AppleSearchMusicResponses searchMusic(
      @RequestHeader("Authorization") final String developerToken,
      @RequestParam final String term
  );
}
