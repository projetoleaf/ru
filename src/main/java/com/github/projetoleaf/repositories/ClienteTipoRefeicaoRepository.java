package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.ClienteTipoRefeicao;

@Repository
public interface ClienteTipoRefeicaoRepository extends JpaRepository<ClienteTipoRefeicao, Long> {

}
