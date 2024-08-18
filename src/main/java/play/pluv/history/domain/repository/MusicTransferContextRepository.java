package play.pluv.history.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import play.pluv.history.domain.MusicTransferContext;
import play.pluv.history.domain.TransferFailMusicInContext;

@Component
public class MusicTransferContextRepository {

  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();

  public void initContext(
      final Long memberId, final List<TransferFailMusicInContext> transferFailMusic
  ) {
    final MusicTransferContext context = new MusicTransferContext(memberId, transferFailMusic);
    musicTransferContextMap.put(memberId, context);
  }
}
