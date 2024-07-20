package play.pluv.playlist.domain;

import play.pluv.music.domain.MusicStreaming;

public record PlayListId(
    String id,
    MusicStreaming musicStreaming
) {

}
