package play.pluv.playlist.application.dto;

import java.util.List;

public record PlayListOcrRequest(
    List<String> base64EncodedImages
) {

}
