package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);
}
