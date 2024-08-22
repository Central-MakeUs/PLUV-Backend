package play.pluv.oauth.spotify;

import static java.lang.String.format;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.music.application.MusicExplorer;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.oauth.spotify.dto.SpotifyAddMusicRequest;
import play.pluv.oauth.spotify.dto.SpotifyCreatePlayListRequest;
import play.pluv.oauth.spotify.dto.SpotifyCreatePlayListResponse;
import play.pluv.oauth.spotify.dto.SpotifyPlayListResponses;
import play.pluv.oauth.spotify.dto.SpotifyUserResponse;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.progress.application.MusicTransferContextManager;

@Component
@RequiredArgsConstructor
public class SpotifyConnector implements PlayListConnector, MusicExplorer, SocialLoginClient {

  private static final Integer MUSIC_ID_MAX_SIZE = 100;
  private static final String AUTHORIZATION_FORMAT = "Bearer %s";
  private static final Function<String, String> CREATE_AUTH_HEADER
      = (token) -> String.format(AUTHORIZATION_FORMAT, token);
  private static final String MUSIC_QUERY_FORMAT_NAME_ARTIST = "%s %s";
  private static final String MUSIC_QUERY_FORMAT_ISRC = "isrc:%s";

  private final SpotifyApiClient spotifyApiClient;
  private final SpotifyConfigProperty spotifyConfigProperty;
  private final MusicTransferContextManager musicTransferContextManager;

  @Override
  public List<PlayList> getPlayList(final String accessToken) {
    final SpotifyPlayListResponses response = spotifyApiClient.getPlayList(
        CREATE_AUTH_HEADER.apply(accessToken)
    );
    return response.toPlayLists();
  }

  @Override
  public OAuthMemberInfo fetchMember(final String accessToken) {
    return spotifyApiClient.getUserProfile(CREATE_AUTH_HEADER.apply(accessToken))
        .toOAuthMemberInfo();
  }

  @Override
  public MusicStreaming supportedType() {
    return SPOTIFY;
  }

  @Override
  public DestinationMusics searchMusic(
      final String accessToken, final PlayListMusic source
  ) {
    final MultiValueMap<String, String> param = createRequestParamForSearchMusic(source);
    return spotifyApiClient.searchMusic(CREATE_AUTH_HEADER.apply(accessToken), param)
        .toDestinationMusics();
  }

  @Override
  public void transferMusics(
      final Long memberId, final String accessToken, final List<MusicId> musicIds,
      final String playlistName
  ) {
    final PlayListId playlistId = createPlayList(accessToken, playlistName);

    final List<List<MusicId>> requests = splitMusicIds(musicIds);

    requests.parallelStream()
        .forEach(request -> addMusics(memberId, accessToken, request, playlistId));
    musicTransferContextManager.saveTransferHistory(memberId);
  }

  private void addMusics(
      final Long memberId, final String accessToken, final List<MusicId> musicIds,
      final PlayListId playlistId
  ) {
    final SpotifyAddMusicRequest request = SpotifyAddMusicRequest.from(musicIds);
    spotifyApiClient.addMusics(
        CREATE_AUTH_HEADER.apply(accessToken), playlistId.id(), request
    );
    final var historyMusicIds = musicIds.stream()
        .map(subMusicId -> new HistoryMusicId(subMusicId.musicStreaming(), subMusicId.id()))
        .toList();
    musicTransferContextManager.addTransferredMusics(memberId, historyMusicIds);
  }

  private List<List<MusicId>> splitMusicIds(final List<MusicId> musicIds) {
    final List<List<MusicId>> partitionedMusicIds = new ArrayList<>();
    for (int i = 0; i < musicIds.size(); i += MUSIC_ID_MAX_SIZE) {
      partitionedMusicIds.add(
          musicIds.subList(i, Math.min(i + MUSIC_ID_MAX_SIZE, musicIds.size()))
      );
    }
    return partitionedMusicIds;
  }

  public String getAccessToken(final String authCode) {
    return spotifyApiClient.getAccessToken(createRequestParamForAccessToken(authCode))
        .accessToken();
  }

  public List<PlayListMusic> getMusics(final String playListId, final String accessToken) {
    return spotifyApiClient.getMusics(playListId, CREATE_AUTH_HEADER.apply(accessToken))
        .toMusics();
  }

  @Override
  public PlayListId createPlayList(final String accessToken, final String name) {
    final String authorization = CREATE_AUTH_HEADER.apply(accessToken);
    final SpotifyUserResponse userProfile = spotifyApiClient.getUserProfile(authorization);
    final SpotifyCreatePlayListResponse response = spotifyApiClient.createPlayList(
        authorization, userProfile.id(), new SpotifyCreatePlayListRequest(name)
    );
    return new PlayListId(response.id(), SPOTIFY);
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

  private MultiValueMap<String, String> createRequestParamForSearchMusic(
      final PlayListMusic music
  ) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

    final String artistName = String.join(",", music.getArtistNames());

    final String query = music.getIsrcCode()
        .map(isrc -> format(MUSIC_QUERY_FORMAT_ISRC, isrc))
        .orElseGet(() ->
            format(MUSIC_QUERY_FORMAT_NAME_ARTIST, music.getTitle(), artistName)
        );

    param.add("q", query);
    param.add("type", "track");
    param.add("market", "KR");
    param.add("locale", "ko");
    return param;
  }
}
