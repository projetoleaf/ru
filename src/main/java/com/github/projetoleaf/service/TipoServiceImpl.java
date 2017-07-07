package com.github.projetoleaf.service;

import com.github.projetoleaf.beans.Tipo;
import com.github.projetoleaf.repositories.TipoRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoServiceImpl implements TipoService {

    @Autowired
    TipoRepository tipoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Tipo> listar() {
        return tipoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo buscar(Integer id) {
        return tipoRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public Tipo incluir(Tipo tipo) {
        return tipoRepository.save(tipo);
    }
    
    @Override
    @Transactional
    public void excluir(Integer id) {
        tipoRepository.delete(id);
    }
}