package play.pluv.music.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.transfer_context.application.MusicTransferContextManager;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorerComposite musicExplorerComposite;
  private final MusicTransferContextManager musicTransferContextManager;

  //TODO : 추후 필요없는 파라미터 삭제
  public List<MusicSearchResponse> searchMusics(
      final Long memberId, final MusicStreaming musicStreaming, final MusicSearchRequest request
  ) {
    final String accessToken = request.destinationAccessToken();
    final List<PlayListMusic> playListMusics = request.toPlayListMusics();

    return playListMusics.stream()
        .map(playListMusic -> searchMusic(musicStreaming, playListMusic, accessToken))
        .toList();
  }

  public void transferMusics(
      final Long memberId, final MusicAddRequest request, final MusicStreaming destination
  ) {
    final List<MusicId> musicIds = request.extractMusicIds(destination);
    musicTransferContextManager.initContext(
        memberId, request.getTransferFailMusics(), musicIds.size()
    );
    musicExplorerComposite.transferMusics(
        memberId, destination, request.destinationAccessToken(), musicIds, request.playListName()
    );
  }

  private MusicSearchResponse searchMusic(
      final MusicStreaming musicStreaming, final PlayListMusic playlistMusic,
      final String accessToken
  ) {
    final DestinationMusics destinationMusics = musicExplorerComposite
        .searchMusic(musicStreaming, accessToken, playlistMusic);
    musicTransferContextManager.putDestMusic(destinationMusics.toTransferredMusics());
    if (destinationMusics.isEmpty()) {
      return MusicSearchResponse.createNotFound(playlistMusic);
    }
    return MusicSearchResponse.createFound(playlistMusic, destinationMusics);
  }
}
