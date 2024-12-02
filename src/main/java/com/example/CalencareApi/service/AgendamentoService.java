package com.example.CalencareApi.service;

import com.example.CalencareApi.api.google.calendar.GoogleCalendarService;
import com.example.CalencareApi.dto.agendamento.AgendamentoConsultaDto;
import com.example.CalencareApi.dto.agendamento.AgendamentoFinalizarDto;
import com.example.CalencareApi.dto.funcionario.FuncionarioConsultaDto;
import com.example.CalencareApi.entity.*;
import com.example.CalencareApi.interfaces.AgendamentoObserver;
import com.example.CalencareApi.mapper.AgendamentoMapper;
import com.example.CalencareApi.mapper.FuncionarioMapper;
import com.example.CalencareApi.repository.AgendamentoRepository;
import com.example.CalencareApi.repository.EmpresaRepository;
import com.example.CalencareApi.service.log.ServicoPrecoLogService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.CalencareApi.entity.Agendamento;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
public class AgendamentoService {

    List<AgendamentoObserver> observers;
    @Autowired private AgendamentoRepository agendamentoRepository;
    @Autowired private FuncionarioService funcionarioService;
    @Autowired private ClienteService clienteService;
    @Autowired private ServicoPrecoService servicoPrecoService;
    @Autowired private ServicoPorFuncionarioService servicoPorFuncionarioService;
    @Autowired private EmailService emailService;
    @Autowired private HorarioFuncionamentoService horarioFuncionamentoService;
    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private GoogleCalendarService googleCalendarService;
    @Autowired private ServicoPrecoLogService servicoPrecoLogService;

    public final String[] diasDaSemana = { "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado" };
    public final String[] horasDoDia = {
            "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00",
            "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
            "20:00", "21:00", "22:00", "23:00"
    };

    /*public AgendamentoConsultaDto cadastrar(
            AgendamentoCriacaoDto novoAgendamento,
            int funcionarioId,
            int clienteId,
            int servicoPrecoId) {
        Funcionario funcionario = funcionarioService.buscarEntidadePorId(funcionarioId);
        Cliente cliente = clienteService.buscarEntidadePorId(clienteId);
        ServicoPreco servicoPreco = servicoPrecoService.buscarEntidadePorId(servicoPrecoId);

        if (funcionario == null || cliente == null || servicoPreco == null) {
            throw new RuntimeException("Funcionário, cliente ou serviço não encontrado");
        }

        Agendamento agendamentoConvertido = AgendamentoMapper.toEntity(novoAgendamento);
        agendamentoConvertido.setCliente(cliente);
        agendamentoConvertido.setFuncionario(funcionario);
        agendamentoConvertido.setServicoPreco(servicoPreco);
        Agendamento agendamentoSave = this.agendamentoRepository.save(agendamentoConvertido);
        AgendamentoConsultaDto dto = AgendamentoMapper.toDto(agendamentoSave);

        notificarAdmins(agendamentoSave, funcionario.getEmpresa().getId());
        googleCalendarService.createEvent(agendamentoSave.getId());

        return dto;
    }*/


    public Integer getTotaisAgendamentosDoDia(LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        return agendamentoRepository.countTotaisAgendamentosDoDia(dataInicio, dataFim);
    }

