package play.pluv.oauth.apple;

import static io.jsonwebtoken.io.Decoders.BASE64;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.oauth.apple.dto.AppleTokenResponse;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.playlist.domain.MusicStreaming;

@Component
@RequiredArgsConstructor
public class AppleConnector implements SocialLoginClient {

  private static final String AUDIENCE = "https://appleid.apple.com";
  private static final Long EXP = MILLISECONDS.convert(30, MINUTES);

  private final AppleApiClient appleApiClient;
  private final AppleConfigProperty appleConfigProperty;

  @Override
  public OAuthMemberInfo fetchMember(final String authCode) {
    final MultiValueMap<String, String> param = createRequestParamForAccessToken(authCode);
    final AppleTokenResponse appleTokenResponse = appleApiClient.fetchToken(param);
    return null;
  }

  public AppleTokenResponse fetchMemberTest(final String authCode) {
    final MultiValueMap<String, String> param = createRequestParamForAccessToken(authCode);
    return appleApiClient.fetchToken(param);
  }

  @Override
  public MusicStreaming supportedType() {
    return APPLE;
  }

  private MultiValueMap<String, String> createRequestParamForAccessToken(final String authCode) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    param.add("grant_type", "authorization_code");
    param.add("code", authCode);
    param.add("redirect_uri", appleConfigProperty.redirectUri());
    param.add("client_id", appleConfigProperty.clientId());
    param.add("client_secret", generateClientSecret());
    return param;
  }

  //  @Override
//  public OAuth2MemberResponse getMemberResponse(String authCode) {
//    AppleTokenResponse tokenResponse;
//    try {
//      tokenResponse = appleOAuth2Client.getToken(
//          appleOAuth2Properties.getClientId(),
//          generateClientSecret(),
//          GRANT_TYPE,
//          authCode
//      );
//    } catch (Exception e) {
//      throw new BaseException(AppleExceptionType.TOKEN_ERROR, e);
//    }
//    String idToken = tokenResponse.getIdToken();
//    AppleTokenIdPayload appleTokenIdPayload = jwtProvider.decodePayload(idToken,
//        AppleTokenIdPayload.class);
//    return new AppleOAuth2MemberResponse(appleTokenIdPayload.getEmail());
//  }
//
  private String generateClientSecret() {
    final Map<String, Object> jwtHeaders = Map.of(
        "kid", appleConfigProperty.keyId(),
        "alg", "ES256"
    );

    return Jwts.builder()
        .header().add(jwtHeaders).and()
        .issuer(appleConfigProperty.teamId())
        .issuedAt(new Date())
        .audience().add(AUDIENCE).and()
        .expiration(new Date(EXP + currentTimeMillis()))
        .subject(appleConfigProperty.clientId())
        .signWith(Keys.hmacShaKeyFor(BASE64.decode(appleConfigProperty.privateKey())))
        .compact();
  }

//  private PrivateKey generatePrivateKey() {
//    Security.addProvider(new BouncyCastleProvider());
//    JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//
//    try {
//      byte[] privateKeyBytes = Base64.getDecoder().decode(appleOAuth2Properties.getPrivateKey());
//
//      return converter.getPrivateKey(PrivateKeyInfo.getInstance(privateKeyBytes));
//    } catch (Exception e) {
//      throw new RuntimeException("애플에 전송시 사용할 키 생성 중 예외가 발생했습니다.", e);
//    }
//  }
}
