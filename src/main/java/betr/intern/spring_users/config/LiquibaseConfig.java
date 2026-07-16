package betr.intern.spring_users.config;

import betr.intern.spring_users.migration.UserStaffMigration;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

  @Value("${spring.mongodb.uri}")
  private String mongoUri;

  @Bean
  public Liquibase liquibase(final UserStaffMigration userStaffMigration) throws Exception {

    final MongoLiquibaseDatabase database =
        (MongoLiquibaseDatabase)
            DatabaseFactory.getInstance()
                .openDatabase(mongoUri, null, null, null, new ClassLoaderResourceAccessor());


    final Liquibase liquibase =
        new Liquibase(
            "db/changelog/changelog-master.json",
            new ClassLoaderResourceAccessor(),
            database);


    liquibase.clearCheckSums();

    liquibase.update("");

    return liquibase;
  }
}
