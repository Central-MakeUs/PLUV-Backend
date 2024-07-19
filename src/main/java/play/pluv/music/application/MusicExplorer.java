package play.pluv.music.application;

import java.util.Optional;
import play.pluv.music.domain.Music;
import play.pluv.music.domain.MusicQuery;
import play.pluv.music.domain.MusicStreaming;

public interface MusicExplorer {

  Optional<Music> searchMusic(final String accessToken, final MusicQuery query);

  MusicStreaming supportedType();
}
