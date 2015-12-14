package csb.config
import csb.aspect.TransformLogger
import csb.model.DataProviders
import csb.model.Staging
import csb.service.GeoJsonService
import csb.service.ITransformService
import csb.service.SubmitService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
@EnableAspectJAutoProxy
class AppConfig {

    private static final Logger logger =
            LoggerFactory.getLogger AppConfig.class

    private String activeProfile

    private def config

    public AppConfig() {

        // Get active profile
        this.activeProfile = System.getProperty "spring.profiles.active"
        logger.debug "activeProfile=${activeProfile}"
        config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())
    }

    // Development
//    @Bean
//    public DataSource embeddedDataSource() {
//        return new EmbeddedDatabaseBuilder()
//        .setType(EmbeddedDatabaseType.H2)
//                .build()
//    }

//    @Bean
//    public BasicDataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("org.h2.Driver");
//        ds.setUrl("jdbc:h2:tcp://localhost/mem/csb");
//        ds.setUsername("sa");
//        ds.setPassword("");
//        ds.setInitialSize(5);
//        ds.setMaxActive(10);
//        return ds;
//    }

//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase("H2");
//        adapter.setShowSql(true);
//        adapter.setGenerateDdl(true);
//        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
//        return adapter;
//    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            DataSource embeddedDataSource, JpaVendorAdapter jpaVendorAdapter) {
//        LocalContainerEntityManagerFactoryBean emfb =
//                new LocalContainerEntityManagerFactoryBean()
//        emfb.setDataSource( dataSource )
//        emfb.setJpaVendorAdapter( jpaVendorAdapter )
//        emfb.setPackagesToScan( "csb.model" )
//        return emfb;
//    }

    @Bean
    public DataProviders dps() {

        logger.debug "DataProviders bean construction..."

        DataProviders dps = new DataProviders( config.data.providers )

        return dps

    }

    @Bean
    public Staging staging() {

        logger.debug "Staging bean construction..."

        // get active profile
        String activeProfile = System.getProperty "spring.profiles.active"
        def config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())
        
        Staging staging = new Staging( config.staging.dir.map )
        return staging

    }

    @Bean
    public ITransformService ss() {

        ITransformService ss = new SubmitService( staging() )
        return ss

    }

    @Bean
    public ITransformService gs() {

        ITransformService gs = new GeoJsonService( dps() )
        return gs

    }

    @Bean
    public TransformLogger tLogger() {

        return new TransformLogger()

    }

}
