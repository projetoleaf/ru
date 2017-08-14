package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.ReservaItem;

@Repository
public interface ReservaItemRepository extends JpaRepository<ReservaItem, Long> {
	
}