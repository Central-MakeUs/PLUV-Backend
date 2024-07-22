package play.pluv.playlist.application.dto;

import java.util.List;
import lombok.Builder;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

@Builder
public record PlayListOverViewResponse(
    String id,
    String thumbNailUrl,
    Integer songCount,
    String name,
    String source
) {

  public static List<PlayListOverViewResponse> createList(final List<PlayList> playLists) {
    return playLists.stream()
        .map(PlayListOverViewResponse::from)
        .toList();
  }

  public static PlayListOverViewResponse from(final PlayList playList) {
    final PlayListId playListId = playList.getPlayListId();
    return new PlayListOverViewResponseBuilder()
        .thumbNailUrl(playList.getThumbNailUrl())
        .songCount(playList.getSongCount())
        .name(playList.getName())
        .source(playListId.musicStreaming().getName())
        .id(playListId.id())
        .build();
  }
}
