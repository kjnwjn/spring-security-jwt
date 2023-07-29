package net.quanpham.security.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(generator = "student_uuid")
    @GenericGenerator(name = "student_uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String pwd;
}
