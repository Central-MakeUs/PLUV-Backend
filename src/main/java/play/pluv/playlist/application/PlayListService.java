package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final PlayListConnectorComposite playListConnectorComposite;

  public List<PlayList> getPlayLists(final String accessToken, final MusicStreaming source) {
    return playListConnectorComposite.getPlayList(source, accessToken);
  }

  public List<PlayListMusic> getPlayListMusics(
      final String playListId, final String accessToken, final MusicStreaming source
  ) {
    return playListConnectorComposite.getMusics(source, accessToken, playListId);
  }

  public List<PlayListMusic> getOcrPlayListMusics(final List<String> base64EncodedImages) {
    return List.of(
        new PlayListMusic(
            "좋은 날", List.of("아이유"), null,
            "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"
        ),
        new PlayListMusic(
            "ㅈㅣㅂ", List.of("한로로"), null,
            "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a22314"
        )
    );
  }
}
