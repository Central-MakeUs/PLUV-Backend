package play.pluv.playlist.controller.dto;

import java.util.List;
import lombok.Builder;
import play.pluv.playlist.domain.PlayList;

@Builder
public record PlayListOverViewResponse(
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
    return new PlayListOverViewResponseBuilder()
        .thumbNailUrl(playList.getThumbNailUrl())
        .songCount(playList.getSongCount())
        .name(playList.getName())
        .source(playList.getSource().getName())
        .build();
  }
}
