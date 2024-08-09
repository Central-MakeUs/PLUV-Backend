package play.pluv.login.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import play.pluv.login.application.dto.JwtMemberId;

@Component
@RequiredArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtProvider jwtProvider;

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.getParameterType().equals(JwtMemberId.class);
  }

  @Override
  public JwtMemberId resolveArgument(
      final MethodParameter parameter, final ModelAndViewContainer mavContainer,
      final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory
  ) {
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    final String token = AuthorizationExtractor.extract(request);

    final Long memberId = jwtProvider.parseMemberId(token);

    return new JwtMemberId(memberId);
  }
}
