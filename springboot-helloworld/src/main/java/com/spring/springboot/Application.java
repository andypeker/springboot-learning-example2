package com.spring.springboot;

import ch.qos.logback.classic.servlet.LogbackServletContainerInitializer;
import com.spring.springboot.initializer.MyApplicationContextInitializer1;
import com.spring.springboot.initializer.MyApplicationContextInitializer2;
import com.spring.springboot.initializer.MyServletContainerInitializer;
import com.spring.springboot.initializer.MyWebApplicationInitializer;
import com.spring.springboot.listener.*;
import com.spring.springboot.runListener.MySprAppRunLsnr;
import com.spring.springboot.service.HelloAutoConfiguration;
import org.apache.tomcat.websocket.server.WsContextListener;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcHypermediaManagementContextConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcManagementContextConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.boot.autoconfigure.data.mongo.MongoClientDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.builder.ParentContextCloserApplicationListener;
import org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer;
import org.springframework.boot.context.ContextIdApplicationContextInitializer;
import org.springframework.boot.context.FileEncodingApplicationListener;
import org.springframework.boot.context.config.AnsiOutputApplicationListener;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.config.DelegatingApplicationContextInitializer;
import org.springframework.boot.context.config.DelegatingApplicationListener;
import org.springframework.boot.context.embedded.*;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetaData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener;
import org.springframework.boot.logging.ClasspathLoggingApplicationListener;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.boot.context.event.*;
import org.springframework.boot.web.servlet.*;
import org.springframework.boot.web.support.ServletContextApplicationContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.TaskNamespaceHandler;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.MvcNamespaceHandler;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import org.springframework.web.util.IntrospectorCleanupListener;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;
import javax.websocket.server.ServerEndpoint;
import java.util.ServiceLoader;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static com.sun.org.apache.xml.internal.serialize.LineSeparator.Web;

/**
 * Spring Boot 应用启动类
 * <p>
 * Created by bysocket on 16/4/26.
 */
