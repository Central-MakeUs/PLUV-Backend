package play.pluv.oauth.apple.dto;

import java.util.List;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListId;

public record AppleCreatePlayListResponse(
    List<ApplePlayListData> data
) {

  public PlayListId getId() {
    return new PlayListId(data.get(0).id, MusicStreaming.APPLE);
  }

  private record ApplePlayListData(
      String id
  ) {

  }
}
