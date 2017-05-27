package sample;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import com.atlassian.jwt.*;
import com.atlassian.jwt.core.writer.*;
import com.atlassian.jwt.httpclient.CanonicalHttpUriRequest;
import com.atlassian.jwt.writer.JwtJsonBuilder;
import com.atlassian.jwt.writer.JwtWriterFactory;

public class JWTSample {

    public static void main(String[] args)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        long issuedAt = System.currentTimeMillis() / 1000L;
        long expiresAt = issuedAt + 198880L;
        String key = "com.thed.zephyr.zapi"; //the key from the add-on descriptor
        String sharedSecret = "secretKey";    //the sharedsecret key received
        //during the add-on installation handshake
        String method = "GET";
        String baseUrl = "https://client.atlassian.net/";
        String contextPath = "/";
        String apiPath = "/rest/atlassian-connect/latest/addons/"+key;

        JwtJsonBuilder jwtBuilder = new JsonSmartJwtJsonBuilder()
                .issuedAt(issuedAt)
                .expirationTime(expiresAt)
                .issuer(key);

        CanonicalHttpUriRequest canonical = new CanonicalHttpUriRequest(method,
                apiPath, contextPath, new HashMap());
        JwtClaimsBuilder.appendHttpRequestClaims(jwtBuilder, canonical);

        JwtWriterFactory jwtWriterFactory = new NimbusJwtWriterFactory();
        String jwtbuilt = jwtBuilder.build();
        String jwtToken = jwtWriterFactory.macSigningWriter(SigningAlgorithm.HS256,
                sharedSecret).jsonToJwt(jwtbuilt);

        String apiUrl = baseUrl + apiPath + "?jwt=" + jwtToken;
        System.out.println(apiUrl);
     }
}