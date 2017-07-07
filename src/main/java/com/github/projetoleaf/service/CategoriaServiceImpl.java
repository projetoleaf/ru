package com.github.projetoleaf.service;

import com.github.projetoleaf.data.Categoria;
import com.github.projetoleaf.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscar(Integer id) {
        return categoriaRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public Categoria incluir(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    @Override
    @Transactional
    public void excluir(Integer id) {
    	categoriaRepository.delete(id);
    }
}