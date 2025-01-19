package com.pavan.domain;

import com.pavan.enumeration.EmailDeliveryStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The type User.
 *
 * @author gumparthypavankumar[pk] created on 28/12/24
 */
@Entity
@Table(name = "cd_users")
public class User extends BaseEntity<Long> {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "email_delivery_status")
  @Enumerated(value = EnumType.STRING)
  private EmailDeliveryStatusType emailDeliveryStatus;

  public User() {
  }

  public User(String firstName, String lastName, String email,
              EmailDeliveryStatusType emailDeliveryStatus) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.emailDeliveryStatus = emailDeliveryStatus;
  }

  @Override
  public boolean isNew() {
    return this.getId() == null;
  }

  @Override
  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public EmailDeliveryStatusType getEmailDeliveryStatus() {
    return emailDeliveryStatus;
  }

  public void setEmailDeliveryStatus(EmailDeliveryStatusType emailDeliveryStatus) {
    this.emailDeliveryStatus = emailDeliveryStatus;
  }
}
