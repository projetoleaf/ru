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

import com.github.projetoleaf.beans.PeriodoRefeicao;
import com.github.projetoleaf.repositories.PeriodoRefeicaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/periodosRefeicoes")
public class PeriodoRefeicaoController {

	@Autowired
	private MessageSource config;

	@Autowired
	private PeriodoRefeicaoRepository repository;

	@GetMapping
	public String pesquisarPeriodoRefeicao(Model model) {
		model.addAttribute("listagemPeriodos", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/periodosRefeicoes/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirPeriodoRefeicao(Model model) {
		model.addAttribute("periodoRefeicao", new PeriodoRefeicao());
		return abrirCadastroPeriodoRefeicao(model);
	}

	@GetMapping("/editar/{id}")
	public String editarPeriodoRefeicao(@PathVariable Long id, Model model) {
		PeriodoRefeicao periodoRefeicao = repository.findOne(id);
		model.addAttribute("periodoRefeicao", periodoRefeicao);
		return abrirCadastroPeriodoRefeicao(model);
	}

	public String abrirCadastroPeriodoRefeicao(Model model) {
		return "/periodosRefeicoes/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarPeriodoRefeicao(Model model,
			@ModelAttribute("periodoRefeicao") @Valid PeriodoRefeicao periodoRefeicao, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				periodoRefeicao = repository.save(periodoRefeicao);
				log.info(periodoRefeicao.toString() + " gravado com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "o período refeição" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/periodosRefeicoes";
	}

	@GetMapping("/excluir/{id}")
	public String excluirPeriodoRefeicao(RedirectAttributes ra, @PathVariable Long id) {
		try {
			repository.delete(id);
			log.info("Período #" + id + " excluído com sucesso");
			ra.addFlashAttribute("mensagemInfo",
					config.getMessage("excluidoSucesso", new Object[] { "o período refeição" }, null));
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/periodosRefeicoes";
	}
}