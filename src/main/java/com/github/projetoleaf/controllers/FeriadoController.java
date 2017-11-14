package com.github.projetoleaf.controllers;

import java.util.Date;

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

import com.github.projetoleaf.beans.Feriado;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;

import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Slf4j
@Controller
@RequestMapping("/feriados")
public class FeriadoController {

	@Autowired
	private MessageSource config;

	@Autowired
	private FeriadoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public String pesquisarFeriado(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		model.addAttribute("listagemFeriados", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/feriados/pesquisar";
	}

	@GetMapping("/incluir")
	public String incluirFeriado(Model model) {
		model.addAttribute("feriado", new Feriado());
		return abrirCadastroFeriado(model);
	}

	@GetMapping("/editar/{id}")
	public String editarFeriado(@PathVariable Long id, Model model) {
		Feriado feriado = repository.findOne(id);
		model.addAttribute("feriado", feriado);
		return abrirCadastroFeriado(model);
	}

	public String abrirCadastroFeriado(Model model) {
		return "/feriados/cadastro";
	}

	@PostMapping("/salvar")
	public String salvarFeriado(Model model, @ModelAttribute("feriado") @Valid Feriado feriado, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				Feriado feriadoAtualizado = repository.save(feriado);
				log.info(feriadoAtualizado.toString() + " gravado com sucesso");
				model.addAttribute("mensagemInfo",
						config.getMessage("gravadoSucesso", new Object[] { "o feriado" }, null));
			}
		} catch (Exception ex) {
			log.error("Erro de processamento", ex);
			model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
		}

		return "redirect:/feriados";

	}

	@PostMapping("/excluir")
	public @ResponseBody String excluirFeriado(RedirectAttributes ra, @RequestParam("data") Date data) throws JSONException {
		JSONObject json = new JSONObject();
		
		try {
			Long id = repository.findByData(data).getId();
			repository.delete(id);
			json.put("sucesso", new Boolean(true));
		} catch (Exception ex) {
			json.put("erro", "exclusao");
		}

		return json.toString();
	}
}