package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

	Status findByDescricao(String descricao);
}