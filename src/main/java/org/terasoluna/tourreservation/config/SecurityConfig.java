package org.terasoluna.tourreservation.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.authorizeHttpRequests(authz -> authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll()
				.requestMatchers("/tours/{tourId}/reserve")
				.hasRole("USER")
				.requestMatchers("/reservations/**")
				.hasRole("USER")
				.anyRequest()
				.permitAll())
			.formLogin(form -> {
				SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
				successHandler.setTargetUrlParameter("redirectTo");
				form.loginPage("/login").failureUrl("/login?error=true").successHandler(successHandler);
			})
			.logout(logout -> logout.logoutSuccessUrl("/").deleteCookies("JSESSIONID"))
			.build();
	}

}
