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
import com.github.projetoleaf.repositories.FeriadoRepository;
import lombok.extern.slf4j.Slf4j;
import com.github.projetoleaf.beans.Feriado;

@Slf4j
@Controller
@RequestMapping("/feriados")
public class FeriadoController {
	
	@Autowired
    private FeriadoRepository repository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarFeriado(Model model) {
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
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o feriado" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return abrirCadastroFeriado(model);

    }
	
	@PostMapping("/excluir/{id}")
    public String excluirFeriado(RedirectAttributes ra, @PathVariable Long id) {			
		try {
            repository.delete(id);
            log.info("Feriado #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o feriado" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }

        return "redirect:/feriados";
    }
	
}