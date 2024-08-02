package com.macedo.apigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    private static final String[] PUBLIC = {"/oauth/oauth/token", "/user/users/register"};
    private static final String[] OPERATOR = {"/worker/**"};
    private static final String[] ADMIN = {"/customer/**", "/user/**", "/actuator/**", "/customer/actuator/**", "/oauth/actuator/**"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(requests -> requests
                .antMatchers(PUBLIC).permitAll()

                .antMatchers(HttpMethod.GET, "/api/customers").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("ADMIN", "SUPORTE", "USER")
                .antMatchers("/api/customers/**").hasAnyRole("USER", "ADMIN", "SUPORTE")

                .antMatchers(HttpMethod.GET, "/api/addresses").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers(HttpMethod.GET, "/api/addresses/**").hasAnyRole("ADMIN", "SUPORTE", "USER")
                .antMatchers("/api/addresses/**").hasAnyRole("USER", "ADMIN", "SUPORTE")

                .antMatchers(HttpMethod.GET, "/api/creditCards").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers(HttpMethod.GET, "/api/creditCards/**").hasAnyRole("ADMIN", "SUPORTE", "USER")
                .antMatchers("/api/creditCards/**").hasAnyRole("USER", "ADMIN", "SUPORTE")

                .antMatchers(HttpMethod.GET, "/api/categories/**").hasAnyRole("ADMIN", "LOJISTA", "USER")
                .antMatchers("/api/categories/**").hasAnyRole("ADMIN", "LOJISTA")

                .antMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "LOJISTA", "USER")
                .antMatchers("/api/products/**").hasAnyRole("ADMIN", "LOJISTA")

                .antMatchers(HttpMethod.GET, "/api/discounts/**").hasAnyRole("ADMIN", "LOJISTA", "USER")
                .antMatchers("/api/discounts/**").hasRole("ADMIN")

                .antMatchers("/api/shippingTaxes/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/purchases").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers(HttpMethod.GET, "/api/purchases/**").hasAnyRole("ADMIN", "SUPORTE", "USER")
                .antMatchers(HttpMethod.POST, "/api/purchases/**").hasAnyRole("ADMIN", "SUPORTE", "USER")
                .antMatchers("/api/purchases/**").hasAnyRole("ADMIN", "SUPORTE")

                .antMatchers(HttpMethod.GET, "/api/payments").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("ADMIN", "SUPORTE", "USER")

                .antMatchers(HttpMethod.GET, "/api/shoppingCarts").hasAnyRole("ADMIN", "SUPORTE")
                .antMatchers("/api/shoppingCarts/**").hasAnyRole("ADMIN", "SUPORTE", "USER")

                .anyRequest().authenticated());
        http.cors(cors -> cors.configurationSource(configurationSource()));
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "DELETE","GET","PATCH"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(configurationSource()));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
