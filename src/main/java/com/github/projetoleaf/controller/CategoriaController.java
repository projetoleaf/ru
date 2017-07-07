package com.github.projetoleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.github.projetoleaf.data.Categoria;
import com.github.projetoleaf.dto.CategoriaDTO;
import com.github.projetoleaf.service.CategoriaService;

@Controller
@RequestMapping("/desktop")
public class CategoriaController {
	
	@Autowired
    private CategoriaService categoriaService;
	
	@GetMapping("/categoria")
	public String tipo(Model model) {
		model.addAttribute("categorias", categoriaService.listar());
		return "categoria";
	}
	
	@GetMapping("/adicionarCategoria")
	public String adicionarTipo(Model model) {
		model.addAttribute("categoria", new CategoriaDTO());
		return "adicionarCategoria";
	}
	
	@GetMapping(value="/editarCategoria/{id}")
    public ModelAndView editarCategoria(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.buscar(id);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setDescricao(categoria.getDescricao());
        
        return new ModelAndView("editarCategoria","command", categoriaDTO);
    }
	
	@PostMapping(value="/salvarCategoria")
    public String salvarCategoria(@ModelAttribute("categoria") CategoriaDTO categoriaDTO, BindingResult result) {
        Categoria categoria;

        if (categoriaDTO.getId() != null) {
        	categoria = categoriaService.buscar(categoriaDTO.getId());
        } else {
        	categoria = new Categoria();
        }
        
        categoria.setDescricao(categoriaDTO.getDescricao());
        
        categoriaService.incluir(categoria);
        
        return "redirect:/desktop/categoria";

    }
	 
	@PostMapping(value="/incluirCategoria")
    public String incluirCategoria(@ModelAttribute CategoriaDTO categoriaDTO, BindingResult result) {
        Categoria categoria;

        if (categoriaDTO.getId() != null) {
        	categoria = categoriaService.buscar(categoriaDTO.getId());
        } else {
        	categoria = new Categoria();
        }
        
        categoria.setDescricao(categoriaDTO.getDescricao());
        
        categoriaService.incluir(categoria);
        
        return "redirect:/desktop/categoria";

    }
	
	@PostMapping(value="/excluirCategoria/{id}")
    public String excluirCategoria(@PathVariable Integer id) {		
		categoriaService.excluir(id);

        return "redirect:/desktop/categoria";
    }
	
}