package com.pavan.repository;

import com.pavan.domain.User;
import com.pavan.enumeration.EmailDeliveryStatusType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.util.List;
import org.hibernate.jpa.SpecHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

/**
 * The type UserRepository.
 *
 * @author gumparthypavankumar[pk] created on 28/12/24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({
      @QueryHint(name = SpecHints.HINT_SPEC_LOCK_TIMEOUT, value = "-2")
  })
  List<User> findTop50ByEmailDeliveryStatus(EmailDeliveryStatusType status);
}
