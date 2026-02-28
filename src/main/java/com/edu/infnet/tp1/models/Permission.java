package com.edu.infnet.tp1.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "permissions")
@Getter
@Setter
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "descricao")
  private String descricao;

  // N permissions para N roles
  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  private Set<Role> roles = new HashSet<>();
}
