package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.music.domain.SourceMusic;

public interface MusicExplorer {

  Optional<DestinationMusic> searchMusic(final String accessToken, final SourceMusic query);

  void transferMusics(
      final String accessToken, final List<MusicId> musicIds, final String playlistName
  );

  MusicStreaming supportedType();
}