    public Integer getTotaisAgendamentosDoDia(LocalDate data, Integer empresaId) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        return agendamentoRepository.countTotaisAgendamentosDoDia(dataInicio, dataFim, empresaId);
    }


    public Double getPotencialLucroDoDia(LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        return agendamentoRepository.calcularPotencialLucroDoDia(dataInicio, dataFim);
    }
    public Double getPotencialLucroDoDia(LocalDate data, Integer empresaId) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        return agendamentoRepository.calcularPotencialLucroDoDia(dataInicio, dataFim, empresaId);
    }


    public String getServicoMaisProcurado() {
        return agendamentoRepository.getServicoMaisProcurado();
    }
    public String getServicoMaisProcurado(Integer empresaId) {
        return agendamentoRepository.getServicoMaisProcurado(empresaId);
    }


    public List<AgendamentoConsultaDto> getProximosAgendamentos() {
        return AgendamentoMapper.toDto(agendamentoRepository.getProximosAgendamentos());
    }
    public List<AgendamentoConsultaDto> getProximosAgendamentos(Integer empresaId) {
        LocalDateTime dataAtual = LocalDate.now().atStartOfDay().plusHours(LocalTime.now().getHour());
        LocalDateTime dataFim = dataAtual.plusHours(1);
        return AgendamentoMapper.toDto(agendamentoRepository.getProximosAgendamentos(empresaId, dataAtual, dataFim));
    }

    public List<AgendamentoConsultaDto> getAgendamentosEmAndamento() {
        return AgendamentoMapper.toDto(agendamentoRepository.getAgendamentosEmAndamento());
    }
    public List<AgendamentoConsultaDto> getAgendamentosEmAndamento(Integer empresaId) {
        return AgendamentoMapper.toDto(agendamentoRepository.getAgendamentosEmAndamento(empresaId));
    }

    public List<AgendamentoObserver> getListaAdmins(Integer idEmpresa) {
        observers.addAll(funcionarioService.listarAdmin(idEmpresa));
        if (observers.isEmpty()) { return null; }
        return observers;
    }

    public void notificarAdmins(Agendamento agendamento, Integer idEmpresa) {
        getListaAdmins(idEmpresa);
        for (AgendamentoObserver observer : observers) { observer.notificar(emailService,agendamento); }
    }

    public Object[][] getAgendamentosDoDia(LocalDate data, Integer empresaId) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusHours(23).plusMinutes(59).plusSeconds(59);
        List<Agendamento> agendamentos = agendamentoRepository.getAgendamentosDoDia(dataInicio, dataFim, empresaId);
        List<Funcionario> funcionarios = servicoPorFuncionarioService.listarFuncionariosComServicosEntidade(empresaId);
        List<FuncionarioConsultaDto> funcionariosDto = FuncionarioMapper.toDto(funcionarios);
        List<LocalTime> horarios = horarioFuncionamentoService.retornarIntervalosAtendimento(empresaId, data);

        Object[][] matrizAgendamento = new Object[horarios.size()+1][funcionarios.size()+1];

        for (int i = 1; i < horarios.size()+1; i++) {
            for (int j = 1; j < funcionarios.size() + 1; j++) {
                matrizAgendamento[i][j] = null;
                matrizAgendamento[0][j] = funcionariosDto.get(j-1).getNome();
                matrizAgendamento[i][0] = horarios.get(i-1).toString();

                for (Agendamento agendamento : agendamentos) {
                    LocalTime hr = agendamento.getDtHora().toLocalTime();
                    Object[] infoAgendamento = {agendamento.getServicoPreco().getServico().getNome(), agendamento.getId()};
                    if (horarios.get(i - 1).equals(hr) &&
                            funcionariosDto.get(j - 1).getNome().equals(agendamento.getFuncionario().getNome())) {
                        matrizAgendamento[i][j] = infoAgendamento;
                        break;
                    }
                }
            }
        }
        return matrizAgendamento;
    }

    public List<Agendamento> getAgendamentosDaSemanaAtual() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioDaSemana = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate fimDaSemana = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        LocalDateTime startDateTime = inicioDaSemana.atStartOfDay();
        LocalDateTime endDateTime = fimDaSemana.atTime(LocalTime.MAX);

        return agendamentoRepository.findAllByDtHoraBetween(startDateTime, endDateTime);
    }


    public String[][] getMatrizAgendamentosDaSemanaAtual() {
        List<Agendamento> agendamentos = getAgendamentosDaSemanaAtual();
        String[][] matrizCalendario = new String[24][7];

        // Inicializa a matriz com strings vazias
        for (int hora = 0; hora < 24; hora++) {
            for (int dia = 0; dia < 7; dia++) {
                matrizCalendario[hora][dia] = "";
            }
        }

        // Popula a matriz com descrições de agendamentos
        for (Agendamento agendamento : agendamentos) {
            LocalDateTime dataHora = agendamento.getDtHora();
            LocalDate data = dataHora.toLocalDate();
            LocalDate hoje = LocalDate.now();
            LocalDate inicioDaSemana = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            LocalDate fimDaSemana = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

            if (!data.isBefore(inicioDaSemana) && !data.isAfter(fimDaSemana)) {
                int hora = dataHora.getHour();
                DayOfWeek diaDaSemana = dataHora.getDayOfWeek();
                int dia = diaDaSemana.getValue() % 7; // Para alinhar com nosso array de 0 (Domingo) a 6 (Sábado)
                matrizCalendario[hora][dia] = agendamento.getDescription();
            }
        }

        return matrizCalendario;
    }

    public String[][] exibirCalendario() {
        String[][] matrizAgendamentos = getMatrizAgendamentosDaSemanaAtual();
        String[][] matrizCompleta = new String[25][8]; // 24 horas + 1 para cabeçalho, 7 dias + 1 para cabeçalho

        // Preenche a primeira linha com os dias da semana
        matrizCompleta[0][0] = "Hora";
        for (int j = 0; j < 7; j++) {
            matrizCompleta[0][j + 1] = diasDaSemana[j];
        }

        // Preenche a primeira coluna com as horas do dia
        for (int i = 0; i < 24; i++) {
            matrizCompleta[i + 1][0] = horasDoDia[i];
        }

        // Copia os agendamentos para a matriz completa
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 7; j++) {
                matrizCompleta[i + 1][j + 1] = matrizAgendamentos[i][j];
            }
        }

        return matrizCompleta;
    }

    public List<AgendamentoConsultaDto> listarAgendamentosPorPeriodoEmpresa(Integer empresaId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new ResponseStatusException(400, "Data de início não pode ser posterior à data de fim", null);
        }

        if (dataInicio == null || dataFim == null || empresaId == null) {
            throw new ResponseStatusException(400, "Um ou mais Parâmetros nulos", null);
        }
        List<AgendamentoConsultaDto> agendamentos = AgendamentoMapper.toDto(
                agendamentoRepository.findByFuncionarioEmpresaIdAndDtHoraBetween(empresaId,dataInicio, dataFim));
        return agendamentos;
    }

    public List<AgendamentoConsultaDto> getAgendamentosPendentesEmpresa(Integer empresaId) {
        List<Agendamento> agendamentos = agendamentoRepository.getAgendamentosPendentesEmpresa(empresaId);
        return AgendamentoMapper.toDto(agendamentos);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void validarAgendamentosPendentes() {
        LocalDate data = LocalDate.now();
        LocalTime agora = LocalTime.now();
        LocalDateTime dataTransformadaInicio = data.atStartOfDay().plusHours(agora.getHour()).minusHours(3);
        LocalDateTime dataTransformadaFim = dataTransformadaInicio.plusHours(3);

        List<Agendamento> agendamentos = agendamentoRepository.findAllByDtHoraBetween(
                dataTransformadaInicio, dataTransformadaFim);

        if (agendamentos.isEmpty()) {
            //System.out.println("Sem agendamentos");
            return;
        }

        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDtHora().toLocalTime().plusMinutes(
                    agendamento.getDuracao()).isBefore(agora)) {
                agendamento.setBitStatus(2);
                agendamentoRepository.save(agendamento);
                //System.out.println("Executado");
            }
        }
    }

    public List<Agendamento> getAgendamentosDoMomento (LocalDate data) {
        LocalTime agora = LocalTime.now();
        LocalDateTime dataTransformadaInicio = data.atStartOfDay().plusHours(agora.getHour());
        LocalDateTime dataTransformadaFim = dataTransformadaInicio.plusMinutes(60);

        List<Agendamento> agendamentos = agendamentoRepository.findAllByDtHoraBetween(
                dataTransformadaInicio, dataTransformadaFim);

        if (agendamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204), "Não há agendamentos para o momento");
        }

        return agendamentos;
    }


    public Agendamento getAgendamentoPorIdEmpresa(Integer empresaId, Integer id) {
        Agendamento agendamento = agendamentoRepository.getAgendamentoPorIdEmpresa(empresaId, id);
        if (agendamento == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Agendamento não encontrado");
        }
        return agendamento;
    }

    public Agendamento finalizar(Integer id, AgendamentoFinalizarDto agendamentoFinalizarDto){
        Agendamento agendamento = this.agendamentoRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado."));
        agendamento.setMetodoPagamento(agendamentoFinalizarDto.getMetodoPagamento());
        agendamento.setBitStatus(5);
        Agendamento agendamentoSalvo = this.agendamentoRepository.save(agendamento);
        return agendamentoSalvo;
    }

    @Transactional
    public void atualizarBitStatus(Integer agendamentoId, Integer empresaId) {
        agendamentoRepository.updateBitStatus(agendamentoId, empresaId);
    }

    public Integer getNumeroFuncionariosDia(Integer empresaId, LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusHours(23).plusMinutes(59).plusSeconds(59);
        return agendamentoRepository.countDistinctFuncionarioByFuncionarioIdAndDtHoraBetween(empresaId, dataInicio, dataFim);
    }

    public List<Agendamento> getHistoricoDoMes(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        return agendamentoRepository.getHistoricoAgendamentosMes(empresaId, dataInicioTransformada, dataFimTransformada);
    }

    public byte[] getCsv(Integer empresaId, Month mes, Year ano) throws Exception {
        List<Agendamento> agendamentos = getHistoricoDoMes(empresaId, mes, ano);
        List<AgendamentoConsultaDto> agendamentosDto = AgendamentoMapper.toDto(agendamentos);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);


        StatefulBeanToCsv<AgendamentoConsultaDto> sbc = new StatefulBeanToCsvBuilder<AgendamentoConsultaDto>(writer)
                .withSeparator(',')
                .build();


        sbc.write(agendamentosDto);

        writer.flush();
        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    // FINANÇAS //////////////////////////////////////////////////////////////////////////////

    public Double getReceitasMes(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        List<AgendamentoConsultaDto> lista = listarAgendamentosPorPeriodoEmpresa(
                empresaId, dataInicioTransformada, dataFimTransformada);
        Double receitas = 0.0;
        for (AgendamentoConsultaDto agendamento : lista) {
            receitas += agendamento.getValorServico();
        }
        return receitas;
    }

    public Double getComissaoMes(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        List<AgendamentoConsultaDto> lista = listarAgendamentosPorPeriodoEmpresa(
                empresaId, dataInicioTransformada, dataFimTransformada);
        Double comissao = 0.0;
        for (AgendamentoConsultaDto agendamento : lista) {
            comissao +=   agendamento.getValorServico() * agendamento.getComissao();
        }
        return comissao;
    }

    public Double getLucroMes(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        List<AgendamentoConsultaDto> lista = listarAgendamentosPorPeriodoEmpresa(
                empresaId, dataInicioTransformada, dataFimTransformada);
        Double lucro = 0.0;
        for (AgendamentoConsultaDto agendamento : lista) { lucro += agendamento.getValorServico(); }
        if (lucro == 0.0) { return 0.0; }
        return lucro - getComissaoMes(empresaId, mes, ano);
    }

    public List<Map<String, Object>> getReceitaDiaria(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        return agendamentoRepository.getReceitaDiaria(empresaId, dataInicioTransformada, dataFimTransformada);
    }

    /*public void validarValoresAgendamento(List<AgendamentoConsultaDto> dto) {
        for (int i = 0 ; i < dto.size(); i++) {
            AgendamentoConsultaDto agendamento = dto.get(i);
            ServicoPrecoLogConsultaDto servicoPrecoLog = servicoPrecoLogService
                    .buscarUltimaAlteracao(agendamento.getServicoPrecoId(), LocalDateTime.now());
            if (servicoPrecoLog != null) {
                dto.get(i).setValorServico(servicoPrecoLog.getPrecoAnterior());
                dto.get(i).setComissao(servicoPrecoLog.getComissaoAnterior());
                dto.get(i).setDuracaoServico(servicoPrecoLog.getDuracaoAnterior());
            }
        }
    }*/

    // Validações



    /*public List<AgendamentoConsultaDto> retornaAgendamentosDia (String data) {
        List<Agendamento> agendamentos = agendamentoRepository.findByDtHoraContaining(data);

        List<AgendamentoConsultaDto> dto = AgendamentoMapper.toDto(agendamentos);
        return dto;
    }*/

    /*public List<AgendamentoConsultaDto> retornaAgendamentosDiaServico (String data, int idServico) {
        List<Agendamento> agendamentos = agendamentoRepository.findByDtHoraContainingAndServicoId(data, idServico);

        List<AgendamentoConsultaDto> dto = AgendamentoMapper.toDto(agendamentos);
        return dto;
    }*/



}
