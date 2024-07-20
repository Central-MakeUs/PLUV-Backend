package play.pluv.music.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.SourceMusic;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final MusicExplorer musicExplorer;

  public List<MusicSearchResponse> searchMusics(final MusicSearchRequest request) {
    final String accessToken = request.destinationAccessToken();
    final List<SourceMusic> datas = request.musics().parallelStream()
        .map(MusicQuery::toDomain)
        .toList();

    return datas.stream()
        .map(sourceMusic -> searchMusic(sourceMusic, accessToken)
        ).toList();
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
