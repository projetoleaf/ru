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
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@Controller
public class RemanescentesClienteController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private CardapioRepository cardapioRepository;

	@Autowired
	private TipoValorRepository tipoValorRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	private Cliente cliente;
	private Calendar dataAtual;
	private Calendar proximaSeg;
	private Extrato ultimoExtrato;
	private Calendar ultimoDiaUtil;
	private List<String> datasBanco;
	private ClienteCategoria categoria;
	private List<Date> todasAsDatasDaProximaSemana;
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");

	@PostMapping("/verificarremanescente")
	public @ResponseBody String verificarRemanescente() throws JSONException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		return verificar();
	}

	public String verificar() throws JSONException {
		JSONObject json = new JSONObject();

		datasBanco = new ArrayList<String>();
		todasAsDatasDaProximaSemana = new ArrayList<Date>();

		if (!verificarDiscente())
			json.put("erro", "discente");
		else if (!verificarSaldo())
			json.put("erro", "saldo");
		else if (!verificarDataAtual())
			json.put("erro", "data");
		else if (!verificarContagens())
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

	public Boolean verificarSaldo() {
		ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);

		if (ultimoExtrato == null
				|| ultimoExtrato.getSaldo().compareTo(categoria.getCategoria().getValorComSubsidio()) < 0)
			return false;
		else
			return true;
	}

	public Boolean verificarDataAtual() {
		dataAtual = Calendar.getInstance();

		int horaAtual = dataAtual.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = dataAtual.get(Calendar.MINUTE);

		datasDaSemana();

		if (!(dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataAtual.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& dataAtual.get(Calendar.DAY_OF_WEEK) == ultimoDiaUtil.get(Calendar.DAY_OF_WEEK)) {
			if (feriadoRepository.findByData(dataAtual.getTime()) == null)
				return true;
			else if (horaAtual >= 7 && minutoAtual >= 30 && horaAtual <= 10)
				return true;
		}

		return false;
	}

	public void datasDaSemana() {
		proximaSeg = verificarData(Calendar.getInstance());
		ultimoDiaUtil = Calendar.getInstance();
		ultimoDiaUtil.setTime(proximaSeg.getTime());
		ultimoDiaUtil.add(Calendar.DATE, 4);
		todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(proximaSeg.getTime(),
				ultimoDiaUtil.getTime());
		ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1));
	}

	public Boolean verificarContagens() {
		int countExpirado = 0;
		int countRemanescentes = 0;

		List<ReservaItem> reservasDoBD = reservaItemRepository.reservasDoUltimoDiaUtil(dataAtual.getTime());

		datasDaSemana();

		for (int i = 0; i < 5; i++) {
			Cardapio dataDoBanco = cardapioRepository.findByData(proximaSeg.getTime());
			if (dataDoBanco != null) {
				countExpirado = reservaItemRepository.qtdeDeReservasExpiradasPorData(dataDoBanco.getData());

				// Qtde de reservas jÃ¡ feitas no Ãºltimo dia Ãºtil, por data da semana que vem
				for (ReservaItem reserva : reservasDoBD) {
					if (reserva.getCardapio().getData().equals(dataDoBanco.getData())) {
						countRemanescentes++;
					}
				}

				if (countRemanescentes <= countExpirado) { // Verifica se a qtde de remanescentes Ã© menor ou igual a
															// qtde de expirados
					if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), dataDoBanco.getData()) == null)
						datasBanco.add(formatar.format(dataDoBanco.getData()));
				}
			}
			countExpirado = 0;
			countRemanescentes = 0;
			proximaSeg.add(Calendar.DAY_OF_MONTH, 1);
		}

		if (datasBanco.isEmpty())
			return false;
		else
			return true;
	}

	@RequestMapping("/remanescente")
	public String carregarRemanescente(Model model) throws JSONException {
		JSONObject json = new JSONObject(verificar());
		Boolean sucesso = json.getBoolean("sucesso");

		if (sucesso) {
			model.addAttribute("datas", datasBanco);
			model.addAttribute("tipoRefeicoes", tipoRefeicaoRepository.findAll());
			return "remanescente";
		}

		return "boasvindas";
	}

	@PostMapping("/verificaremanescente")
	public @ResponseBody String verificarRemanescente(@RequestParam(value = "datas[]", required = false) String[] datas,
			@RequestParam(value = "tipos[]", required = false) String[] tipos) throws JSONException {
		JSONObject json = new JSONObject();
		
		int count = 0;
		int countRefeicao = 0;
		
		if(datas != null)
			count = datas.length;
		
		if(tipos != null) {
			for (String tR : tipos) {
				if(tR.length() != 0) {
					countRefeicao++;
				}					
			}
		}
		
		if (datas == null) {
			json.put("erro", "data");
		} else if (tipos == null || countRefeicao != count) {
			json.put("erro", "tipos");
		} else if (categoria.getCategoria().getValorComSubsidio().multiply(new BigDecimal(datas.length))
				.compareTo(ultimoExtrato.getSaldo()) > 0) {
			json.put("erro", "creditos");
		} else {
			json.put("sucesso", new Boolean(true));
		}		

		return json.toString();
	}

	@PostMapping("/efetuarremanescente")
	public @ResponseBody String efetuarRemanescente(@RequestParam(value = "datas[]") String[] datas,
			@RequestParam(value = "tipos[]") String[] tipos) throws JSONException, ParseException {
		JSONObject json = new JSONObject();

		datasBanco = new ArrayList<String>();
		todasAsDatasDaProximaSemana = new ArrayList<Date>();

		Boolean teste = true;
		if (verificarContagens()) {
			for (String data : datas) {
				if (!datasBanco.contains(data))
					teste = false;
			}
		} else
			teste = false;

		if (teste) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Reserva reserva = new Reserva();
			reserva.setCliente(cliente);
			reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
			reserva.setDataReserva(timestamp);
			reservaRepository.save(reserva);

			Reserva ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente);

			Extrato extrato = new Extrato();

			BigDecimal transacao = BigDecimal.valueOf(datas.length)
					.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());
			extrato.setCliente(cliente);
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(ultimoExtrato.getSaldo().subtract(transacao));
			extrato.setTransacao(transacao.negate());
			extratoRepository.save(extrato);

			for (int x = 0; x < datas.length; x++) {
				ReservaItem reservaItem = new ReservaItem();
				reservaItem.setReserva(ultimaReserva);
				reservaItem.setCardapio(cardapioRepository.findByData(formatar.parse(datas[x])));
				reservaItem.setStatus(statusRepository.findByDescricao("Paga"));
				reservaItem.setTipoRefeicao(tipoRefeicaoRepository.findByDescricao(tipos[x]));
				reservaItem.setExtrato(extrato);
				reservaItemRepository.save(reservaItem);
			}

			json.put("sucesso", new Boolean(true));
		} else
			json.put("erro", "erro");

		return json.toString();
	}

	public static Calendar verificarData(Calendar data) {
		// se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// se for terÃ§a
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			data.add(Calendar.DATE, 6);
		}
		// se for quarta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			data.add(Calendar.DATE, 5);
		}
		// se for quinta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			data.add(Calendar.DATE, 4);
		}
		// se for sexta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			data.add(Calendar.DATE, 3);
		}
		// se for sabado
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			data.add(Calendar.DATE, 2);
		}
		// se for domingo
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			data.add(Calendar.DATE, 1);
		}
		return data;
	}
}