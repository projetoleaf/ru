package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.PeriodoRefeicaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cardapios")
public class CardapioController {

	@Autowired
	private MessageSource config;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private PeriodoRefeicaoRepository periodoRefeicaoRepository;
	
	private int count = 0;

	@GetMapping
	public String pesquisarCategoria(Model model) {
		count = 0;
		model.addAttribute("listagemCardapios", cardapioRepository.findAll(new Sort(Sort.Direction.ASC, "data")));
		return "/cardapios/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirCardapio(Model model) {
		count = 0;
		model.addAttribute("cardapio", new Cardapio());
		model.addAttribute("periodoRefeicao", periodoRefeicaoRepository.findAll());
		return abrirCadastroCardapio(model);
	}

	@GetMapping("/editar/{id}")
	public String editarCardapio(@PathVariable Long id, Model model) {
		count = 1;
		Cardapio cardapio = cardapioRepository.findOne(id);
		model.addAttribute("cardapio", cardapio);
		model.addAttribute("periodoRefeicao", periodoRefeicaoRepository.findAll());
		return abrirCadastroCardapio(model);
	}

	public String abrirCadastroCardapio(Model model) {
		model.addAttribute("count", count);
		return "/cardapios/cadastro";
	}
	
	@PostMapping("/verificar")
	@ResponseBody
	public int verificarData(@ModelAttribute("cardapio") Cardapio cardapio, @RequestParam("count") int count) {
		
		int retorno = 0;
		
		if (cardapioRepository.verificarDataEPeriodoRefeicao(cardapio.getData(),
				cardapio.getPeriodoRefeicao().getId()) == null) {
			
			if (feriadoRepository.findByData(cardapio.getData()) != null) {
				retorno = 1;
			}
		} else {
			retorno = 2;
		}
		
		if(retorno == 0 || count == 1) {
			retorno = 0;
			cardapioRepository.save(cardapio);
		}
				
		return retorno;
	}

	@GetMapping("/excluir/{id}")
	public String excluirCardapio(RedirectAttributes ra, @PathVariable Long id) {
		count = 0;
		try {
			cardapioRepository.delete(id);
			log.info("Cardápio #" + id + " excluído com sucesso");
			ra.addFlashAttribute("mensagemInfo",
					config.getMessage("excluidoSucesso", new Object[] { "o cardápio" }, null));
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/cardapios";
	}
}