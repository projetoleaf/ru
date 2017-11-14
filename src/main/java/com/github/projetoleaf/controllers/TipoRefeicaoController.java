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

import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;

import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Slf4j
@Controller
@RequestMapping("/tiposRefeicoes")
public class TipoRefeicaoController {

	@Autowired
	private MessageSource config;

	@Autowired
	private TipoRefeicaoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public String pesquisarTipoRefeicao(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemTiposRefeicoes", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/tiposRefeicoes/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirTipoRefeicao(Model model) {
		model.addAttribute("tipoRefeicao", new TipoRefeicao());
		return abrirCadastroTipoRefeicao(model);
	}

	@GetMapping("/editar/{id}")
	public String editarTipoRefeicao(@PathVariable Long id, Model model) {
		TipoRefeicao tipoRefeicao = repository.findOne(id);
		model.addAttribute("tipoRefeicao", tipoRefeicao);
		return abrirCadastroTipoRefeicao(model);
	}

	public String abrirCadastroTipoRefeicao(Model model) {
		return "/tiposRefeicoes/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarTipoRefeicao(Model model, @ModelAttribute("tipoRefeicao") @Valid TipoRefeicao tipoRefeicao,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				TipoRefeicao tipoAtualizado = repository.save(tipoRefeicao);
				log.info(tipoAtualizado.toString() + " gravado com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "o tipo de refeição" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/tiposRefeicoes";

	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirTipoRefeicao(RedirectAttributes ra, @RequestParam("descricao") String descricao) throws JSONException {
		JSONObject json = new JSONObject();
		
		try {
			Long id = repository.findByDescricao(descricao).getId();	
			repository.delete(id);
			json.put("sucesso", new Boolean(true));
		} catch (Exception ex) {
			json.put("erro", "exclusao");
		}

		return json.toString();
	}
}