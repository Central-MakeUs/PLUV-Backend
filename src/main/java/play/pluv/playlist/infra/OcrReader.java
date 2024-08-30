package play.pluv.playlist.infra;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.playlist.infra.dto.OcrMusicRequest;
import play.pluv.playlist.infra.dto.OcrMusicResponse;

@Component
@RequiredArgsConstructor
public class OcrReader {

  private final OcrApiClient ocrApiClient;

  public List<PlayListMusic> ocrImages(final List<String> base64EncodedImages) {
    final OcrMusicRequest request = OcrMusicRequest.from(base64EncodedImages);
    return ocrApiClient.ocrPlayListImage(request).stream()
        .map(OcrMusicResponse::toPlayListMusic)
        .toList();
  }
}
