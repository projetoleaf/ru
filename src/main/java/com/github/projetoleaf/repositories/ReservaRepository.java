package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
