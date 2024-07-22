package play.pluv.playlist.application.dto;

import java.util.List;
import lombok.Builder;
import play.pluv.playlist.domain.PlayListMusic;

@Builder
public record PlayListMusicResponse(
    String name, String artistNames, String isrcCode, String imageUrl
) {

  public static List<PlayListMusicResponse> createList(final List<PlayListMusic> musics) {
    return musics.stream()
        .map(PlayListMusicResponse::from)
        .toList();
  }

  private static PlayListMusicResponse from(final PlayListMusic playListMusic){
    return PlayListMusicResponse.builder()
        .name(playListMusic.getName())
        .artistNames(String.join(",",playListMusic.getArtistNames()))
        .imageUrl(playListMusic.getImageUrl())
        .isrcCode(playListMusic.getIsrcCode().get())
        .build();
  }
}
