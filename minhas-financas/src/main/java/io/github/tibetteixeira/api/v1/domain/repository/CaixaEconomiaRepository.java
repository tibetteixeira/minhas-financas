package io.github.tibetteixeira.api.v1.domain.repository;

import io.github.tibetteixeira.api.v1.domain.model.CaixaEconomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaixaEconomiaRepository extends JpaRepository<CaixaEconomia, Integer> {

    List<CaixaEconomia> findByNomeContainsIgnoreCase(String nome);
}
