package store.ACS.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity

public class SecurityConfig {
	 @Value("${jwt.signerkey}")
	 private String signerkey;
	private final String[] PUBLIC_ENDPOINTS = {"/users","/auth/log-in","/auth/introspect"};
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSercurity) throws Exception {
		//Permit các endpoint public
		httpSercurity.authorizeHttpRequests(request -> 
		request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated());
		
		//Disable csrf của xss	
		httpSercurity.csrf(AbstractHttpConfigurer::disable);
		//Decode token
		httpSercurity.oauth2ResourceServer(oauth2 -> 
		oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
		
		return httpSercurity.build();
	}
	
	
	@Bean
	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(signerkey.getBytes(),"HS512");
		return NimbusJwtDecoder
				.withSecretKey(secretKeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}

}