package com.self.mycollegeapp.gateway.filter;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayAuthenticationAuthorizationFilter extends AbstractGatewayFilterFactory<GatewayAuthenticationAuthorizationFilter.Config> {

	public static class Config {
		static String appPublic = "1dd395c19f3a1f7ff0e6bfe3ee6028e4373b41085a6da776ab02a8baf129abba";
		static String jwtPrivate = "a1dd395c19f3a1f7ff0e6bfe3ee6028e4373b41085a6da776ab02a8baf129abb";
	}
	
	public GatewayAuthenticationAuthorizationFilter() {
		super(Config.class);
	}
	  
    private boolean isAuthenticationValid(String authenticationHeader, HttpMethod httpMethod) {
        boolean isAuthenticationValid = false;
        System.out.println(authenticationHeader);
        if(authenticationHeader.contains("Enhanced ")) {
        	authenticationHeader = authenticationHeader.replace("Enhanced ", "");
        	//in actual below token should be process with real data e.g of user information from database, correct issue at time value. this just a mock
            if(authenticationHeader.equals(DigestUtils.sha256Hex("user1password123"+Config.appPublic)) && httpMethod.toString().equals("POST")) {
            	isAuthenticationValid = true;
            }
        }
        return isAuthenticationValid;
    }
	
	private boolean isAuthorisationValid(String authorizationHeader) {
        boolean isAuthorisationValid = false;
        System.out.println(authorizationHeader);
        if(authorizationHeader.contains("Bearer ")) {
        	authorizationHeader = authorizationHeader.replace("Bearer ", "");
        	//in actual below token should be process with real data e.g of user information from database, correct issue at time value. this just a mock
    		String jwtHeader = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    		String jwtPayload = "{\"username\":\"admin1\",\"iat\":\"1620590325\"}";
    		String encodedContent = Base64.getEncoder().encodeToString(jwtHeader.getBytes())+"."+Base64.getEncoder().encodeToString(jwtPayload.getBytes());
    		byte[] jwtSignature = HmacUtils.hmacSha256(Config.jwtPrivate, encodedContent);
    		String jwtToken = Base64.getEncoder().encodeToString(jwtHeader.getBytes())+"."+Base64.getEncoder().encodeToString(jwtPayload.getBytes())+"."+Base64.getEncoder().encodeToString(jwtSignature);
    		if(jwtToken.toString().equals(authorizationHeader)) {
    			isAuthorisationValid = true;
    		}
        }
		return isAuthorisationValid;
	}

    private Mono<Void> onUnauthorised(ServerWebExchange exchange, String err)  {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        return serverHttpResponse.setComplete();
    }	

	@Override
	public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
        	Map<String, Object> mso = exchange.getAttributes();
        	for (Map.Entry<String,Object> entry : mso.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        	}
        	Mono<MultiValueMap<String, String>> data = exchange.getFormData();

            data.map(formData -> {
                String parameterValue = formData.getFirst("id");
                System.out.println("parameterValue "+parameterValue);
                for(Map.Entry<String,List<String>> e : formData.entrySet()) {
                	System.out.println(e.getKey()+" ========================== "+e.getValue().get(0));
                }
                return null;
            });
            String pathURL = serverHttpRequest.getPath().toString().replace("/mycollegeapp/services/student", "");
            
            if(pathURL.equals("/authenticate")) {
            	System.out.println(serverHttpRequest.getQueryParams().size()+" "+serverHttpRequest.getMethod()+" ");
            	boolean isAuthenticationValid = isAuthenticationValid(serverHttpRequest.getHeaders().get("Authorization").get(0), serverHttpRequest.getMethod());
                System.out.println(isAuthenticationValid+" --- "+DigestUtils.sha256Hex("user1password123"+Config.appPublic));
            	if(!isAuthenticationValid) {
                    System.out.println(isAuthenticationValid+" --- "+DigestUtils.sha256Hex("user1password123"+Config.appPublic));
            		return onUnauthorised(exchange, "Invalid authentication");
            	}
            } else if (serverHttpRequest.getHeaders().containsKey("Authorization")) {
            	String authorizationHeader = serverHttpRequest.getHeaders().get("Authorization").get(0);
            	boolean isAuthorisationValid = isAuthorisationValid(authorizationHeader);
            	if(!isAuthorisationValid) {
            		return onUnauthorised(exchange, "Invalid authorisation");
            	}
            } else {
            	return onUnauthorised(exchange, "Invalid authorisation");
            }
        	return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        		System.out.println("Post Filter. Additional thing we can or want to do, we can figure it out here");
        	}));
        };		
	}
	
}