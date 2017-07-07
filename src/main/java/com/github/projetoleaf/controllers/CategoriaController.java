package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.dto.CategoriaDTO;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.service.CategoriaService;

@Controller
@RequestMapping("/manutencao/categorias")
public class CategoriaController {
	
	/*@Autowired
    private CategoriaService categoriaService;*/
	
	private CategoriaRepository repository;
	
	@GetMapping("/pesquisar")
	public String pesquisar(Model model) {
		model.addAttribute("categorias", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/manutencao/categorias/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluir(Model model) {
		model.addAttribute("categoria", new CategoriaDTO());
		return "/manutencao/categorias/incluir";
	}
	
	@GetMapping(value="/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id, Model model) {
        Categoria categoria = repository.findOne(id);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setDescricao(categoria.getDescricao());
        categoriaDTO.setValor_sem_subsidio(categoria.getValor_sem_subsidio());
        categoriaDTO.setValor_com_subsidio(categoria.getValor_com_subsidio());
        
        return new ModelAndView("/manutencao/categorias/editar","command", categoriaDTO);
    }
	
	@PostMapping(value="/salvar")
    public String salvar(@ModelAttribute("categoria") CategoriaDTO categoriaDTO, BindingResult result) {
        Categoria categoria;

        if (categoriaDTO.getId() != null) {
        	categoria = repository.findOne(categoriaDTO.getId());
        } else {
        	categoria = new Categoria();
        }
        
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setValor_sem_subsidio(categoriaDTO.getValor_sem_subsidio());
        categoria.setValor_com_subsidio(categoriaDTO.getValor_com_subsidio());
        
        repository.save(categoria);
        
        return "redirect:/manutencao/categorias/pesquisar";

    }
	
	@PostMapping(value="/excluir/{id}")
    public String excluir(@PathVariable Integer id) {		
		repository.delete(id);

		return "redirect:/manutencao/categorias/pesquisar";
    }
	
}