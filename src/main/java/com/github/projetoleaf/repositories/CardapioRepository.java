package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.Cardapio;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {

}