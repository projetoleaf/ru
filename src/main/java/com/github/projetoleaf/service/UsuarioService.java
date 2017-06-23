package com.github.projetoleaf.service;

import com.github.projetoleaf.data.Usuario;

public interface UsuarioService {

	Usuario buscar(Integer id);
	
    Usuario salvar(Usuario usuario);

}
