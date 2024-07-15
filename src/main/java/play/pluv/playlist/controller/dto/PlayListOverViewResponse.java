package play.pluv.playlist.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import play.pluv.playlist.domain.PlayList;

@Builder
public record PlayListOverViewResponse(
    String thumbNailUrl,
    Integer songCount,
    @JsonFormat(pattern = "yyyy.MM.dd")
    LocalDate updatedDate,
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
        .updatedDate(playList.getUpdatedDate())
        .songCount(playList.getSongCount())
        .source(playList.getSource().getName())
        .build();
  }
}
