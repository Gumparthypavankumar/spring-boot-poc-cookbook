package com.pavan;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.pavan.domain.User;
import com.pavan.repository.UserRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class DistributedSchedulingApplication {

  private static final Logger log = LoggerFactory.getLogger(DistributedSchedulingApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DistributedSchedulingApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(
      final TransactionTemplate transactionTemplate,
      final UserRepository userRepository
  ) {

    return args -> {
      List<User> users = new LinkedList<>();
      int batchSize = 100;
      log.info("Begin populating fake data...");
      for (int i = 0; i <= 1_000; ++i) {
        Faker faker = new Faker();
        Name name = faker.name();
        User user = new User(name.firstName(), name.lastName(),
            String.format("%s%d@email.com", StringUtils.trimAllWhitespace(name.lastName()), i));
        users.add(user);
        if (i % batchSize == 0) {
          transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
              userRepository.saveAll(users);
            }
          });
          Thread.sleep(1_000);
          users.clear();
        }
      }
      log.info("Ending populating fake data...");
    };
  }

  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> Optional.of("System User");
  }


}