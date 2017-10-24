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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.Status;
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class ComprarController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

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

	@GetMapping("/comprar")
	public String comprar(Model model)
			throws JsonGenerationException, JsonMappingException, IOException, ParseException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		BigDecimal saldo = new BigDecimal(0.00);
		List<Integer> contadores = new ArrayList<Integer>();
		List<Cardapio> cardapio = new ArrayList<Cardapio>();
		ClienteCategoria clienteCategoria = new ClienteCategoria();

		if (!(authentication instanceof AnonymousAuthenticationToken)) { // Verifica se a pessoa está logada

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao); // Pega todos os dados da pessoa logada
			clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			int count = 0; // Define quantos tipo valores estarão disponíveis na página. Se for 1, apenas
							// subsidiadas. Ser for 2, apenas de custo. Se for 3, de custo e subsidiada

			List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			if (!todosOsExtratos.isEmpty()) { // Pega o último saldo do cliente
				saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
			}

			// Verifica se a pessoa possui créditos
			if (!saldo.equals(new BigDecimal(0))) {

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
				int countNãoSubsidiadaSegunda = 0;
				int countNãoSubsidiadaTerca = 0;
				int countNãoSubsidiadaQuarta = 0;
				int countNãoSubsidiadaQuinta = 0;
				int countNãoSubsidiadaSexta = 0;

				Calendar dataAtual = Calendar.getInstance();
				Calendar dataHoje = Calendar.getInstance();

				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

				dataAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// Define o dia segunda-feira desta semana

				for (int i = 0; i < 5; i++) {

					Cardapio c = new Cardapio();
					List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());

					for (Object[] linhaDoBanco : dataDoBanco) {

						Calendar cal = Calendar.getInstance();
						cal.setTime((Date) linhaDoBanco[1]);

						if (dataHoje.get(Calendar.DAY_OF_WEEK) < cal.get(Calendar.DAY_OF_WEEK)) {

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSegunda = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaTerca = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuarta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaQuinta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData((Date) linhaDoBanco[1]);
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData((Date) linhaDoBanco[1]);
								countNãoSubsidiadaSexta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData((Date) linhaDoBanco[1]);
							}

							// Verifica se o count disponível é menor que 360 refeições
							if (countDisponivelSegunda < 360 || countDisponivelTerca < 360
									|| countDisponivelQuarta < 360 || countDisponivelQuinta < 360
									|| countDisponivelSexta < 360 || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
								Date dataVar = formatoDesejado.parse(dataFormatada);

								c.setId((Long) linhaDoBanco[0]);
								c.setData(dataVar);

								if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
										(Date) linhaDoBanco[1]) == null) {
									cardapio.add(c);
									
									count = 1;
								}								
							}

							if (countNãoSubsidiadaSegunda < 140 || countNãoSubsidiadaTerca < 140
									|| countNãoSubsidiadaQuarta < 140 || countNãoSubsidiadaQuinta < 140
									|| countNãoSubsidiadaSexta < 140) {

								if (count == 0) {

									String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
									Date dataVar = formatoDesejado.parse(dataFormatada);

									c.setId((Long) linhaDoBanco[0]);
									c.setData(dataVar);

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											(Date) linhaDoBanco[1]) == null) {
										
										int caiu = 0;
										
										for(Cardapio daLista : cardapio) {
											
											if(daLista.getData().equals(dataVar)) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											cardapio.add(c);
										}										
										
										count = 2;						
									}					
									
								} else {
									
									String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
									Date dataVar = formatoDesejado.parse(dataFormatada);

									c.setId((Long) linhaDoBanco[0]);
									c.setData(dataVar);

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											(Date) linhaDoBanco[1]) == null) {
										
										int caiu = 0;
										
										for(Cardapio daLista : cardapio) {
											
											if(daLista.getData().equals(dataVar)) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											cardapio.add(c);
										}	
										
										count = 3;
									}					
								}
							}
							
							contadores.add(count);
						}				
					}

					dataAtual.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		}

		model.addAttribute("contadores", contadores);
		model.addAttribute("saldo", saldo);
		model.addAttribute("todasAsDatas", cardapio);
		model.addAttribute("comprar", new Cardapio());
		model.addAttribute("valorRefeicaoS", clienteCategoria.getCategoria().getValorComSubsidio());
		model.addAttribute("valorRefeicaoC", clienteCategoria.getCategoria().getValorSemSubsidio());

		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(cardapio);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "comprar";
	}

	@PostMapping("/comprar/salvar")
	public String salvarComprar(@RequestParam("data") Integer[] idsCardapios,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao,
			@RequestParam("tipoValor") Integer[] idsTipoValor) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao); // Pega o id da pessoa logada

			BigDecimal saldo = new BigDecimal(0.00);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			List<Extrato> todosOsExtratos = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			List<Integer> tiposValoresCusto = new ArrayList<Integer>();
			List<Integer> tiposRefeicoesCusto = new ArrayList<Integer>();
			List<Integer> datasSelecionadasCusto = new ArrayList<Integer>();
			List<Integer> tiposValoresSubsidiada = new ArrayList<Integer>();
			List<Integer> tiposRefeicoesSubsidiada = new ArrayList<Integer>();
			List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();
			List<Integer> datasSelecionadasSubsidiada = new ArrayList<Integer>();

			if (!todosOsExtratos.isEmpty()) {
				saldo = todosOsExtratos.get(todosOsExtratos.size() - 1).getSaldo();
			}

			for (int z = 0; z < idsTipoRefeicao.length; z++) {

				if (idsTipoRefeicao[z] != null) {
					tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
				}
			}

			for (int z = 0; z < idsTipoValor.length; z++) {

				if (idsTipoValor[z] != null) {

					if (idsTipoValor[z] % 2 == 0) {
						tiposValoresSubsidiada.add(idsTipoValor[z]);
						datasSelecionadasSubsidiada.add(idsCardapios[z]);
						tiposRefeicoesSubsidiada.add(tiposRefeicoesSelecionados.get(z));
					} else {
						tiposValoresCusto.add(idsTipoValor[z]);
						datasSelecionadasCusto.add(idsCardapios[z]);
						tiposRefeicoesCusto.add(tiposRefeicoesSelecionados.get(z));
					}
				}
			}

			if (!tiposValoresSubsidiada.isEmpty()) {

				Extrato extrato = new Extrato();
				Reserva reserva = new Reserva();

				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); // Definir como subsidiada caso
																							// seja umas das 360
																							// primeiras refeições

				reservaRepository.save(reserva);

				extrato.setCliente(cliente);
				extrato.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(datasSelecionadasSubsidiada.size())
						.multiply(clienteCategoria.getCategoria().getValorComSubsidio());

				extrato.setTransacao(transacao.negate());
				extrato.setSaldo(saldo.subtract(transacao));

				extratoRepository.save(extrato);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);

				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {

					ReservaItem reservaItem = new ReservaItem();
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					Status s = new Status();
					TipoRefeicao t = null;

					if (tiposRefeicoesSubsidiada.get(x) % 2 == 0) {
						t = tipoRefeicaoRepository.findByDescricao("Vegetariano");
					} else {
						t = tipoRefeicaoRepository.findByDescricao("Tradicional");
					}

					s = statusRepository.findByDescricao("Pago");

					r.setId(id);
					c.setId(datasSelecionadasSubsidiada.get(x).longValue());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(s);
					reservaItem.setTipoRefeicao(t);
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			}

			if (!tiposValoresCusto.isEmpty()) {

				Extrato extrato = new Extrato();
				Reserva reserva = new Reserva();

				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

				reservaRepository.save(reserva);

				extrato.setCliente(cliente);
				extrato.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(datasSelecionadasCusto.size())
						.multiply(clienteCategoria.getCategoria().getValorSemSubsidio());

				extrato.setTransacao(transacao.negate());
				extrato.setSaldo(saldo.subtract(transacao));

				extratoRepository.save(extrato);

				List<Reserva> todasAsReservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);

				Long id = todasAsReservasDoCliente.get(todasAsReservasDoCliente.size() - 1).getId();

				for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {

					ReservaItem reservaItem = new ReservaItem();
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					Status s = new Status();
					TipoRefeicao t = null;

					if (tiposRefeicoesCusto.get(x) % 2 == 0) {
						t = tipoRefeicaoRepository.findByDescricao("Vegetariano");
					} else {
						t = tipoRefeicaoRepository.findByDescricao("Tradicional");
					}

					s = statusRepository.findByDescricao("Pago");

					r.setId(id);
					c.setId(datasSelecionadasCusto.get(x).longValue());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(s);
					reservaItem.setTipoRefeicao(t);
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			}
		}

		return "redirect:/historico";
	}
}