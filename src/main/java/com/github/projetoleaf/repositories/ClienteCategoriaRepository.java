package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.ClienteCategoria;

@Repository
public interface ClienteCategoriaRepository extends JpaRepository<ClienteCategoria, Long> {

}
