package play.pluv.music.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import play.pluv.playlist.domain.MusicStreaming;

@Embeddable
public record MusicId(
    MusicStreaming musicStreaming,
    @Column(name = "music_id")
    String id
) {

}
