package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorerComposite musicExplorerComposite;

  public List<MusicSearchResponse> searchMusics(
      final MusicStreaming musicStreaming, final MusicSearchRequest request
  ) {
    final String accessToken = request.destinationAccessToken();

    return request.musics().parallelStream()
        .map(MusicQuery::toDomain)
        .map(playListMusic -> searchMusic(musicStreaming, playListMusic, accessToken))
        .toList();
  }

  public void transferMusics(final MusicAddRequest request, final MusicStreaming destination) {
    final List<MusicId> musicIds = request.extractMusicIds(destination);

    musicExplorerComposite.transferMusics(
        destination, request.destinationAccessToken(), musicIds, request.playListName()
    );
  }

  private MusicSearchResponse searchMusic(
      final MusicStreaming musicStreaming, final PlayListMusic playlistMusic, final String accessToken
  ) {
    final Optional<DestinationMusic> result
        = musicExplorerComposite.searchMusic(musicStreaming, accessToken, playlistMusic);
    return result
        .map(
            destination -> MusicSearchResponse.createFound(playlistMusic, destination)
        )
        .orElseGet(() -> MusicSearchResponse.createNotFound(playlistMusic));
  }
}
