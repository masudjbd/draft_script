package com.example.demo;

import com.atlassian.jwt.SigningAlgorithm;
import com.atlassian.jwt.core.writer.JsonSmartJwtJsonBuilder;
import com.atlassian.jwt.core.writer.JwtClaimsBuilder;
import com.atlassian.jwt.core.writer.NimbusJwtWriterFactory;
import com.atlassian.jwt.httpclient.CanonicalHttpUriRequest;
import com.atlassian.jwt.writer.JwtJsonBuilder;

import java.util.HashMap;

public class JWTSample {

    public static void main(String[] args)
          {

              try {

        long issuedAt = System.currentTimeMillis() / 1000L;
        long expiresAt = issuedAt + 198880L;
        String key = "com.thed.zephyr.zapi"; //the key from the add-on descriptor
        String sharedSecret = "";    //the sharedsecret key received
        //during the add-on installation handshake
        String method = "GET";
        String baseUrl = "https://domain.atlassian.net";
        String contextPath = "/";
        String apiPath = "/rest/atlassian-connect/latest/addons/"+key;

        JwtJsonBuilder jwtBuilder = new JsonSmartJwtJsonBuilder()
                .issuedAt(issuedAt)
                .expirationTime(expiresAt)
                .issuer(key);

        CanonicalHttpUriRequest canonical = new CanonicalHttpUriRequest(method,
                apiPath, contextPath, new HashMap());
        JwtClaimsBuilder.appendHttpRequestClaims(jwtBuilder, canonical);

        NimbusJwtWriterFactory jwtWriterFactory = new NimbusJwtWriterFactory();
        String jwtbuilt = jwtBuilder.build();
        String jwtToken = jwtWriterFactory.macSigningWriter(SigningAlgorithm.HS256,
                sharedSecret).jsonToJwt(jwtbuilt);

        String apiUrl = baseUrl + apiPath + "?jwt=" + jwtToken;
        System.out.println(apiUrl);

              }catch (Exception e){

              }
     }
}