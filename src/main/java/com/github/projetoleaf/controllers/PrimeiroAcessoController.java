package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteTipoRefeicao;
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.beans.UsuarioDetails;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ClienteTipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PrimeiroAcessoController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;
	
	@Autowired
	private ClienteTipoRefeicaoRepository clienteTipoRefeicaoRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
    private MessageSource config;
	
	private String retorno;
	
	@GetMapping("/boasVindas")
	public String boasVindas(Model model) {		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();	
		   
		    Cliente cliente = clienteRepository.buscarCliente(identificacao);
		    
		    if(cliente != null) {		    	
		    	retorno = "boasVindas";
		    } else {
		    	model.addAttribute("primeiroAcesso", new Cliente());
		    	retorno = abrirPrimeiroAcesso(model); 
		    }
		}
		
		return retorno;
	}
	
	public String abrirPrimeiroAcesso(Model model) {						
        return "primeiroAcessoI";
    }
	
    @PostMapping("/primeiroAcesso/salvar")
    public String salvarPrimeiroLogin(Model model, @ModelAttribute("primeiroAcesso") Cliente cliente, BindingResult result) {
		try {
            if (!result.hasErrors()) {   
            	
            	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			
        		UsuarioDetails usuarioAtual = (UsuarioDetails) authentication.getDetails();
        		
        		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
        			
        		    String nome = usuarioAtual.getNome();
        		    String cpf = usuarioAtual.getCpf();
        		    String identificacao = authentication.getName();	
        		    BigDecimal creditos = new BigDecimal(0.00);
        		    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
        		    
        		    Cliente clienteAtualizado = new Cliente();
        		    clienteAtualizado.setIdentificacao(identificacao);
        		    clienteAtualizado.setCpf(cpf);
        		    clienteAtualizado.setNome(nome);
        		    clienteAtualizado.setDataNascimento(cliente.getDataNascimento());
        		    clienteAtualizado.setDataCriado(timestamp);
        		    clienteAtualizado.setCreditos(creditos);
        		    
        		    clienteRepository.save(clienteAtualizado);
                    
                    log.info(clienteAtualizado.toString() + " gravado com sucesso");
                    model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o primeiro acesso" }, null));
        		}              
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
		model.addAttribute("primeiroTipoRefeicao", new TipoRefeicao());
		model.addAttribute("tipoRefeicao", tipoRefeicaoRepository.findAll());
        return abrirPrimeiroTipoRefeicao(model);
    }	
    
    public String abrirPrimeiroTipoRefeicao(Model model) {						
        return "primeiroAcessoII";
    }
    
    @PostMapping("/primeiroTipoRefeicao/salvar")
    public String salvarPrimeiroTipoRefeicao(Model model, @ModelAttribute("primeiroTipoRefeicao") TipoRefeicao tipoRefeicao, BindingResult result) {
		try {
            if (!result.hasErrors()) {   
            	
            	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
        		
        		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
        			
        			String identificacao = authentication.getName();	
        			   
        		    Cliente cliente = clienteRepository.buscarCliente(identificacao); 
        		    
        		    ClienteTipoRefeicao clienteTipoRefeicao = new ClienteTipoRefeicao();
        		    clienteTipoRefeicao.setCliente(cliente);
        		    clienteTipoRefeicao.setTipoRefeicao(tipoRefeicao);
        		    clienteTipoRefeicao.setAtivo(true);
        		    
        		    clienteTipoRefeicaoRepository.save(clienteTipoRefeicao);
                    
                    log.info(clienteTipoRefeicao.toString() + " gravado com sucesso");
                    model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "a primeira refeição" }, null));
        		}              
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
		model.addAttribute("primeiraCategoriaCliente", new Categoria());
		model.addAttribute("categoriaCliente", categoriaRepository.findAll());
        return abrirPrimeiraCategoriaCliente(model);
    }	
    
    public String abrirPrimeiraCategoriaCliente(Model model) {						
        return "primeiroAcessoIII";
    }
    
    @PostMapping("/primeiraCategoriaCliente/salvar")
    public String salvarPrimeiraCategoriaCliente(Model model, @ModelAttribute("primeiraCategoriaCliente") Categoria categoria, BindingResult result) {
		try {
            if (!result.hasErrors()) {   
            	
            	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
        		
        		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
        			
        			String identificacao = authentication.getName();	
        			   
        		    Cliente cliente = clienteRepository.buscarCliente(identificacao);   
        		    
        		    ClienteCategoria clienteCategoria = new ClienteCategoria();
        		    clienteCategoria.setCliente(cliente);
        		    clienteCategoria.setCategoria(categoria);
        		    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
        		    clienteCategoria.setDataInicio(timestamp); 
        		    clienteCategoria.setDataFim(null);
        		    clienteCategoria.setMatricula(11111111);
        		    
        		    clienteCategoriaRepository.save(clienteCategoria);
                    
                    log.info(clienteCategoria.toString() + " gravado com sucesso");
                    model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "a primeira categoria" }, null));
        		}              
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
		
        return "redirect:/boasVindas";
    }	
}