package play.pluv.history.domain;

import jakarta.persistence.Embeddable;
import play.pluv.playlist.domain.MusicStreaming;

@Embeddable
public record HistoryMusicId(
    MusicStreaming source,
    String musicId
) {

}
