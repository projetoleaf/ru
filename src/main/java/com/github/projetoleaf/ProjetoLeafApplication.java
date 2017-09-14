package com.github.projetoleaf;

import com.github.projetoleaf.beans.UsuarioDetails;
import java.io.IOException;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProjetoLeafApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoLeafApplication.class, args);
	}

    @Configuration
    @EnableOAuth2Sso
    @EnableWebSecurity
    public static class Security extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/layouts/**").permitAll()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                ;
        }

    }

    @Component
    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        private final AuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/boasVindas");

        @Resource(name = "springSecurityFilterChain")
        private FilterChainProxy chainProxy;

        @PostConstruct
        public void init() {
            for (SecurityFilterChain chain : chainProxy.getFilterChains()) {
                for (Filter filter : chain.getFilters()) {
                    if (filter instanceof OAuth2ClientAuthenticationProcessingFilter) {
                        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = (OAuth2ClientAuthenticationProcessingFilter) filter;
                        oAuth2ClientAuthenticationProcessingFilter.setAuthenticationSuccessHandler(this);
                    }
                }
            }
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Authentication auth = ((OAuth2Authentication) authentication).getUserAuthentication();
            Map<String, Object> map = (Map<String, Object>) auth.getDetails();
            Map<String, Object> mapDetails = (Map<String, Object>) map.get("details");
            UsuarioDetails details = new UsuarioDetails();
            details.setNome((String) mapDetails.get("nome"));
            details.setEmail((String) mapDetails.get("email"));
            details.setCpf((String) mapDetails.get("cpf"));
            details.setRemoteAddress((String) mapDetails.get("remoteAddress"));
            ((OAuth2Authentication) authentication).setDetails(details);
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }

    }

}
