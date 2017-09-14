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
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteTipoRefeicao;
import com.github.projetoleaf.beans.PrimeiroAcesso;
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
		    	model.addAttribute("primeiroAcesso", new PrimeiroAcesso());
		    	model.addAttribute("categoria", categoriaRepository.findAll());
				model.addAttribute("tipoRefeicao", tipoRefeicaoRepository.findAll());
		    	retorno = abrirPrimeiroAcesso(model); 
		    }
		}
		
		return retorno;
	}
	
	public String abrirPrimeiroAcesso(Model model) {						
        return "primeiroAcesso";
    }
	
    @PostMapping("/primeiroAcesso/salvar")
    public String salvarPrimeiroLogin(Model model, @ModelAttribute("primeiroAcesso") PrimeiroAcesso primeiroAcesso, BindingResult result) {
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
        		    
        		    Cliente cliente = new Cliente();
        		    
        		    System.out.println("Primeiro acesso bugado" + primeiroAcesso.getDataNascimento());
        		    
        		    Cliente clienteAtualizado = new Cliente();
        		    clienteAtualizado.setIdentificacao(identificacao);
        		    clienteAtualizado.setCpf(cliente.imprimeCPF(cpf));
        		    clienteAtualizado.setNome(nome);
        		    clienteAtualizado.setDataNascimento(primeiroAcesso.getDataNascimento());
        		    clienteAtualizado.setDataCriado(timestamp);
        		    clienteAtualizado.setCreditos(creditos);
        		    clienteAtualizado.setBiometria("N");
        		    
        		    clienteRepository.save(clienteAtualizado);
        		    
        		    ClienteTipoRefeicao clienteTipoRefeicao = new ClienteTipoRefeicao();
        		    clienteTipoRefeicao.setCliente(clienteAtualizado);
        		    clienteTipoRefeicao.setTipoRefeicao(primeiroAcesso.getTipoRefeicao());
        		    clienteTipoRefeicao.setAtivo(true);
        		    
        		    clienteTipoRefeicaoRepository.save(clienteTipoRefeicao);
        		    
        		    ClienteCategoria clienteCategoria = new ClienteCategoria();
        		    clienteCategoria.setCliente(clienteAtualizado);
        		    clienteCategoria.setCategoria(primeiroAcesso.getCategoria());
        		    Timestamp timestamp2 = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
        		    clienteCategoria.setDataInicio(timestamp2); 
        		    clienteCategoria.setDataFim(null);
        		    clienteCategoria.setMatricula(11111111);
        		    
        		    clienteCategoriaRepository.save(clienteCategoria);
                    
                    log.info(clienteAtualizado.toString() + " gravado com sucesso");
                    model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o primeiro acesso" }, null));
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