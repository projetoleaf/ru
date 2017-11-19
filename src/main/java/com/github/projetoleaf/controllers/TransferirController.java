package com.github.projetoleaf.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class TransferirController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private TipoValorRepository tipoValorRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	private Cliente cliente;
	private Cliente clienteTransferido;
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
	
	@PostMapping("/verificartransferir")
	public @ResponseBody String verificarTransferir() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		if(verificarReservaPaga())
			return "ok";
		
		return "erro";
    }
	
	@RequestMapping("/transferir")
	public String carregarTransferir(Model model) {
		if(verificarReservaPaga()) {
			List<ReservaItem> datasPagas = reservaItemRepository.buscarReservasPagaSubsidiada(cliente.getId());
	        
	        List<String> datas = new ArrayList<String>();
	        
			for(ReservaItem item : datasPagas)
				datas.add(formatar.format(item.getCardapio().getData()));
			
			model.addAttribute("datasPagas", datas);
			return "transferir";
		}
		
		return "boasVindas";
    }
	
	@PostMapping("/verificartransferencia")
	public @ResponseBody String verificarTransferencia(@RequestParam("cpf") String cpf, @RequestParam(value = "datas[]", required = false) String[] datas) throws JSONException {
		JSONObject json = new JSONObject();
		
		clienteTransferido = clienteRepository.findByCpf(cpf);
		
		Boolean verifica = false;
		
		if(datas == null)
			json.put("erro", "data");
		else if(cpf.equals(cliente.getCpf()))
			json.put("erro", "cpfigual");
		else if(cpf == "" || clienteTransferido == null)
			json.put("erro", "cpf");
		else if(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getDescricao() != clienteCategoriaRepository.findByCliente(clienteTransferido).getCategoria().getDescricao())
			json.put("erro", "categoria");
		else
		{
			List<ReservaItem> reservas = reservaItemRepository.reservasDoClienteTransferido(clienteTransferido.getId());
			for(ReservaItem reserva : reservas) {
				for(String data : datas) {
					if(formatar.format(reserva.getCardapio().getData()).equals(data))
						verifica = true;
				}
			}
			
			if(verifica)
				json.put("erro", "reservado");
			else {
				json.put("sucesso", new Boolean(true));
				json.put("nome", clienteTransferido.getNome());
			}
		}
		
		return json.toString();
    }
	
	@PostMapping("/efetuartransferencia")
	public @ResponseBody void efetuarTransferencia(@RequestParam("cpf") String cpf, @RequestParam(value = "datas[]", required = false) String[] datas) throws ParseException {		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		cliente = clienteRepository.findByIdentificacao(authentication.getName());
		
		clienteTransferido = clienteRepository.findByCpf(cpf);
		
		Reserva reservaTransferida = new Reserva();
		reservaTransferida.setCliente(clienteTransferido);
		reservaTransferida.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
		reservaTransferida.setDataReserva(new Timestamp(System.currentTimeMillis()));
		reservaRepository.save(reservaTransferida);
		
		Reserva ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(clienteTransferido);
		
		for(String data : datas) {
			ReservaItem reservaItemCliente = reservaItemRepository.buscarReserva(cliente.getId(), formatar.parse(data));
			reservaItemCliente.setStatus(statusRepository.findByDescricao("Transferida"));
			reservaItemRepository.save(reservaItemCliente);
			
			ReservaItem reservaItemTransferida = new ReservaItem();
			reservaItemTransferida.setReserva(ultimaReserva);
			reservaItemTransferida.setCardapio(cardapioRepository.findByData(formatar.parse(data)));
			reservaItemTransferida.setStatus(statusRepository.findByDescricao("Recebida"));
			reservaItemTransferida.setTipoRefeicao(reservaItemCliente.getTipoRefeicao());
			reservaItemTransferida.setExtrato(null);
			
			reservaItemRepository.save(reservaItemTransferida);
		}
    }
	
	public boolean verificarReservaPaga() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		cliente = clienteRepository.findByIdentificacao(authentication.getName());
		
		if(!reservaItemRepository.buscarReservasPagaSubsidiada(cliente.getId()).isEmpty())
			return true;
		
		return false;
	}
}