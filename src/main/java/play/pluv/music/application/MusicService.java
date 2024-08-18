package play.pluv.music.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.application.HistoryUpdater;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.music.domain.MusicTransferContext;
import play.pluv.music.domain.repository.MusicTransferContextRepository;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorerComposite musicExplorerComposite;
  private final MusicTransferContextRepository musicTransferContextRepository;
  private final HistoryUpdater historyUpdater;

  public List<MusicSearchResponse> searchMusics(
      final Long memberId, final MusicStreaming musicStreaming, final MusicSearchRequest request
  ) {
    final String accessToken = request.destinationAccessToken();
    final List<PlayListMusic> playListMusics = request.toPlayListMusics();

    musicTransferContextRepository.setSearchMusics(memberId, playListMusics);

    return playListMusics.stream()
        .map(playListMusic -> searchMusic(musicStreaming, playListMusic, accessToken))
        .toList();
  }

  public void transferMusics(final MusicAddRequest request, final MusicStreaming destination) {
    final List<MusicId> musicIds = request.extractMusicIds(destination);

    musicExplorerComposite.transferMusics(
        destination, request.destinationAccessToken(), musicIds, request.playListName()
    );
  }

  @Transactional
  public void transferMusics(
      final Long memberId, final MusicAddRequest request, final MusicStreaming destination
  ) {
    final List<MusicId> musicIds = request.extractMusicIds(destination);

    final MusicTransferContext context = musicTransferContextRepository.getContext(memberId);

    musicExplorerComposite.transferMusics(
        destination, request.destinationAccessToken(), musicIds, request.playListName()
    );
  }

  private MusicSearchResponse searchMusic(
      final MusicStreaming musicStreaming, final PlayListMusic playlistMusic,
      final String accessToken
  ) {
    final DestinationMusics destinationMusics = musicExplorerComposite
        .searchMusic(musicStreaming, accessToken, playlistMusic);
    if (destinationMusics.isEmpty()) {
      return MusicSearchResponse.createNotFound(playlistMusic);
    }
    return MusicSearchResponse.createFound(playlistMusic, destinationMusics);
  }
}
