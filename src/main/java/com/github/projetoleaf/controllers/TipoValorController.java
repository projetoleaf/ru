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

import com.github.projetoleaf.beans.TipoValor;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Slf4j
@Controller
@RequestMapping("/tiposValores")
public class TipoValorController {

	@Autowired
	private MessageSource config;

	@Autowired
	private TipoValorRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public String pesquisarTipoValor(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemTiposValores", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/tiposValores/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirTipoValor(Model model) {
		model.addAttribute("tipoValor", new TipoValor());
		return abrirCadastroTipoValor(model);
	}

	@GetMapping("/editar/{id}")
	public String editarTipoValor(@PathVariable Long id, Model model) {
		TipoValor TipoValor = repository.findOne(id);
		model.addAttribute("tipoValor", TipoValor);
		return abrirCadastroTipoValor(model);
	}

	public String abrirCadastroTipoValor(Model model) {
		return "/tiposValores/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarTipoValor(Model model, @ModelAttribute("tipoValor") @Valid TipoValor tipoValor,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				TipoValor tipoAtualizado = repository.save(tipoValor);
				log.info(tipoAtualizado.toString() + " gravado com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "o tipo valor" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/tiposValores";

	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirTipoValor(RedirectAttributes ra, @RequestParam("descricao") String descricao) throws JSONException {
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