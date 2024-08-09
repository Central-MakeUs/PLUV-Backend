package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

public interface MusicExplorer {

  DestinationMusics searchMusic(final String accessToken, final PlayListMusic source);

  void transferMusics(
      final String accessToken, final List<MusicId> musicIds, final String playlistName
  );

  MusicStreaming supportedType();
}
