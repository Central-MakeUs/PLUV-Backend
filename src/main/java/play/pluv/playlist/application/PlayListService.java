package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.playlist.infra.OcrReader;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final PlayListConnectorComposite playListConnectorComposite;
  private final OcrReader ocrReader;

  public List<PlayList> getPlayLists(final String accessToken, final MusicStreaming source) {
    return playListConnectorComposite.getPlayList(source, accessToken);
  }

  public List<PlayListMusic> getPlayListMusics(
      final String playListId, final String accessToken, final MusicStreaming source
  ) {
    return playListConnectorComposite.getMusics(source, accessToken, playListId);
  }

  public List<PlayListMusic> getOcrPlayListMusics(final List<String> base64EncodedImages) {
    return ocrReader.ocrImages(base64EncodedImages);
  }
}
