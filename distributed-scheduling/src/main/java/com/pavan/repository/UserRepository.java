package com.pavan.repository;

import com.pavan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The type UserRepository.
 *
 * @author gumparthypavankumar[pk] created on 28/12/24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
