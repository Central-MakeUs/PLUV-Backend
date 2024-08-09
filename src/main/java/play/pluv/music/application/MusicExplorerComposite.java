package play.pluv.music.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static play.pluv.music.exception.MusicExceptionType.MUSIC_STREAMING_NOT_FOUND;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;
import play.pluv.music.exception.MusicException;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

@Component
public class MusicExplorerComposite {

  private final Map<MusicStreaming, MusicExplorer> musicExplorerMap;

  public MusicExplorerComposite(final Set<MusicExplorer> musicExplorers) {
    this.musicExplorerMap = musicExplorers.stream()
        .collect(toMap(MusicExplorer::supportedType, identity()));
  }

  public Optional<DestinationMusic> searchMusic(
      final MusicStreaming musicStreaming, final String accessToken, final PlayListMusic source
  ) {
    return getClient(musicStreaming).searchMusic(accessToken, source);
  }

  public void transferMusics(
      final MusicStreaming destination, final String accessToken, final List<MusicId> musicIds,
      final String playListName
  ) {
    getClient(destination).transferMusics(accessToken, musicIds, playListName);
  }

  private MusicExplorer getClient(final MusicStreaming serverType) {
    return Optional.ofNullable(musicExplorerMap.get(serverType))
        .orElseThrow(() -> new MusicException(MUSIC_STREAMING_NOT_FOUND));
  }
}
