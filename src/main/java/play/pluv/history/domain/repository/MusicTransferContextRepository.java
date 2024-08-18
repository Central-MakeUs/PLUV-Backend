package play.pluv.history.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import play.pluv.history.domain.MusicTransferContext;
import play.pluv.playlist.domain.PlayListMusic;

@Component
public class MusicTransferContextRepository {

  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();

  public void setSearchMusics(
      final Long memberId, final List<PlayListMusic> playListMusics
  ) {
    final MusicTransferContext context = new MusicTransferContext(memberId, playListMusics);
    musicTransferContextMap.put(memberId, context);
  }

  public MusicTransferContext getContext(final Long memberId) {
    return musicTransferContextMap.get(memberId);
  }
}
