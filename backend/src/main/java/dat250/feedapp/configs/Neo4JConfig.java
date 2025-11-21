package dat250.feedapp.configs;

import jakarta.persistence.EntityManagerFactory;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableNeo4jRepositories(basePackages = "dat250.feedapp.repositories.neo",
        transactionManagerRef = "neo4jTransactionManager")
@EnableJpaRepositories(basePackages = "dat250.feedapp.repositories.jpa",
        transactionManagerRef = "jpaTransactionManager")
public class Neo4JConfig {
    @Bean
    public Driver neo4jDriver() {
        //use "bolt://localhost:7687" if not running in docker compose
        return GraphDatabase.driver("bolt://neo4j:7687",
                AuthTokens.basic("neo4j", "password"));
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    public PlatformTransactionManager neo4jTransactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}
