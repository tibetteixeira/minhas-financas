package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRecebimentoRepository extends JpaRepository<CategoriaRecebimento, Integer> {

    List<CategoriaRecebimento> findByDescricaoContainsIgnoreCase(String descricao);
}
