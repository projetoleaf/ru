package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

}
