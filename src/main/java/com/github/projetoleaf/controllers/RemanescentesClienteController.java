package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.Status;
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.beans.TipoValor;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.CategoriaRepository;
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
	private CategoriaRepository categoriaRepository;

	@Autowired
	private TipoValorRepository tipoValorRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@GetMapping("/remanescente")
	public String remanescente(Model model)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		BigDecimal saldo = new BigDecimal(0.00);
		List<Cardapio> cardapio = new ArrayList<Cardapio>();
		ClienteCategoria clienteCategoria = new ClienteCategoria();

		if (!(authentication instanceof AnonymousAuthenticationToken)) { // Verifica se a pessoa está logada

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao); // Pega todos os dados da pessoa logada

			Categoria dGraduacao = categoriaRepository.findByDescricao("Discentes de Graduação");
			Categoria dPosGraduacao = categoriaRepository.findByDescricao("Discentes de Pós Graduação");
			clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			List<ReservaItem> todasAsReservasDoBD = reservaItemRepository.findAll();
			List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());
			List<ReservaItem> todasAsReservasDoCliente = reservaItemRepository
					.todasAsReservasDoCliente(cliente.getId());

			if (!todosOsExtratos.isEmpty()) { // Pega o último saldo do cliente
				saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
			}

			// Verifica se a pessoa é discente e se possui créditos
			if ((!(clienteCategoria.getCategoria() != dGraduacao)
					|| !(clienteCategoria.getCategoria() != dPosGraduacao)) && !saldo.equals(new BigDecimal(0))) {

				int countSegunda = 0;
				int countTerca = 0;
				int countQuarta = 0;
				int countQuinta = 0;
				int countSexta = 0;
				int countDisponivelSegunda = 0;
				int countDisponivelTerca = 0;
				int countDisponivelQuarta = 0;
				int countDisponivelQuinta = 0;
				int countDisponivelSexta = 0;
				int countExpiradoSegunda = 0;
				int countExpiradoTerca = 0;
				int countExpiradoQuarta = 0;
				int countExpiradoQuinta = 0;
				int countExpiradoSexta = 0;

				Calendar dataHoje = Calendar.getInstance();
				Calendar dataAtual = Calendar.getInstance();
				Calendar ultimoDiaUtil = Calendar.getInstance();

				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat formatoHora = new SimpleDateFormat("HH");

				dataAtual = verificarData(dataAtual); // Pega a segunda-feira da semana que vem

				ultimoDiaUtil.setTime(dataAtual.getTime());
				ultimoDiaUtil.add(Calendar.DATE, 5); // Com base no valor da dataAtual, define a sexta-feira da próxima
														// semana

				List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(dataAtual.getTime(),
						ultimoDiaUtil.getTime());
				ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1)); // Define
																												// o
																												// último
																												// dia
																												// util,
																												// conforme
																												// o
																												// range
																												// de
																												// datas
																												// do BD

				Integer horaDeHoje = Integer.parseInt(formatoHora.format(dataHoje.getTime())); // Pega a hora atual

				// Consistências dos finais de semanas, feriados, hora de acesso e se hoje é o
				// último dia útil. Não apague \/
				// if(horaDeHoje >= 7 && horaDeHoje <= 10 && dataHoje.get(Calendar.DAY_OF_WEEK)
				// == ultimoDiaUtil.get(Calendar.DAY_OF_WEEK) &&
				// !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) &&
				// !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) &&
				// feriadoRepository.findByData(dataHoje.getTime()) == null) {
				if (horaDeHoje >= 7) {

					for (int i = 0; i < 5; i++) {

						Cardapio c = new Cardapio();
						List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());

						for (Object[] linhaDoBanco : dataDoBanco) {

							Calendar cal = Calendar.getInstance();
							cal.setTime((Date) linhaDoBanco[1]);

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
							}

							// Qtde de reservas já feitas no último dia útil, por data da semana que vem
							for (int x = 0; x < todasAsReservasDoBD.size(); x++) {

								if (todasAsReservasDoBD.get(x).getReserva().getDataReserva()
										.equals(dataHoje.getTime())) {

									if (todasAsReservasDoBD.get(x).getCardapio().getData().equals(linhaDoBanco[1])) {

										if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
											countSegunda++;
										}

										if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
											countTerca++;
										}

										if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
											countQuarta++;
										}

										if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
											countQuinta++;
										}

										if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
											countSexta++;
										}
									}
								}
							}

							// Verifica se o count disponível é menor que 360 refeições e se o count das
							// reservas do último dia útil é maior que 0
							if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
									|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
									|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
								Date dataVar = formatoDesejado.parse(dataFormatada);

								c.setId((Long) linhaDoBanco[0]);
								c.setData(dataVar);

								// Verifica se a qtde de reservas do último dia útil é menor ou igual a qtde de
								// reservas expiradas (que agora estão disponíveis)
								if (countSegunda <= countExpiradoSegunda || countTerca <= countExpiradoTerca
										|| countQuarta <= countExpiradoQuarta || countQuinta <= countExpiradoQuinta
										|| countSexta <= countExpiradoSexta) {

									if (!(todasAsReservasDoCliente.isEmpty())) {

										if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
												(Date) linhaDoBanco[1]) == null
												&& formatoDesejado.format(c.getData())
														.equals(formatoDesejado.format(dataAtual.getTime()))) {
											cardapio.add(c);
										}
									} else {
										if (formatoDesejado.format(c.getData())
												.equals(formatoDesejado.format(dataAtual.getTime()))) {
											cardapio.add(c);
										}
									}
								}
							}
						}

						dataAtual.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
			}
		}

		model.addAttribute("remanescentes", new Cardapio());
		model.addAttribute("todasAsDatas", cardapio);
		model.addAttribute("todosOsTipos", tipoRefeicaoRepository.findAll());
		model.addAttribute("saldo", saldo);
		model.addAttribute("valorRefeicao", clienteCategoria.getCategoria().getValorComSubsidio());

		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(cardapio);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "remanescente";
	}

	@PostMapping("/remanescente/salvar")
	public String salvarRemanescente(@RequestParam("data") String[] idsCardapios,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao); // Pega o id da pessoa logada

			BigDecimal saldo = new BigDecimal(0.00);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

			TipoValor tipoValor = tipoValorRepository.findByDescricao("Subsidiada");
			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();

			if (!todosOsExtratos.isEmpty()) {
				saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
			}

			Reserva reserva = new Reserva();
			Extrato extrato = new Extrato();

			reserva.setCliente(cliente);
			reserva.setTipoValor(tipoValor); // Definir como subsidiada caso seja umas das 360 primeiras refeições
			reserva.setDataReserva(timestamp);

			reservaRepository.save(reserva);

			List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);

			Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

			for (int z = 0; z < idsTipoRefeicao.length; z++) {

				if (idsTipoRefeicao[z] != null) {
					tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
				}
			}

			extrato.setCliente(cliente);
			extrato.setDataTransacao(timestamp);

			BigDecimal transacao = BigDecimal.valueOf(idsCardapios.length)
					.multiply(clienteCategoria.getCategoria().getValorComSubsidio());

			extrato.setSaldo(saldo.subtract(transacao));
			extrato.setTransacao(transacao.negate());

			extratoRepository.save(extrato);

			for (int x = 0; x <= idsCardapios.length - 1; x++) {

				ReservaItem reservaItem = new ReservaItem();
				Reserva r = new Reserva();
				Cardapio c = new Cardapio();
				Status s = new Status();
				TipoRefeicao t = null;

				if (tiposRefeicoesSelecionados.get(x) % 2 == 0) {
					t = tipoRefeicaoRepository.findByDescricao("Vegetariano");
				} else {
					t = tipoRefeicaoRepository.findByDescricao("Tradicional");
				}

				s = statusRepository.findByDescricao("Pago");

				r.setId(id);
				c.setId(Long.parseLong((idsCardapios[x])));

				reservaItem.setReserva(r);
				reservaItem.setCardapio(c);
				reservaItem.setStatus(s);
				reservaItem.setTipoRefeicao(t);
				reservaItem.setExtrato(extrato);

				reservaItemRepository.save(reservaItem);
			}
		}

		return "redirect:/historico";
	}

	public static Calendar verificarData(Calendar data) {
		// se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// se for terça
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