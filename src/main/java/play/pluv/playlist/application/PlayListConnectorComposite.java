package play.pluv.playlist.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static play.pluv.playlist.exception.PlayListExceptionType.PLAYLIST_PROVIDER_NOT_FOUND;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.playlist.exception.PlayListException;

@Component
public class PlayListConnectorComposite {

  private final Map<MusicStreaming, PlayListConnector> playListConnectorMap;

  public PlayListConnectorComposite(final Set<PlayListConnector> playListConnectors) {
    this.playListConnectorMap = playListConnectors.stream()
        .collect(toMap(PlayListConnector::supportedType, identity()));
  }

  public List<PlayList> getPlayList(final MusicStreaming serverType, final String accessToken) {
    return getClient(serverType).getPlayList(accessToken);
  }

  public List<PlayListMusic> getMusics(
      final MusicStreaming serverType, final String accessToken, final String playListId
  ) {
    return getClient(serverType).getMusics(playListId, accessToken);
  }

  private PlayListConnector getClient(final MusicStreaming serverType) {
    return Optional.ofNullable(playListConnectorMap.get(serverType))
        .orElseThrow(() -> new PlayListException(PLAYLIST_PROVIDER_NOT_FOUND));
  }
}
