package play.pluv.music.domain;

import play.pluv.playlist.domain.MusicStreaming;

public record MusicId(
    MusicStreaming musicStreaming,
    String id
) {

}
