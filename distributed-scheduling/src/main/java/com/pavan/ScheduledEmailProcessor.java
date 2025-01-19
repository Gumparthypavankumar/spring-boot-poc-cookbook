package com.pavan;

import com.pavan.domain.User;
import com.pavan.enumeration.EmailDeliveryStatusType;
import com.pavan.repository.UserRepository;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * The type ScheduledEmailProcessor.
 *
 * @author gumparthypavankumar[pk] created on 12/01/25
 */

@SuppressWarnings("java:S2259")
@Service
@EnableScheduling
@EnableTransactionManagement
class ScheduledEmailProcessor {

  private final JavaMailSender mailSender;
  private final UserRepository userRepository;
  private final TransactionTemplate transactionTemplate;
  private static final String FROM_EMAIL = "blog@knowledge.in";

  ScheduledEmailProcessor(
      JavaMailSender mailSender,
      @NonNull TransactionTemplate transactionTemplate,
      UserRepository userRepository) {
    this.mailSender = mailSender;
    this.transactionTemplate = transactionTemplate;
    this.userRepository = userRepository;
  }

  @Scheduled(fixedDelay = 10_000)
  public void sendEmail() {
    boolean isPendingProcessing = true;
    while (isPendingProcessing) {
      isPendingProcessing = transactionTemplate.execute(this::doInTransaction);
    }
  }

  private Boolean doInTransaction(TransactionStatus status) {
    List<User> users =
        userRepository.findTop50ByEmailDeliveryStatus(EmailDeliveryStatusType.PENDING);
    if (users.isEmpty()) {
      return false;
    }
    users.forEach(user -> {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(FROM_EMAIL);
      message.setTo(user.getEmail());
      mailSender.send(message);
      user.setEmailDeliveryStatus(EmailDeliveryStatusType.DELIVERED);
      userRepository.save(user);
    });
    return true;
  }
}
