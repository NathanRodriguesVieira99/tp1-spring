package com.edu.infnet.tp1.repositories;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.edu.infnet.tp1.domain.models.organizacao.Organizacao;
import com.edu.infnet.tp1.domain.models.organizacao.Permission;
import com.edu.infnet.tp1.domain.models.organizacao.Role;
import com.edu.infnet.tp1.domain.models.organizacao.Usuario;
import com.edu.infnet.tp1.infrastructure.repositories.organizacao.OrganizacaoRepository;
import com.edu.infnet.tp1.infrastructure.repositories.organizacao.PermissionRepository;
import com.edu.infnet.tp1.infrastructure.repositories.organizacao.RoleRepository;
import com.edu.infnet.tp1.infrastructure.repositories.organizacao.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private OrganizacaoRepository organizacaoRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void persistNewUserWithExistingOrganization() {
    Organizacao org = new Organizacao();
    org.setNome("org-test-" + System.currentTimeMillis());
    Organizacao savedOrg = organizacaoRepository.save(org);

    Usuario u = new Usuario();
    u.setNome("Teste Persist");
    u.setEmail("teste.persist+" + System.currentTimeMillis() + "@example.com");
    u.setOrganizacao(savedOrg);

    Usuario saved = usuarioRepository.save(u);
    assertNotNull(saved.getId());
    Usuario reloaded = usuarioRepository.findById(saved.getId()).orElseThrow();
    assertEquals(savedOrg.getId(), reloaded.getOrganizacao().getId());
  }

  @Test
  void listRolesWithPermissionsAndUserWithRoles() {
    Organizacao org = new Organizacao();
    org.setNome("org-test-roles-" + System.currentTimeMillis());
    organizacaoRepository.save(org);

    Permission p = new Permission();
    p.setCode("PERM_TEST");
    p.setDescricao("perm test");
    permissionRepository.save(p);

    Role r = new Role();
    r.setNome("ROLE_TEST");
    r.setOrganizacao(org);
    r.getPermissions().add(p);
    roleRepository.save(r);

    Usuario u = new Usuario();
    u.setNome("User Roles Test");
    u.setEmail("user.roles.test+" + System.currentTimeMillis() + "@example.com");
    u.setOrganizacao(org);
    usuarioRepository.save(u);

    jdbcTemplate.update("INSERT INTO audit.user_roles (usuario_id, role_id, granted_at) VALUES (?,?,?)",
        u.getId(), r.getId(), OffsetDateTime.now());

    Optional<Usuario> opt = usuarioRepository.findByIdWithRolesAndPermissions(u.getId());
    assertTrue(opt.isPresent());
    Usuario user = opt.get();
    assertNotNull(user.getRoles());
    assertFalse(user.getRoles().isEmpty());
    Role loadedRole = user.getRoles().iterator().next();
    assertNotNull(loadedRole.getPermissions());
    assertFalse(loadedRole.getPermissions().isEmpty());

    Role rloaded = roleRepository.findById(r.getId()).orElseThrow();
    assertFalse(rloaded.getPermissions().isEmpty());
  }
}
