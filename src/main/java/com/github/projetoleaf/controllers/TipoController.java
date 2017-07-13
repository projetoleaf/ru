package com.github.projetoleaf.controllers;

import javax.validation.Valid;
import org.apache.log4j.Logger;
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
import com.github.projetoleaf.repositories.TipoRepository;
import com.github.projetoleaf.beans.Tipo;

@Controller
@RequestMapping("/tipos")
public class TipoController {
	
	@Autowired
    private TipoRepository repository;
	
	@Autowired
    private MessageSource config;
	
	Logger log = Logger.getLogger(TipoController.class);
	
	@GetMapping
	public String pesquisarTipo(Model model) {
		model.addAttribute("listagemTipos", repository.findAll(new Sort(Sort.Direction.ASC, "descricao")));
		return "/tipos/pesquisar";
	}
	
	@GetMapping("/incluir")
	public String incluirTipo(Model model) {
		model.addAttribute("tipo", new Tipo());
		return abrirCadastroTipo(model);
	}
	
	@GetMapping("/editar/{id}")
    public String editarTipo(@PathVariable Integer id, Model model) {
        Tipo tipo = repository.findOne(id);
        model.addAttribute("tipo", tipo);
        return abrirCadastroTipo(model);
    }
	
	public String abrirCadastroTipo(Model model) {
        return "/tipos/cadastro";
    }
	 
	@PostMapping("/salvar")
    public String salvarTipo(Model model, @ModelAttribute("tipo") @Valid Tipo tipo, BindingResult result) {
		try {
            if (!result.hasErrors()) {
                Tipo tipoAtualizado = repository.save(tipo);
                log.info(tipoAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o tipo" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return abrirCadastroTipo(model);

    }
	
	@GetMapping("/excluir/{id}")
    public String excluirTipo(RedirectAttributes ra, @PathVariable Integer id) {		
		try {
            repository.delete(id);
            log.info("Tipo #" + id + " excluído com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o tipo" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }

        return "redirect:/tipos";
    }
	
}