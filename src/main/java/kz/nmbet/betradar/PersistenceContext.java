package kz.nmbet.betradar;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing()
@EnableJpaRepositories(basePackages = {
        "kz.nmbet.betradar.dao.repository"
})
@EnableTransactionManagement
class PersistenceContext {
 

}
