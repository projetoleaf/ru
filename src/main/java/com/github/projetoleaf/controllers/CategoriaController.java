package com.github.projetoleaf.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.repositories.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private MessageSource config;

	@Autowired
	private CategoriaRepository repository;

	@GetMapping
	public String pesquisarCategoria(Model model) {
		model.addAttribute("listagemCategorias", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/categorias/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return abrirCadastroCategoria(model);
	}

	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable Long id, Model model) {
		Categoria categoria = repository.findOne(id);

		model.addAttribute("categoria", categoria);

		return abrirCadastroCategoria(model);
	}

	public String abrirCadastroCategoria(Model model) {
		return "/categorias/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarCategoria(Model model, @ModelAttribute("categoria") @Valid Categoria categoria,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				categoria = repository.save(categoria);
				log.info(categoria.toString() + " gravada com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "a categoria" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/categorias";
	}

	@GetMapping("/excluir/{id}")
	public String excluirCategoria(RedirectAttributes ra, @PathVariable Long id) {
		try {
			repository.delete(id);
			log.info("Categoria #" + id + " excluída com sucesso");
			ra.addFlashAttribute("mensagemInfo",
					config.getMessage("excluidoSucesso", new Object[] { "a categoria" }, null));
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/categorias";
	}
}