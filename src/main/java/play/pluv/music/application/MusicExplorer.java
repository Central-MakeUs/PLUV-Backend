package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.music.domain.SourceMusic;
import play.pluv.playlist.domain.PlayListId;

public interface MusicExplorer {

  Optional<DestinationMusic> searchMusic(final String accessToken, final SourceMusic query);

  void addMusics(
      final String accessToken, final List<MusicId> musicIds, final PlayListId playListId
  );

  MusicStreaming supportedType();
}
