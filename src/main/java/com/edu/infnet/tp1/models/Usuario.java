package com.edu.infnet.tp1.models;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "audit", name = "usuarios")
@Getter
@Setter
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "senha_hash")
  private String senhaHash;

  @Column(name = "status")
  private String status;

  @Column(name = "ultimo_login_em")
  private OffsetDateTime ultimoLoginEm;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  // N usuários para 1 organizacao
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "organizacao_id", nullable = false)
  private Organizacao organizacao;

  // N usuários para N roles
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(schema = "audit", name = "user_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
}
