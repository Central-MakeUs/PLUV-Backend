package play.pluv.oauth.google.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import play.pluv.playlist.domain.PlayListId;

public record YoutubeCreatePlayListResponse(String id) {

  public PlayListId toPlayListId() {
    return new PlayListId(id, YOUTUBE);
  }
}
