package play.pluv.login.application.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import play.pluv.playlist.domain.MusicStreaming;

public record LoginTypeResponse(
    String type
) {

  public static LoginTypeResponse from(final MusicStreaming type) {
    if (type == YOUTUBE) {
      return new LoginTypeResponse("google");
    }
    return new LoginTypeResponse(type.getName());
  }

  public static List<LoginTypeResponse> createList(final List<MusicStreaming> types) {
    return types.stream()
        .map(LoginTypeResponse::from)
        .toList();
  }
}
