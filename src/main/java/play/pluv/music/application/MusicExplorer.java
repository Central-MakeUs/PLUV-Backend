package play.pluv.music.application;

import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.SourceMusic;
import play.pluv.music.domain.MusicStreaming;

public interface MusicExplorer {

  Optional<DestinationMusic> searchMusic(final String accessToken, final SourceMusic query);

  MusicStreaming supportedType();
}