// Spring Boot 应用的标识
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
//        SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);

        app.addListeners(
                new MyApplicationStartingListener(),
                new MyApplicationFailListener(),
                new MyApplicationReadyListener(),
                new MyApplicationPreparedListener(),
                new MyEnvReadyListener(),
                new MyContextClosedListener(),
                new MyContextRefreshedListener(),
                new MyContextStartedListener(),
                new MyContextStoppedListener());

        app.addInitializers(
                new MyApplicationContextInitializer1(),
                new MyApplicationContextInitializer2());

        app.run(args);
    }




    public void fwefwef() {

        ApplicationRunner apprnnr;

        ServletContextInitializer ewfwef;
        WebApplicationInitializer wefwefwef;
        ApplicationContextInitializer wefwefwefwef;

        SpringBootConfiguration wer;
        EnableAutoConfiguration wrwer23;

        EnableScheduling we3r;
        EnableCaching werwer;

        EnableWebMvc fwefwefwef;

        SpringFactoriesLoader wer2323r23r;

        SpringApplicationRunListener wewerl;
        ApplicationListener fwefwef;

        ParentContextCloserApplicationListener ewf;
        FileEncodingApplicationListener fwefwe;
        AnsiOutputApplicationListener fwef;
        ConfigFileApplicationListener fwefewf;
        DelegatingApplicationListener ddddergerd;
        LiquibaseServiceLocatorApplicationListener gwgwegweg;
        ClasspathLoggingApplicationListener fwefwewefwef;
        LoggingApplicationListener fwefweflwefl;


        //  1

        //  org.springframework.context
        ApplicationContextInitializer wefwefwwwef;
        AutoConfigurationReportLoggingInitializer vnbvg;
        ServerPortInfoApplicationContextInitializer fefwefewf;
        //  javax.servlet
        ServletContainerInitializer svltContIniter2;
        //  org.springframework.web
        WebApplicationInitializer webAppIniter;
        //  org.springframework.boot.web.servlet
        ServletContextInitializer svltCntxIniter;


        //  2

//        SpringApplicationRunListeners springAppRunLsnrs;
        SpringApplicationRunListener springAppRunLsnr;
        EventPublishingRunListener ergergerg;
        ApplicationEvent we135rwer;
        SpringApplicationEvent erger34234;
        ApplicationEnvironmentPreparedEvent ewrwer;
        ApplicationPreparedEvent fwefw221ef;
        ApplicationReadyEvent feefw2ef;
        ApplicationFailedEvent efwefeeeeew;
        ApplicationStartingEvent wefwefwef222333;
        ApplicationStartedEvent fewiioweirf;
        ApplicationContextEvent erfer324;
        ContextClosedEvent fwefwef234234;
        ContextRefreshedEvent owefionwef;
        ContextStoppedEvent voiwne;
        ContextStartedEvent ovwieobwefe;
        ApplicationEventMulticaster gergerg;
        AbstractApplicationEventMulticaster ewwefwef;
        SimpleApplicationEventMulticaster sdgweg;
        ApplicationListener lsnr;
//            rergerg ergerg;
//                rergerg ergerg;


        //  3

        Conditional fwefwdedwe;
        ConditionalOnWebApplication fwhjfgjfgjefwef;

        DispatcherServlet dispSvlt;
        SpringFactoriesLoader spFacldr;

        //  657567

        JmxAutoConfiguration gegerg;

        ServiceLoader svcLdr;
        ClassLoader clsLdr;
        SpringFactoriesLoader spFacLdr;


        //  4

//        AutoConfigureWebMvc sfsdff;
        WebMvcAutoConfiguration wefeeewefwef;

        WebApplicationInitializer fwefw2123123e;
        ApplicationContextInitializer vervwr324234;

        MessageSourceAutoConfiguration fef92309020934234;

        ApplicationListener gergergerg;
        ParentContextCloserApplicationListener grgergergerg;
        FileEncodingApplicationListener wgwegweg;
        DelegatingApplicationListener wefwe23423424fwef;

        MessageSourceAutoConfiguration ge23454325rgergerg;

        WebMvcAutoConfiguration grgweg;

        /*

            # AutoConfigureWebMvc auto-configuration imports
            org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc=\
            org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
            org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
            org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
            org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
            org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
            org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
            org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration

            # Application Listeners
            org.springframework.context.ApplicationListener=\
            org.springframework.boot.ClearCachesApplicationListener,\
            org.springframework.boot.builder.ParentContextCloserApplicationListener,\
            org.springframework.boot.context.FileEncodingApplicationListener,\
            org.springframework.boot.context.config.AnsiOutputApplicationListener,\
            org.springframework.boot.context.config.ConfigFileApplicationListener,\
            org.springframework.boot.context.config.DelegatingApplicationListener,\
            org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener,\
            org.springframework.boot.logging.ClasspathLoggingApplicationListener,\
            org.springframework.boot.logging.LoggingApplicationListener

            # AutoConfigureWebClient auto-configuration imports
            org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient=\
            org.springframework.boot.test.autoconfigure.web.client.WebClientRestTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
            org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration

        */

        ApplicationListener fw21111e;
        AutoConfigurationImportFilter gwrgwegweg;

        BackgroundPreinitializer gwegwegweg;

        SpringBootConfiguration fwefwe5367537357;

        ServletComponentScan fwefwef2443634613;
        ComponentScan fwefwefwe3214134f;
        ComponentScans fwefwefwefw451345351e;

        EnableAutoConfiguration feef242342343ef;
        EnableMBeanExport egwegwegweg;
        EnableScheduling gewrgwegweg;
        EnableWebMvc fwefwefwef133123123123;

        SpringApplicationAdminJmxAutoConfiguration fwe2323f;


//        AutoConfigureWebMvc fwef575466wef;
        MessageSourceAutoConfiguration gergerge456245645rg;

        WebMvcAutoConfiguration fewfwefwef;
        GroovyTemplateAutoConfiguration ggwegweg;

        ConfigurationWarningsApplicationContextInitializer wevwevwev;
        ContextIdApplicationContextInitializer gwegweg;
        DelegatingApplicationContextInitializer grgrgrg;
        ServerPortInfoApplicationContextInitializer fwefepfpe;

        CacheAutoConfiguration gerger56u356u356u;
        ApplicationContextInitializer gergerg34g34g34;

        /*

            # Application Context Initializers
            org.springframework.context.ApplicationContextInitializer=\
            org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,\
            org.springframework.boot.context.ContextIdApplicationContextInitializer,\
            org.springframework.boot.context.config.DelegatingApplicationContextInitializer,\
            org.springframework.boot.context.embedded.ServerPortInfoApplicationContextInitializer

            # AutoConfigureCache auto-configuration imports
            org.springframework.boot.test.autoconfigure.core.AutoConfigureCache=\
            org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration

            # AutoConfigureDataJpa auto-configuration imports
            org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa=\
            org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
            org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
            org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

            # AutoConfigureDataMongo auto-configuration imports
            org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo=\
            org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
            org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration

            # AutoConfigureJdbc auto-configuration imports
            org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc=\
            org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
            org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

            # Auto Configure
            org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
            org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
            org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
            org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
            org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
            org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
            org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
            org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration,\
            org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
            org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
            org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
            org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.ldap.LdapDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
            org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
            org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
            org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
            org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
            org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
            org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
            org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
            org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
            org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
            org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
            org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
            org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
            org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
            org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
            org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
            org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
            org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
            org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
            org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
            org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
            org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
            org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
            org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
            org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
            org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
            org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
            org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
            org.springframework.boot.autoconfigure.mobile.DeviceResolverAutoConfiguration,\
            org.springframework.boot.autoconfigure.mobile.DeviceDelegatingViewResolverAutoConfiguration,\
            org.springframework.boot.autoconfigure.mobile.SitePreferenceAutoConfiguration,\
            org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
            org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
            org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
            org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
            org.springframework.boot.autoconfigure.reactor.ReactorAutoConfiguration,\
            org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration,\
            org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration,\
            org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration,\
            org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration,\
            org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
            org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
            org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration,\
            org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration,\
            org.springframework.boot.autoconfigure.social.LinkedInAutoConfiguration,\
            org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration,\
            org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
            org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
            org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
            org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
            org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration,\
            org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration,\
            org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration,\
            org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration,\
            org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration

        */

        FlywayAutoConfiguration gergergergerg;

        MongoDataAutoConfiguration vczvzxvzxcv;
        AutoConfigurationImportFilter gwefwef;
        AutoConfigurationImportListener fwefwe3513451435;
        ApplicationContextInitializer fwefwefwef234234;

        SpringApplicationAdminJmxAutoConfiguration ergqhqrhqrh;


        //  6

        EmbeddedServletContainerAutoConfiguration fwefoiwef989we89f;
        DispatcherServletAutoConfiguration fwefwef9090209f23f;

        ConfigurableEmbeddedServletContainer gerg345354345;
        AbstractConfigurableEmbeddedServletContainer reer4545;
        EmbeddedServletContainerFactory r23r23r2333452345r;
        AbstractEmbeddedServletContainerFactory fwefwef0203f;
        TomcatEmbeddedServletContainerFactory gwg4343t134twegweg;
        UndertowEmbeddedServletContainerFactory ggergeqrge314t143tqrg;
        JettyEmbeddedServletContainerFactory fwefqg34g341g;

        EmbeddedServletContainer fwef123123wef;
        JettyEmbeddedServletContainer r23r23r23r;
        TomcatEmbeddedServletContainer gqgerg43g34g;
        UndertowEmbeddedServletContainer gergergo090qergqrg;

        DispatcherServlet ds;

        AbstractApplicationContext fwe090we0fw0ef;
        GenericApplicationContext fwfwefwef;
        GenericWebApplicationContext fwef092f023f;
        EmbeddedWebApplicationContext fwefwef234234234;
        AnnotationConfigEmbeddedWebApplicationContext wef0090wefwef;
        XmlEmbeddedWebApplicationContext ergergpeorpogperg0909erg;

        EnableWebMvc wefwefwef430143901490;
        WebMvcConfigurer vfdvf122323;
        WebMvcConfigurerAdapter erobeqribnoiqerbqerb;
        WebMvcConfigurationSupport vv2390923confi;
        DelegatingWebMvcConfiguration greogioeirg;
        WebMvcAutoConfiguration.EnableWebMvcConfiguration rogioerigerg;
        WebServlet df;
        WebListener egerg;
        WebFilter fwef23;
        WebInitParam eropowe;


        ServletContextInitializer fwefwefwef23452435345;

        SpringBootCondition erververv;

        EndpointWebMvcManagementContextConfiguration erf2309230f923f;

        SpringApplicationAdminJmxAutoConfiguration grgergwg0909g23g;

        FreeMarkerTemplateAvailabilityProvider gergergerg93148951485915;
        ConditionalOnClass ergerg0314901943014;
        ConditionalOnBean gregergerg2039023;


        EnableScheduling fwef01092;
        ApplicationContextInitializer fwefwefwef1203910293;

        BeanPostProcessor gregeg013409014;

        ConfigurationProperties tohijioqehr;
        EnableConfigurationProperties eroignoinerng;

        ApplicationContext ac;
        RequestMapping reqMap;

        EnableAutoConfiguration foweio23;
        DataSourceAutoConfiguration ewoi2o3io2i3of;

        WebMvcConfigurerAdapter weadpt;

        HttpMessageConverter wewwe;
        ServletRegistrationBean wqqwq232323;

        //  start
        EmbeddedServletContainerCustomizer fwefwe2323;

        EmbeddedServletContainerFactory wewewe23232;
        ConfigurableEmbeddedServletContainer conownow;
        AbstractConfigurableEmbeddedServletContainer fwf23f23;
        AbstractEmbeddedServletContainerFactory wewe2322323;
        TomcatEmbeddedServletContainerFactory tomcatfs;
        UndertowEmbeddedServletContainerFactory undertowfs;
        JettyEmbeddedServletContainerFactory jettyfs;

        EmbeddedServletContainer cont;
        UndertowEmbeddedServletContainer wewe23f23f;
        TomcatEmbeddedServletContainer weiuifu23;
        JettyEmbeddedServletContainer gooi43gnoi2323;

        GenericApplicationContext gac;
        EmbeddedWebApplicationContext eac;
        AnnotationConfigEmbeddedWebApplicationContext aaa23223;
        XmlEmbeddedWebApplicationContext xmleac;

        //  end

        AnnotationConfigApplicationContext ac121;
        AnnotationConfigEmbeddedWebApplicationContext eac11231;

        EnvironmentAware wefwef;
        Environment wefwef23f23;

        JdbcTemplateAutoConfiguration auorooro;
        ServerProperties rweoiwnoeb;

        ApplicationListener al;
        ApplicationContextInitializer acInit;
        ParentContextCloserApplicationListener a23223;
        ServletContextListener scl;
        EmbeddedServletContainerInitializedEvent aa232323;

        ServerEndpoint se;
        WebMvcProperties www;
        WebMvcConfigurer aa22323;
        WebMvcAutoConfiguration abwbe232323;
        WebMvcRegistrations eger4334;

        WebApplicationInitializer wainit;
        SpringBootServletInitializer sbsinit;
        AbstractContextLoaderInitializer weoiow23;
        AbstractDispatcherServletInitializer absdispsvlinit;
        AbstractAnnotationConfigDispatcherServletInitializer a2oinoi23;

        ServletContext sc;

        EnableAutoConfiguration aaa2323232112;
        CassandraAutoConfiguration ebqerbqerb2323;
        MongoAutoConfiguration d4gn3oi43o4ngoi34g;
        MongoDataAutoConfiguration qevqerbqerb23;
        MongoRepositoriesAutoConfiguration qoerinqeorbnoqerbno23902903;

        HelloAutoConfiguration aaaa23223;

        TransactionManagerCustomizers aaa232323;

        DataSourceAutoConfiguration erbeqb2223;


        //  ############################ WebMVC

        DispatcherServletAutoConfiguration erbqerbqeb323;
        HttpMessageConvertersAutoConfiguration g3g34g43;
        ServerPropertiesAutoConfiguration gr34go3ini3o4g;
        WebClientAutoConfiguration vrebe43g34g;
        EmbeddedServletContainerAutoConfiguration lnoienrboerb34g;


        //  ############################ WebMvc AutoConfiguration

        EnableWebMvc afef2232323;
        WebMvcAutoConfiguration fwefweg34ginoi3n4g;
        WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter g34g4hnoi42nho24ohi;
        WebMvcConfigurerAdapter ewgtwrth3223;
        WebMvcConfigurer rbqebrqe43g;

        /*
        *
        * https://www.youtube.com/watch?v=uDl1qlJWE7A
        *
        * https://www.youtube.com/watch?v=viP3VCx1X6w
        *
        * */

        ServletListenerRegistrationBean onionoi34343;
        FilterRegistrationBean eroinoi23224;
        ServletRegistrationBean oeqrinboi45981450801;

        ImportBeanDefinitionRegistrar lknlknlk3434;
        ImportSelector g34g34;
        AutoConfigurationImportSelector greon3o4i;


        //  ############################ Initializer

        /**
         * 类 ServletContainerInitializer 的 子类的 onStartup 方法是一个web应用中，我们的代码可以控制到的最早时间点。
         * 其 子类 SpringServletContainerInitializer 专门处理各种 WebApplicationInitializer；
         * 循环地 调起 各个 WebApplicationInitializer 的子类 的 方法 onStartup。
         *
         * 简单地说，SpringServletContainerInitializer 负责将 ServletContext 实例化并委托
         * 给用户定义的 WebApplicationInitializer 实现。
         * 然后每个 WebApplicationInitializer 负责完成初始化 ServletContext 的实际工作。
         * */
        ServletContainerInitializer rthr34oi;
            WsSci swioeowie;
        AbstractDispatcherServletInitializer g34go3igoi3o4ginoi;

        SpringServletContainerInitializer noin340h89034;
            LogbackServletContainerInitializer aaa34g09340g9j09;
            MyServletContainerInitializer g3ig039g093k4g0k;

        WebApplicationInitializer niog340;
            SpringBootServletInitializer noi3480384g;
            AbstractContextLoaderInitializer n03n4g083490n;
                AbstractDispatcherServletInitializer n034ng03409g;
                    AbstractAnnotationConfigDispatcherServletInitializer aaa0394g0934g;
            JerseyAutoConfiguration.JerseyWebApplicationInitializer gbgbg098hg34g34;
            MyWebApplicationInitializer f3gi3ng3o4igno3in4ogi3o4gio;

        /**
         * SpringBoot 启动中，会查找 ApplicationContextInitializer 的子类，
         * 调起 其中的 各个 方法initialize。
         * */
        ApplicationContextInitializer verver34gj03g093j940;
            ServletContextApplicationContextInitializer g3oi3oi4gnoi;
            DelegatingApplicationContextInitializer g3o4g039g409;
            ServerPortInfoApplicationContextInitializer g34og34g09340g9;
            ContextIdApplicationContextInitializer g34ing034g093j4gj9;
            ConfigurationWarningsApplicationContextInitializer g34n0934g09j0;
            AutoConfigurationReportLoggingInitializer g34oin304g09340g9;
            MyApplicationContextInitializer1 init34f23f09j091;
            MyApplicationContextInitializer2 init34f23f09j092;
        /*
            ## springboot
            # Application Context Initializers
            org.springframework.context.ApplicationContextInitializer=\
            org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,\
            org.springframework.boot.context.ContextIdApplicationContextInitializer,\
            org.springframework.boot.context.config.DelegatingApplicationContextInitializer,\
            org.springframework.boot.context.embedded.ServerPortInfoApplicationContextInitializer

            ## springboot-autoconfigure
            # Initializers
            org.springframework.context.ApplicationContextInitializer=\
            org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,\
            org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer
        */

        //  ############################ PostProcessor

        BeanPostProcessor wepnip23p0g092;
            ServletContextAwareProcessor g34onoi3n4ogi34;
                WebApplicationContextServletContextAwareProcessor g3gon3oi4g;
            AbstractAdvisingBeanPostProcessor g34gpm03m094g;
                AbstractBeanFactoryAwareAdvisingPostProcessor g3009304g9;
                    AsyncAnnotationBeanPostProcessor g30j0394jg0934;
            BeanValidationPostProcessor g34ogn0934g;
            InstantiationAwareBeanPostProcessor g340j09g3409g34g;
                CommonAnnotationBeanPostProcessor gerg34g0934g09;
            EmbeddedServletContainerCustomizerBeanPostProcessor g34j093j4g093j4g0934g;
        //  ApplicationContextAwareProcessor
        EmbeddedServletContainerAutoConfiguration.BeanPostProcessorsRegistrar o34ig0m0384g;
        BeanFactoryPostProcessor g3g0934g09304;
        //  ServletComponentRegisteringPostProcessor aaa3og4in3ogino34;
            BeanDefinitionRegistryPostProcessor ff39h0394jg0934;
                ConfigurationClassPostProcessor g340g3n09gn0934jg034;
            ConfigurationBeanFactoryMetaData h0394g0934;
            PropertyResourceConfigurer a0g390g93j04;
                PlaceholderConfigurerSupport g3o4gin3oigo3i4;
                    PropertySourcesPlaceholderConfigurer aa3goi3nogi34;
            AbstractDependsOnBeanFactoryPostProcessor oi34ngo3n4oig3io4;
                EntityManagerFactoryDependsOnPostProcessor jg093j409g34;
                MongoClientDependsOnBeanFactoryPostProcessor g3gj09j039g4;


        //  ############################ Listener

        /**
         *
         * 类 SpringApplicationRunListener 通过 自动配置被调起，
         * 其 子类 EventPublishingRunListener 专门处理 各种 ApplicationListener；
         * 循环地 调起 各个 ApplicationListener 的子类。
         * */

        SpringApplicationRunListener aprlsnr;
            EventPublishingRunListener g303049jg09;
            MySprAppRunLsnr g340g309g039k4g09;

        ApplicationListener allal;
            MyEnvReadyListener gj09j0934g;
            MyApplicationPreparedListener g30034g;
            MyApplicationStartingListener gj093jg90j3904g;
            MyApplicationFailListener gj039j4g093049g;
            MyApplicationReadyListener gj03j4g093j40g9;
            MyContextRefreshedListener g309j039g40934;
            MyContextClosedListener gj039jg093409g;
            MyContextStartedListener gj03jg903049j;
            MyContextStoppedListener gj039g03940g934g;
        /*
            ## springboot
            # Application Listeners
            org.springframework.context.ApplicationListener=\
            org.springframework.boot.ClearCachesApplicationListener,\
            org.springframework.boot.builder.ParentContextCloserApplicationListener,\
            org.springframework.boot.context.FileEncodingApplicationListener,\
            org.springframework.boot.context.config.AnsiOutputApplicationListener,\
            org.springframework.boot.context.config.ConfigFileApplicationListener,\
            org.springframework.boot.context.config.DelegatingApplicationListener,\
            org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener,\
            org.springframework.boot.logging.ClasspathLoggingApplicationListener,\
            org.springframework.boot.logging.LoggingApplicationListener

            ## springboot-autoconfigure
            # Application Listeners
            org.springframework.context.ApplicationListener=\
            org.springframework.boot.autoconfigure.BackgroundPreinitializer
        */

        ApplicationEvent g3ig30g0934gk09k;
            SpringApplicationEvent g3g34g34g;
                ApplicationEnvironmentPreparedEvent f3409309g4;
                ApplicationFailedEvent verver340gm09;
                ApplicationReadyEvent g34090934gj0j;
                ApplicationStartingEvent g3409093kg0934;
            ApplicationContextEvent g3m09m34g09;
                ContextRefreshedEvent g30m093jg0934;
                ContextStartedEvent go3ig093094gk;
                ContextStoppedEvent go3i4g093049g0k;

        //  ############################ Servlet Configure


        //  ############################ Servlet Listener

        ServletContextListener oieoivoir34g34g;
            ContextLoaderListener vr3robinoi3bnio;
            WebAppRootListener j0923jf02093;
            ContextCleanupListener fff34ogin3oigo43i;
            WsContextListener aaa3gpompopo;
            IntrospectorCleanupListener fgogoi34goi;


        //  ############################ Spring Handlers
        /*
            http://www.springframework.org/schema/c=org.springframework.beans.factory.xml.SimpleConstructorNamespaceHandler
            http://www.springframework.org/schema/p=org.springframework.beans.factory.xml.SimplePropertyNamespaceHandler
            http://www.springframework.org/schema/aop=org.springframework.aop.config.AopNamespaceHandler
            http://www.springframework.org/schema/util=org.springframework.beans.factory.xml.UtilNamespaceHandler
            http://www.springframework.org/schema/context=org.springframework.context.config.ContextNamespaceHandler
            http://www.springframework.org/schema/jee=org.springframework.ejb.config.JeeNamespaceHandler
            http://www.springframework.org/schema/lang=org.springframework.scripting.config.LangNamespaceHandler
            http://www.springframework.org/schema/task=org.springframework.scheduling.config.TaskNamespaceHandler
            http://www.springframework.org/schema/cache=org.springframework.cache.config.CacheNamespaceHandler
            http://www.springframework.org/schema/jdbc=org.springframework.jdbc.config.JdbcNamespaceHandler
            http://www.springframework.org/schema/oxm=org.springframework.oxm.config.OxmNamespaceHandler
            http://www.springframework.org/schema/tx=org.springframework.transaction.config.TxNamespaceHandler
            http://www.springframework.org/schema/mvc=org.springframework.web.servlet.config.MvcNamespaceHandler
        */

        MvcNamespaceHandler g3g34g3423232g;
    }

}
