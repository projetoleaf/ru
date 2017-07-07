package com.github.projetoleaf.service;

import com.github.projetoleaf.data.Feriado;
import com.github.projetoleaf.repository.FeriadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeriadoServiceImpl implements FeriadoService {

    @Autowired
    FeriadoRepository feriadoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Feriado> listar() {
        return feriadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Feriado buscar(Integer id) {
        return feriadoRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public Feriado incluir(Feriado feriado) {
        return feriadoRepository.save(feriado);
    }
    
    @Override
    @Transactional
    public void excluir(Integer id) {
    	feriadoRepository.delete(id);
    }
}