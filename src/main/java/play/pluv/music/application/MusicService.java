package play.pluv.music.application;

import java.util.List;
import org.springframework.stereotype.Service;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.domain.SourceMusic;

@Service
public class MusicService {

  private final MusicExplorer musicExplorer;

  public MusicService(final MusicExplorer musicExplorer) {
    this.musicExplorer = musicExplorer;
  }

  public List<MusicSearchResponse> searchMusic(final MusicSearchRequest request) {
    final String accessToken = request.destinationAccessToken();
    final List<SourceMusic> datas = request.musics().parallelStream()
        .map(MusicQuery::toDomain)
        .toList();

//    return datas.stream()
//        .map(sourceMusic -> {
//          final Optional<DestinationMusic> result
//              = musicExplorer.searchMusic(accessToken, sourceMusic);
//          return result.map(
//              destinationMusic -> {
//                new MusicSearchResponse()
//              }
//          ).orElseGet(() -> MusicSearchResponse.createNotFound(sourceMusic));
//        })
//        .toList();
    return null;
  }
}
