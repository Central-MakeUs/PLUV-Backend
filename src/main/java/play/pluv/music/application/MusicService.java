package play.pluv.music.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.transfer_context.application.MusicTransferContextManager;
import play.pluv.transfer_context.domain.MusicTransferContext;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorerComposite musicExplorerComposite;
  private final MusicTransferContextManager musicTransferContextManager;

  public List<MusicSearchResponse> searchMusics(
      final MusicStreaming musicStreaming, final MusicSearchRequest request
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
    initTransferContext(memberId, request, destination, musicIds.size());
    musicExplorerComposite.transferMusics(
        memberId, destination, request.destinationAccessToken(), musicIds, request.playListName()
    );
  }

  private void initTransferContext(
      final Long memberId, final MusicAddRequest request, final MusicStreaming destination,
      final Integer willTransferMusicCount
  ) {
    final MusicTransferContext context = MusicTransferContext.builder()
        .transferFailMusics(request.toTransferFailMusics())
        .title(request.playListName())
        .willTransferMusicCount(willTransferMusicCount)
        .destination(destination)
        .source(request.toSource())
        .memberId(memberId)
        .thumbNailUrl(request.thumbNailUrl())
        .build();
    musicTransferContextManager.initContext(context);
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
