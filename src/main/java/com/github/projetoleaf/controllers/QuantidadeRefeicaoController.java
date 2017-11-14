package com.github.projetoleaf.controllers;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.projetoleaf.beans.QuantidadeRefeicao;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.QuantidadeRefeicaoRepository;

import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Slf4j
@Controller
@RequestMapping("/quantidadesRefeicoes")
public class QuantidadeRefeicaoController {

	@Autowired
	private MessageSource config;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private QuantidadeRefeicaoRepository repository;

	@GetMapping
	public String pesquisarQuantidadeRefeicao(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemQuantidades", repository.findAll(new Sort(Sort.Direction.ASC, "id")));
		return "/quantidadesRefeicoes/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirQuantidadeRefeicao(Model model) {
		model.addAttribute("quantidadeRefeicao", new QuantidadeRefeicao());
		return abrirCadastroQuantidadeRefeicao(model);
	}

	@GetMapping("/editar/{id}")
	public String editarQuantidadeRefeicao(@PathVariable Long id, Model model) {
		QuantidadeRefeicao quantidadeRefeicao = repository.findOne(id);
		model.addAttribute("quantidadeRefeicao", quantidadeRefeicao);
		return abrirCadastroQuantidadeRefeicao(model);
	}

	public String abrirCadastroQuantidadeRefeicao(Model model) {
		return "/quantidadesRefeicoes/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarQuantidadeRefeicao(Model model,
			@ModelAttribute("quantidadeRefeicao") @Valid QuantidadeRefeicao quantidadeRefeicao, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				quantidadeRefeicao = repository.save(quantidadeRefeicao);
				log.info(quantidadeRefeicao.toString() + " gravada com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "a quantidade de refeição" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/quantidadesRefeicoes";
	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirQuantidadeRefeicao(RedirectAttributes ra, @RequestParam("subsidiada") int subsidiada) throws JSONException {
		JSONObject json = new JSONObject();
		
		try {
			Long id = repository.findBySubsidiada(subsidiada).getId();
			repository.delete(id);
			json.put("sucesso", new Boolean(true));
		} catch (Exception ex) {
			json.put("erro", "exclusao");
		}

		return json.toString();
	}
}