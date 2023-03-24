package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.CategoriaRecebimento;
import io.github.tibetteixeira.api.v1.domain.model.Recebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {

    List<Recebimento> findByCategoria(CategoriaRecebimento categoriaRecebimento);
}
