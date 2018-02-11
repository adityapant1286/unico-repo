package com.serviceapi.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import javax.jms.ConnectionFactory;

/**
 * This class is responsible to configure Basic authentication
 * for any request made for any resource.
 * Session management is configured as stateless, as in this example
 * we are not maintaining any use state.
 *
 * <ul>
 *     <li>CSRF is disabled for good</li>
 *     <li>Authenticate any requests</li>
 *     <li>Attaching custom BasicAuthenticationEntryPoint for invalid credentials scenario</li>
 *     <li>Set session management to stateless for this example</li>
 * </ul>
 *
 */
@EnableJms
@EnableWs
@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    // Constants
    // List of users under User role
    private static final String USER = "foo";
    private static final String USER_PASS = "bar";
    private static final String USER_ROLE = "USER";

    // Admin user
    private static final String ADMIN = "admin";
    private static final String ADMIN_PASS = "admin";
    private static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Configure http security for each request
     *
     * @param http {@link HttpSecurity}
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http)
            throws Exception {

        http.csrf()
                .disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Builder will be injected during runtime by spring.
     * Valid user credentials setup.
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.inMemoryAuthentication()
                .withUser(USER).password(USER_PASS).roles(USER_ROLE).and()
                .withUser(ADMIN).password(ADMIN_PASS).roles(ADMIN_ROLE);
    }

    // soap
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);

        return new ServletRegistrationBean(servlet, "/soapapi/ws/*");
    }

    @Bean(name = "gcd")
    public Wsdl11Definition defaultWsdl11Definition() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/gcd.wsdl"));

        return wsdl11Definition;
    }

    // jms
    @Bean
    public JmsListenerContainerFactory<?> apiMsgJmsFactory(ConnectionFactory connectionFactory,
                                                           DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

}
