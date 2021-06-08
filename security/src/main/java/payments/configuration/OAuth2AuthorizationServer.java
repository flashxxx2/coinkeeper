package payments.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import payments.service.UserService;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserService userDetailsService;
  private final DataSource dataSource;

  public OAuth2AuthorizationServer(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userDetailsService, DataSource dataSource) {
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.dataSource = dataSource;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .jdbc(dataSource)
        .withClient("client")
        .secret(passwordEncoder.encode("secret"))
        .scopes("read", "write")
        .authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials", "refresh_token")
        .redirectUris("https://yandex.ru/")
        .accessTokenValiditySeconds(60 * 60)
        .refreshTokenValiditySeconds(7 * 24 * 60 * 60)
        .autoApprove(false)
        .and()
        .withClient("remote")
        .secret(passwordEncoder.encode("secret"));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    // откуда брать обычных пользователей
    endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }
}
