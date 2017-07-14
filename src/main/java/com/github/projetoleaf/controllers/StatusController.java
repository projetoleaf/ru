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
import com.github.projetoleaf.repositories.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import com.github.projetoleaf.beans.Status;

@Slf4j
@Controller
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
    private StatusRepository repository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarStatus(Model model) {
		model.addAttribute("listagemStatus", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/status/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirStatus(Model model) {
		model.addAttribute("status", new Status());
		return abrirCadastroStatus(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarStatus(@PathVariable Long id, Model model) {
        Status status = repository.findOne(id);
        model.addAttribute("status", status);
        return abrirCadastroStatus(model);
    }
	
	public String abrirCadastroStatus(Model model) {
        return "/status/cadastro";
    }
	 
	@PostMapping("/salvar")
    public String salvarStatus(Model model, @ModelAttribute("status") @Valid Status status, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Status statusAtualizado = repository.save(status);
                log.info(statusAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o status" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return abrirCadastroStatus(model);

    }
	
	@GetMapping("/excluir/{id}")
    public String excluirStatus(RedirectAttributes ra, @PathVariable Long id) {		
		try {
            repository.delete(id);
            log.info("Status #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o status" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }

        return "redirect:/status";
    }
	
}