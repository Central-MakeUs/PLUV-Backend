package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.SourceMusic;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorer musicExplorer;

  @Transactional(readOnly = true)
  public List<MusicSearchResponse> searchMusics(final MusicSearchRequest request) {
    final String accessToken = request.destinationAccessToken();

    return request.musics().parallelStream()
        .map(MusicQuery::toDomain)
        .map(sourceMusic -> searchMusic(sourceMusic, accessToken))
        .toList();
  }

  private MusicSearchResponse searchMusic(
      final SourceMusic sourceMusic, final String accessToken
  ) {
    final Optional<DestinationMusic> result = musicExplorer.searchMusic(accessToken, sourceMusic);
    return result
        .map(
            destination -> MusicSearchResponse.createFound(sourceMusic, destination)
        )
        .orElseGet(() -> MusicSearchResponse.createNotFound(sourceMusic));
  }
}
