package my.tocker.jwtauth.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//  resource / auth 서버가 분리되어있는 경우. 암호화 복호화 keypair 사용
//  keytool로 keypair 생성시 사용했던 store, key password와 alias명에 따라 아래 설정을 변경하면 됩니다.
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("server.jks"), "spassword".toCharArray())
                .getKeyPair("jwt", "kpassword".toCharArray());
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    // 외부요청 유입시 엑세스 토큰을 보존할 위치를 설정한다. JWT이므로 별도의 내부 store는 존재하지 않으며 토큰 내부에 있다.
    // 보통 jwt가 아닌 accesstoken의 경우 InMemoryTokenStore(연습시)나 별도의 Token스토리지에 대한 정보를 설정한다.
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter());
    }

//  TODO: oauth_client_details 테이블로 앱 시크릿 관리하고 싶은경우 주석제거

//    @Bean
//    @Primary
//    public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    private ClientDetailsService clientDetailsService;
//
//    @Autowired
//    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
//        this.clientDetailsService = clientDetailsService;
//    }

    private static final String APPNAME = "testapp";
    private static final String APPKEY = "$2a$10$EHqL/a.q50N2tTtSbMt3d.wAeJ7WvZwC4fomp9qKoFTiJu.zYwRZm";

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // TODO: oauth_client_details 테이블로 앱 시크릿 관리하고 싶은경우 사용
//        clients.withClientDetails(clientDetailsService);

        // 인메모리 프로퍼티 처리 등.
		clients.inMemory()

            // 클라이언트(앱) 의 식별명, 비밀키
            .withClient(APPNAME).secret(APPKEY)

            // 인증 유형
            .authorizedGrantTypes("password", "authorization_code", "implicit", "refresh_token")

            // 클라이언트가 가질 role
            .authorities("ROLE_YOUR_CLIENT")

            // 허가할 스코프 유형
            .scopes("read", "write")

            // 허가할 리소스 (resource 서버 설정 참조)
            .resourceIds("tocker-resource")

            // 토큰 유효기간 (초)
            .accessTokenValiditySeconds(36000);

    }

}
