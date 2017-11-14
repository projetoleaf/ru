package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.Status;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.QuantidadeRefeicaoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@Controller
public class ReservarController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private TipoValorRepository tipoValorRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;
	
	@Autowired
	private QuantidadeRefeicaoRepository qtdeRefeicoesRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	private List<Date> todasAsDatasDaProximaSemana;
	private Cliente cliente;
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
	private List<String> datasBanco;
	private Calendar proximaSeg;
	private Calendar penultimoDiaUtil;
	private ClienteCategoria categoria;
	
	@PostMapping("/verificarreservar")
	public @ResponseBody String verificarReservar() throws JSONException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		return verificar();
    }
	
	@RequestMapping("/reservar")
	public String carregarReservar(Model model) throws JSONException {
		JSONObject json = new JSONObject(verificar());
		Boolean sucesso = json.getBoolean("sucesso");
		
		if(sucesso) {
			model.addAttribute("datas", datasBanco);
			model.addAttribute("tipoRefeicoes", tipoRefeicaoRepository.findAll());
			return "reservar";
		}
		
		return "boasVindas";
    }
	
	@PostMapping("/verificarreserva")
	public @ResponseBody String verificarReserva(@RequestParam(value = "datas[]", required = false) String[] datas, 
		   @RequestParam(value = "tipos[]", required = false) String[] tipos,
		   @RequestParam("pagamento") int pagamento) throws JSONException {
		JSONObject json = new JSONObject();
		Boolean verifica = true;
		if(datas == null) {
			json.put("erro", "data");
			verifica = false;
		}
		else if(pagamento == 1) {
			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);
			if(categoria.getCategoria().getValorComSubsidio().multiply(new BigDecimal(datas.length)).compareTo(saldo) > 0) {
				json.put("erro", "creditos");
				verifica = false;
			}
		} else {
			if(tipos.length == 0) {
				json.put("erro", "tipos");
				verifica = false;
			}
			else {
				for(String tipo : tipos) {
					if(tipo == "") {
						json.put("erro", "tipos");
						verifica = false;
					}
				}	
			}
		}
		
		if(verifica)
			json.put("sucesso", new Boolean(true));
		
		return json.toString();
    }
	
	@PostMapping("/efetuarreserva")
	public @ResponseBody String efetuarReserva(@RequestParam(value = "datas[]", required = false) String[] datas, 
		   @RequestParam(value = "tipos[]", required = false) String[] tipos,
		   @RequestParam("pagamento") int pagamento) throws JSONException, ParseException {
		JSONObject json = new JSONObject();
		
		todasAsDatasDaProximaSemana = new ArrayList<Date>();
		datasBanco = new ArrayList<String>();
		
		Boolean teste = true;
		if(verificarContagens()) {
			for (String data : datas) {
				if(!datasBanco.contains(data))
					teste = false;
			}
		} else
			teste = false;
		
		if(teste) {
			Reserva reserva = new Reserva();
			Extrato extrato = new Extrato();
			
			Extrato ultimoExtrato2 = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato2 != null ? ultimoExtrato2.getSaldo() : new BigDecimal(0.00);
			
			reserva.setCliente(cliente);
			reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			reserva.setDataReserva(timestamp);
			
			reservaRepository.save(reserva);
			
			Reserva ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente);
			
			if (pagamento == 1) {
				BigDecimal transacao = BigDecimal.valueOf(datas.length)
						.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());
				extrato.setCliente(cliente);
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.subtract(transacao));
				extrato.setTransacao(transacao.negate());
				extratoRepository.save(extrato);
			} else
				extrato = null;

			for (int x = 0; x < datas.length; x++) {				
				ReservaItem reservaItem = new ReservaItem();
				Status s = new Status();
				
				s = pagamento == 1 ? statusRepository.findByDescricao("Paga") : statusRepository.findByDescricao("Solicitada");
				
				reservaItem.setReserva(ultimaReserva);
				reservaItem.setCardapio(cardapioRepository.findByData(formatar.parse(datas[x])));
				reservaItem.setStatus(s);
				reservaItem.setTipoRefeicao(tipoRefeicaoRepository.findByDescricao(tipos[x]));
				reservaItem.setExtrato(extrato);

				reservaItemRepository.save(reservaItem);
			}
			
			json.put("sucesso", new Boolean(true));
		} else
			json.put("erro", "erro");
		
		
		return json.toString();
    }
	
	public String verificar() throws JSONException {
		JSONObject json = new JSONObject();
		
		todasAsDatasDaProximaSemana = new ArrayList<Date>();
		datasBanco = new ArrayList<String>();
		
		if(!verificarDiscente())
			json.put("erro", "discente");
		else if(!verificarDataAtual())
			json.put("erro", "data");
		else if(!verificarContagens())
			json.put("erro", "contagem");
		else
			json.put("sucesso", new Boolean(true));
		return json.toString();
	}
	
	public Boolean verificarDiscente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		cliente = clienteRepository.findByIdentificacao(authentication.getName());
		categoria = clienteCategoriaRepository.findByCliente(cliente);
		Pattern pattern = Pattern.compile("(Discente).*");
		Matcher matcher = pattern.matcher(categoria.getCategoria().getDescricao());
		
		return matcher.matches();
	}
	
	public Boolean verificarDataAtual() {
		Calendar seg = Calendar.getInstance();
		Calendar ter = Calendar.getInstance();
		Calendar qua = Calendar.getInstance();
		Calendar qui = Calendar.getInstance();
		Calendar dataAtual = Calendar.getInstance();
		Calendar dataInicio = Calendar.getInstance();
		Calendar primeiroDiaUtil = Calendar.getInstance();
		
		int horaAtual = dataAtual.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = dataAtual.get(Calendar.MINUTE);
		
		datasDaSemana();
		
		primeiroDiaUtil.setTime(todasAsDatasDaProximaSemana.get(0));
		
		seg.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		ter.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		qua.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		qui.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		
		if (dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
				&& feriadoRepository.findByData(dataAtual.getTime()) == null) {
			dataInicio.setTime(seg.getTime());
		} else if (dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				&& feriadoRepository.findByData(seg.getTime()) != null) {
			dataInicio.setTime(ter.getTime());
		} else if (dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
				&& feriadoRepository.findByData(ter.getTime()) != null) {
			dataInicio.setTime(qua.getTime());
		} else if (dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				&& feriadoRepository.findByData(qua.getTime()) != null) {
			dataInicio.setTime(qui.getTime());
		} else {
			dataInicio.setTime(seg.getTime());
		}
		
		if (!(dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
		&& !(dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		&& (dataAtual.get(Calendar.DAY_OF_WEEK) >= dataInicio.get(Calendar.DAY_OF_WEEK))
		&& dataAtual.get(Calendar.DAY_OF_WEEK) <= penultimoDiaUtil.get(Calendar.DAY_OF_WEEK)
		&& todasAsDatasDaProximaSemana.size() >= 2) {
			if(feriadoRepository.findByData(dataAtual.getTime()) == null)
				return true;
			else if (!dataAtual.getTime().equals(dataInicio.getTime()))
				return true;
			else if (horaAtual >= 7 && minutoAtual >= 30)
				return true;
		}
		
		return false;
	}
	
	public Boolean verificarContagens() {
		int count = 0;
		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(qtdeRefeicoesRepository.findAll().size() -1).getSubsidiada();
		
		datasDaSemana();
		
		for (int i = 0; i < todasAsDatasDaProximaSemana.size(); i++) {
			Cardapio dataDoBanco = cardapioRepository.findByData(proximaSeg.getTime());
			if (dataDoBanco != null) {
				count = reservaItemRepository.qtdeDeReservasPorData(dataDoBanco.getData());
				if (count < qtdeSubs) {
					if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), dataDoBanco.getData()) == null)
						datasBanco.add(formatar.format(dataDoBanco.getData()));
				}
			}
			count = 0;
			proximaSeg.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(datasBanco.isEmpty())
			return false;
		else
			return true;
	}
	
	public void datasDaSemana() {
		proximaSeg = verificarData(Calendar.getInstance());
		penultimoDiaUtil = Calendar.getInstance();
		penultimoDiaUtil.setTime(proximaSeg.getTime());
		penultimoDiaUtil.add(Calendar.DATE, 4);
		todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(proximaSeg.getTime(), penultimoDiaUtil.getTime());
		penultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 2));
	}
	
	public static Calendar verificarData(Calendar data) {
		// Se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// Se for terça
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			data.add(Calendar.DATE, 6);
		}
		// Se for quarta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			data.add(Calendar.DATE, 5);
		}
		// Se for quinta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			data.add(Calendar.DATE, 4);
		}
		// Se for sexta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			data.add(Calendar.DATE, 3);
		}
		// Se for sabado
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			data.add(Calendar.DATE, 2);
		}
		// Se for domingo
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			data.add(Calendar.DATE, 1);
		}
		return data;
	}
}