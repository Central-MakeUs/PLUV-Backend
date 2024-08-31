package play.pluv.playlist.infra;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import play.pluv.playlist.infra.dto.OcrMusicRequest;
import play.pluv.playlist.infra.dto.OcrMusicResponse;

public interface OcrApiClient {

  @PostExchange("http://192.168.0.26:8000/ocr")
  List<OcrMusicResponse> ocrPlayListImage(@RequestBody final OcrMusicRequest request);
}
