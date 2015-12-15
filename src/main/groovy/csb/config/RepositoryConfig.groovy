package csb.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by dneufeld on 12/13/15.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = ["csb.model"] )
@EnableJpaRepositories(basePackages = ["csb.repos"] )
@EnableTransactionManagement
class RepositoryConfig {

    //It's all about the annotations.

}
