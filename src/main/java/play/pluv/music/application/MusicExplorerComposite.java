package play.pluv.music.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static play.pluv.music.exception.MusicExceptionType.MUSIC_STREAMING_NOT_FOUND;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.music.domain.SourceMusic;
import play.pluv.music.exception.MusicException;

@Component
public class MusicExplorerComposite {

  private final Map<MusicStreaming, MusicExplorer> musicExplorerMap;

  public MusicExplorerComposite(final Set<MusicExplorer> musicExplorers) {
    this.musicExplorerMap = musicExplorers.stream()
        .collect(toMap(MusicExplorer::supportedType, identity()));
  }

  public Optional<DestinationMusic> searchMusic(
      final MusicStreaming musicStreaming, final String accessToken, final SourceMusic query
  ) {
    return getClient(musicStreaming).searchMusic(accessToken, query);
  }

  private MusicExplorer getClient(final MusicStreaming serverType) {
    return Optional.ofNullable(musicExplorerMap.get(serverType))
        .orElseThrow(() -> new MusicException(MUSIC_STREAMING_NOT_FOUND));
  }
}
