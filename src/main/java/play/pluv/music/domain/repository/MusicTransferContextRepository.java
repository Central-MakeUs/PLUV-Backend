package play.pluv.music.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import play.pluv.music.domain.MusicTransferContext;
import play.pluv.playlist.domain.PlayListMusic;

@Component
public class MusicTransferContextRepository {

  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();

  public void setSearchMusics(final Long memberId, final List<PlayListMusic> playListMusics) {
    final MusicTransferContext context = new MusicTransferContext(playListMusics);
    musicTransferContextMap.put(memberId, context);
  }

  public MusicTransferContext getContext(final Long memberId) {
    return musicTransferContextMap.get(memberId);
  }
}
