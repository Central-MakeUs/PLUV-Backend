package play.pluv.playlist.domain;

public record PlayListId(
    String id,
    PlayListProvider playListProvider
) {

}
