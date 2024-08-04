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
import play.pluv.music.domain.MusicStreaming;
import play.pluv.music.domain.SourceMusic;
import play.pluv.oauth.spotify.SpotifyConnector;
import play.pluv.playlist.domain.PlayListId;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final SpotifyConnector musicExplorer;
  private final MusicExplorerComposite musicExplorerComposite;

  public List<MusicSearchResponse> searchMusics(
      final MusicStreaming musicStreaming, final MusicSearchRequest request
  ) {
    final String accessToken = request.destinationAccessToken();

    return request.musics().parallelStream()
        .map(MusicQuery::toDomain)
        .map(sourceMusic -> searchMusic(musicStreaming, sourceMusic, accessToken))
        .toList();
  }

  public void addMusics(final MusicAddRequest request, final String destinationName) {
    final MusicStreaming destination = MusicStreaming.from(destinationName);
    final List<MusicId> musicIds = request.extractMusicIds(destination);
    final PlayListId playListId = request.extractPlayListId(destination);

    musicExplorer.addMusics(request.destinationAccessToken(), musicIds, playListId);
  }

  private MusicSearchResponse searchMusic(
      final MusicStreaming musicStreaming, final SourceMusic sourceMusic, final String accessToken
  ) {
    final Optional<DestinationMusic> result
        = musicExplorerComposite.searchMusic(musicStreaming, accessToken, sourceMusic);
    return result
        .map(
            destination -> MusicSearchResponse.createFound(sourceMusic, destination)
        )
        .orElseGet(() -> MusicSearchResponse.createNotFound(sourceMusic));
  }
}
