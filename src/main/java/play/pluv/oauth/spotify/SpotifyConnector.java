package play.pluv.oauth.spotify;

import static java.lang.String.format;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.music.application.MusicExplorer;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.music.domain.SourceMusic;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.PlayList;

@Component
@RequiredArgsConstructor
public class SpotifyConnector implements PlayListConnector, MusicExplorer {

  private static final String AUTHORIZATION_FORMAT = "Bearer %s";
  private static final Function<String, String> CREATE_AUTH_HEADER
      = (token) -> String.format(AUTHORIZATION_FORMAT, token);
  private static final String MUSIC_QUERY_FORMAT_NAME_ARTIST = "%s %s";
  private static final String MUSIC_QUERY_FORMAT_ISRC = "isrc:%s";

  private final SpotifyApiClient spotifyApiClient;
  private final SpotifyConfigProperty spotifyConfigProperty;

  @Override
  public List<PlayList> getPlayList(final String accessToken) {
    final SpotifyPlayListResponses response = spotifyApiClient.getPlayList(
        CREATE_AUTH_HEADER.apply(accessToken)
    );
    return response.toPlayLists();
  }

  @Override
  public MusicStreaming supportedType() {
    return SPOTIFY;
  }

  @Override
  public Optional<DestinationMusic> searchMusic(final String accessToken, final SourceMusic query) {
    final MultiValueMap<String, String> param = createRequestParamForSearchMusic(query);
    return spotifyApiClient.searchMusic(CREATE_AUTH_HEADER.apply(accessToken), param)
        .toMusic();
  }

  private String getAccessToken(final String authCode) {
    return spotifyApiClient.getAccessToken(createRequestParamForAccessToken(authCode))
        .accessToken();
  }

  private MultiValueMap<String, String> createRequestParamForAccessToken(final String authCode) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    param.add("grant_type", "authorization_code");
    param.add("code", authCode);
    param.add("redirect_uri", spotifyConfigProperty.redirectUri());
    param.add("client_id", spotifyConfigProperty.clientId());
    param.add("client_secret", spotifyConfigProperty.clientSecret());
    return param;
  }

  private MultiValueMap<String, String> createRequestParamForSearchMusic(final SourceMusic music) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

    final String artistName = String.join(",", music.getArtistNames());

    final String query = music.getIsrcCode()
        .map(isrc -> format(MUSIC_QUERY_FORMAT_ISRC, isrc))
        .orElseGet(() ->
            format(MUSIC_QUERY_FORMAT_NAME_ARTIST, music.getName(), artistName)
        );

    param.add("q", query);
    param.add("type", "track");
    param.add("market", "KR");
    return param;
  }
}
