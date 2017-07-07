package com.github.projetoleaf.service;

import java.util.List;
import com.github.projetoleaf.data.Feriado;

public interface FeriadoService {
	
	List<Feriado> listar();

	Feriado buscar(Integer id);
	
	Feriado incluir(Feriado feriado);
    
    void excluir(Integer id);
}
