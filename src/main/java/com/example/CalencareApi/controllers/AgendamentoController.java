package com.example.CalencareApi.controllers;

import com.example.CalencareApi.api.google.calendar.GoogleCalendarService;
import com.example.CalencareApi.dto.agendamento.AgendamentoAtualizarDto;
import com.example.CalencareApi.dto.agendamento.AgendamentoConsultaDto;
import com.example.CalencareApi.dto.agendamento.AgendamentoCriacaoDto;
import com.example.CalencareApi.dto.agendamento.AgendamentoFinalizarDto;
import com.example.CalencareApi.entity.*;
import com.example.CalencareApi.interfaces.AgendamentoObserver;
import com.example.CalencareApi.mapper.AgendamentoMapper;
import com.example.CalencareApi.repository.*;
import com.example.CalencareApi.service.AgendamentoService;
import com.example.CalencareApi.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    List<AgendamentoObserver> observers;
    private final AgendamentoRepository repository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoPrecoRepository servicoPrecoRepository;
    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;


    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private GoogleCalendarController googleCalendarController;

    public AgendamentoController(AgendamentoRepository repository,
                                 FuncionarioRepository funcionarioRepository,
                                 ClienteRepository clienteRepository,
                                 ServicoPrecoRepository servicoPrecoRepository,
                                 HorarioFuncionamentoRepository horarioFuncionamentoRepository) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
        this.servicoPrecoRepository = servicoPrecoRepository;
        this.horarioFuncionamentoRepository = horarioFuncionamentoRepository;
        this.observers = new ArrayList<>();
    }

    public List<AgendamentoObserver> getListaAdmins(Integer idEmpresa) {
        observers.addAll(funcionarioRepository.findAllAdmins(idEmpresa));

        if (observers.isEmpty()) {
            return null;
        }

        return observers;
    }

    public void notificarAdmins(Agendamento agendamento, Integer idEmpresa) {
        getListaAdmins(idEmpresa);

        for (AgendamentoObserver observer : observers) {
            observer.notificar(emailService, agendamento);
        }
    }

    @PostMapping("/{funcionarioId}/{clienteId}/{servicoPrecoId}")
    public ResponseEntity<AgendamentoConsultaDto> cadastrar(
            @RequestBody AgendamentoCriacaoDto novoAgendamento,
            @PathVariable int funcionarioId,
            @PathVariable int clienteId,
            @PathVariable int servicoPrecoId) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioId);
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        Optional<ServicoPreco> servicoPreco = servicoPrecoRepository.findById(servicoPrecoId);
        //List<HorarioFuncionamento> horarioFuncionamento = horarioFuncionamentoRepository.findAll();

        if (funcionario.isEmpty() || cliente.isEmpty() || servicoPreco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Agendamento agendamentoConvertido = AgendamentoMapper.toEntity(novoAgendamento);
        agendamentoConvertido.setCliente(cliente.get());
        agendamentoConvertido.setFuncionario(funcionario.get());
        agendamentoConvertido.setServicoPreco(servicoPreco.get());
        agendamentoConvertido.setPreco(servicoPreco.get().getPreco());
        agendamentoConvertido.setComissao(servicoPreco.get().getComissao());
        agendamentoConvertido.setDuracao(servicoPreco.get().getDuracao());
        Agendamento agendamentoSave = this.repository.save(agendamentoConvertido);
        AgendamentoConsultaDto dto = AgendamentoMapper.toDto(agendamentoSave);
        notificarAdmins(agendamentoSave, funcionario.get().getEmpresa().getId());
        funcionario.get().notificar(emailService, agendamentoSave);
        if (cliente.get().getEmail() != null) {
            cliente.get().notificar(emailService, agendamentoSave);
        }
        //  googleCalendarService.createEvent(agendamentoSave.getId());

        /*if (funcionario.get().getEmail().contains("@gmail.com)")) {
            try {
                googleCalendarService.createEvent(
                        agendamentoSave.getSummary(),
                        agendamentoSave.getDescription(),
                        agendamentoSave.getStartDate(),
                        agendamentoSave.getEndDate(),
                        agendamentoSave.getTimeZoneId(),
                        agendamentoSave.getCalendarId());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
}*/
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoConsultaDto>> exibir() {
        List<Agendamento> agendamentos = repository.findAll();
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<AgendamentoConsultaDto> dto = AgendamentoMapper.toDto(agendamentos);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoConsultaDto> exibirPorId(@PathVariable int id) {
        Optional<Agendamento> agendamento = repository.findById(id);

        if (agendamento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AgendamentoConsultaDto dto = AgendamentoMapper.toDto(agendamento.get());
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoConsultaDto> atualizar(@RequestBody AgendamentoAtualizarDto agendamento, @PathVariable int id) {
        Optional<Agendamento> buscaAgendamento = repository.findById(id);

        if (buscaAgendamento.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Object funcionarioOpt = (agendamento.getFkFuncionario() == null) ?
                buscaAgendamento.get().getFuncionario() : funcionarioRepository.findById(agendamento.getFkFuncionario());
        Object clienteOpt = (agendamento.getFkCliente() == null) ?
                buscaAgendamento.get().getCliente() : clienteRepository.findById(agendamento.getFkCliente());
        Object servicoPrecoOpt = (agendamento.getFkServicoPreco() == null) ?
                buscaAgendamento.get().getServicoPreco() : servicoPrecoRepository.findById(agendamento.getFkServicoPreco());

        if (funcionarioOpt instanceof Optional) {
            if (((Optional<?>) funcionarioOpt).isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                funcionarioOpt = ((Optional<?>) funcionarioOpt).get();
            }
        }

        if (clienteOpt instanceof Optional) {
            if (((Optional<?>) clienteOpt).isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                clienteOpt = ((Optional<?>) clienteOpt).get();
            }
        }

        if (servicoPrecoOpt instanceof Optional) {
            if (((Optional<?>) servicoPrecoOpt).isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                servicoPrecoOpt = ((Optional<?>) servicoPrecoOpt).get();
            }
        }

        LocalDateTime dtHora = (agendamento.getDtHora() == null) ? buscaAgendamento.get().getDtHora() : agendamento.getDtHora();
        LocalDate dia = (agendamento.getDia() == null) ? buscaAgendamento.get().getDia() : agendamento.getDia();
        LocalTime horario = (agendamento.getHorario() == null) ? buscaAgendamento.get().getHorario() : agendamento.getHorario();
        Integer bitStatus = (agendamento.getBitStatus() == null) ? buscaAgendamento.get().getBitStatus() : agendamento.getBitStatus();

        buscaAgendamento.get().setId(id);
        buscaAgendamento.get().setDtHora(dtHora);
        buscaAgendamento.get().setDia(dia);
        buscaAgendamento.get().setHorario(horario);
        buscaAgendamento.get().setBitStatus(bitStatus);
        buscaAgendamento.get().setCliente((Cliente) clienteOpt);
        buscaAgendamento.get().setFuncionario((Funcionario) funcionarioOpt);
        buscaAgendamento.get().setServicoPreco((ServicoPreco) servicoPrecoOpt);
        buscaAgendamento.get().setPreco(((ServicoPreco) servicoPrecoOpt).getPreco());
        buscaAgendamento.get().setComissao(((ServicoPreco) servicoPrecoOpt).getComissao());
        buscaAgendamento.get().setDuracao(((ServicoPreco) servicoPrecoOpt).getDuracao());

        Agendamento agendamentoAtualizado = repository.save(buscaAgendamento.get());
        AgendamentoConsultaDto dto = AgendamentoMapper.toDto(agendamentoAtualizado);

        return ResponseEntity.status(201).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Agendamento buscaAgendamento = repository.findById(id).orElse(null);
        if (buscaAgendamento == null) {
            return ResponseEntity.status(404).build();
        }

        repository.delete(buscaAgendamento);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalAgendamentosDoDia(
            @RequestParam("data") LocalDate data) {
        int total = agendamentoService.getTotaisAgendamentosDoDia(data);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/total/empresa")
    public ResponseEntity<Integer> getTotalAgendamentosDoDia(
            @RequestParam("data") LocalDate data,
            @RequestParam("empresaId") Integer empresaId) {
        int total = agendamentoService.getTotaisAgendamentosDoDia(data, empresaId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/lucro")
    public ResponseEntity<Double> getPotencialLucroDoDia(
            @RequestParam("data") LocalDate data) {
        Double lucro = agendamentoService.getPotencialLucroDoDia(data);
        return new ResponseEntity<>(lucro, HttpStatus.OK);
    }

    @GetMapping("/potencial-lucro")
    public ResponseEntity<Double> calcularPotencialLucroDoDia(
            @RequestParam("data") LocalDate data,
            @RequestParam("empresaId") Integer empresaId) {
        Double lucro = agendamentoService.getPotencialLucroDoDia(data, empresaId);
        return new ResponseEntity<>(lucro, HttpStatus.OK);
    }


    @GetMapping("/servico-mais-procurado")
    public ResponseEntity<String> getServicoMaisProcurado() {
        String servico = agendamentoService.getServicoMaisProcurado();
        return new ResponseEntity<>(servico, HttpStatus.OK);
    }

    @GetMapping("/servico-mais-procurado/empresa")
    public ResponseEntity<String> getServicoMaisProcurado(
            @RequestParam("empresaId") Integer empresaId) {
        String servico = agendamentoService.getServicoMaisProcurado(empresaId);
        return new ResponseEntity<>(servico, HttpStatus.OK);
    }

    @GetMapping("/proximos")
    public ResponseEntity<List<AgendamentoConsultaDto>> getProximosAgendamentos() {
        List<AgendamentoConsultaDto> proximos = agendamentoService.getProximosAgendamentos();
        return new ResponseEntity<>(proximos, HttpStatus.OK);
    }

    @GetMapping("/proximos/empresa")
    public ResponseEntity<List<AgendamentoConsultaDto>> getProximosAgendamentos(
            @RequestParam("empresaId") Integer empresaId) {
        List<AgendamentoConsultaDto> proximos = agendamentoService.getProximosAgendamentos(empresaId);
        return new ResponseEntity<>(proximos, HttpStatus.OK);
    }

    @GetMapping("/em-andamento")
    public ResponseEntity<List<AgendamentoConsultaDto>> getAgendamentosEmAndamento() {
        List<AgendamentoConsultaDto> emAndamento = agendamentoService.getAgendamentosEmAndamento();
        return new ResponseEntity<>(emAndamento, HttpStatus.OK);
    }

    @GetMapping("/em-andamento/empresa")
    public ResponseEntity<List<AgendamentoConsultaDto>> getAgendamentosEmAndamento(
            @RequestParam("empresaId") Integer empresaId) {
        List<AgendamentoConsultaDto> agendamentos = agendamentoService.getAgendamentosEmAndamento(empresaId);
        return new ResponseEntity<>(agendamentos, HttpStatus.OK);
    }

    // Sem recursão
    @GetMapping("/intervalos/{idEmpresa}/{data}")
    public ResponseEntity<List<LocalTime>> retornarIntervalosAtendimento(@PathVariable Integer idEmpresa, @PathVariable LocalDate data) {
        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        HorarioFuncionamento horario = horarioFuncionamentoRepository
                .getHorarioFuncionamentoDiaSemana(data.getDayOfWeek().getValue(), empresa.get());

        if (horario.getStatus() == 0) {
            return ResponseEntity.notFound().build();
        }

        LocalTime horarioAbertura = horario.getInicio();

        List<LocalTime> intervalos = new ArrayList<>();
        int periodoAberto = (int) java.time.Duration.between(horario.getInicio(), horario.getFim()).toHours();

        for (int i = 0; i < periodoAberto; i++) {
            intervalos.add(horarioAbertura);
            horarioAbertura = horarioAbertura.plusMinutes(60);
        }

        return ResponseEntity.ok().body(intervalos);
    }

    // Com recursão | retornaHorasServico + retornarDiaFuncionamento + gerarHorariosDia -> retornarIntervalosAtendimentoRecursao
    List<LocalTime> intervalos = new ArrayList<>();

    public Integer retornaHorasServico(Integer idEmpresa, LocalDate data) {
        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        HorarioFuncionamento horario = horarioFuncionamentoRepository
                .getHorarioFuncionamentoDiaSemana(data.getDayOfWeek().getValue(), empresa.get());
        if (horario.getStatus() == 0) {
            return 0;
        }
        return (int) java.time.Duration.between(horario.getInicio(), horario.getFim()).toHours();
    }

    public HorarioFuncionamento retornarDiaFuncionamento(LocalDate data, Integer empresa) {
        Empresa empresaOpt = empresaRepository.findById(empresa).orElse(null);
        return horarioFuncionamentoRepository.getHorarioFuncionamentoDiaSemana(data.getDayOfWeek().getValue(), empresaOpt);
    }

    public List<LocalTime> gerarHorariosDia(Integer horasServico, LocalTime horarioAbertura, Integer empresa, LocalDate data) {
        if (horasServico > 0) {
            intervalos.add(horarioAbertura);
            horarioAbertura = horarioAbertura.plusMinutes(60);
            horasServico--;
            return gerarHorariosDia(horasServico, horarioAbertura, empresa, data);
        }
        return intervalos;
    }

    @GetMapping("/intervalos/recursao/{idEmpresa}/{data}")
    public ResponseEntity<List<LocalTime>> retornarIntervalosAtendimentoRecursao(@PathVariable Integer idEmpresa, @PathVariable LocalDate data) {
        intervalos.clear();
        List<LocalTime> horarios = gerarHorariosDia(retornaHorasServico(idEmpresa, data),
                retornarDiaFuncionamento(data, idEmpresa).getInicio(), idEmpresa, data);

        if (horarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(horarios);
    }

    @GetMapping("/matriz/empresa/{idEmpresa}/data/{data}")
    public ResponseEntity<Object[][]> getAgendamentosDoDia(
            @PathVariable Integer idEmpresa,
            @PathVariable LocalDate data) {
        Object[][] agendamentos = agendamentoService.getAgendamentosDoDia(data, idEmpresa);
        return new ResponseEntity<>(agendamentos, HttpStatus.OK);
    }

    @GetMapping("/semana-atual")
    public String[][] getMatrizAgendamentosDaSemanaAtual() {
        return agendamentoService.exibirCalendario();
    }

    @GetMapping("/historico_agendamentos")
    public ResponseEntity<List<AgendamentoConsultaDto>> listarAgendamentosPorPeriodoEmpresa(
            @RequestParam Integer empresaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        List<AgendamentoConsultaDto> historico = agendamentoService.listarAgendamentosPorPeriodoEmpresa(empresaId, dataInicio, dataFim);
        return ResponseEntity.status(200).body(historico);
    }

    @GetMapping("/pendentes/{empresaId}")
    public ResponseEntity<List<AgendamentoConsultaDto>> listarAgendamentosPendentes(@PathVariable Integer empresaId) {
        List<AgendamentoConsultaDto> pendentes = agendamentoService.getAgendamentosPendentesEmpresa(empresaId);
        if (pendentes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(200).body(pendentes);
    }

    @GetMapping("/todos/{data}")
    public ResponseEntity<List<AgendamentoConsultaDto>> listarAgendamentosPorData(@PathVariable LocalDate data) {
        List<Agendamento> agendamentos = agendamentoService.getAgendamentosDoMomento(data);
        List<AgendamentoConsultaDto> agendamentosDto = AgendamentoMapper.toDto(agendamentos);
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(200).body(agendamentosDto);
    }


    @GetMapping("/empresa/{empresaId}/id/{id}")
    public ResponseEntity<AgendamentoConsultaDto> getAgendamentoPorIdEmpresa(@PathVariable Integer empresaId, @PathVariable Integer id) {
        Agendamento agendamento = repository.getAgendamentoPorIdEmpresa(empresaId, id);
        AgendamentoConsultaDto dto = AgendamentoMapper.toDto(agendamento);
        return ResponseEntity.status(200).body(dto);
    }

    @PatchMapping("/finalizar/{id}")
    public ResponseEntity<AgendamentoConsultaDto> finalizar(@PathVariable Integer id, @Valid @RequestBody AgendamentoFinalizarDto agendamentoFinalizarDto){
        Agendamento agendamentoSalvo = this.agendamentoService.finalizar(id, agendamentoFinalizarDto);
        AgendamentoConsultaDto agendamentoDto = AgendamentoMapper.toDto(agendamentoSalvo);
        return ResponseEntity.ok(agendamentoDto);

    }

    @PatchMapping("/atualizar-BitStatus-Cancelar-agendamento/{empresaId}/{agendamentoId}")
    public ResponseEntity<Void> atualizarBitStatus(@PathVariable Integer empresaId, @PathVariable Integer agendamentoId) {
        agendamentoService.atualizarBitStatus(agendamentoId, empresaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/qntd-funcionarios-dia/{empresaId}/{data}")
    public ResponseEntity<Integer> getQntdFuncionariosDia(@PathVariable Integer empresaId, @PathVariable LocalDate data) {
        Integer qntd = agendamentoService.getNumeroFuncionariosDia(empresaId, data);
        return ResponseEntity.ok(qntd);
    }

    @GetMapping("/historico-agendamentos-mes/{idEmpresa}/{mes}/{ano}")
    public ResponseEntity<List<AgendamentoConsultaDto>> listarAgendamentosPorMesEmpresa(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer mes,
            @PathVariable Integer ano) {
        List<Agendamento> historico = agendamentoService.getHistoricoDoMes(idEmpresa, Month.of(mes), Year.of(ano));
        if (historico.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AgendamentoConsultaDto> historicoDto = AgendamentoMapper.toDto(historico);
        return ResponseEntity.status(200).body(historicoDto);
    }

    @GetMapping("/csv/{empresaId}/{mes}/{ano}")
    public ResponseEntity<byte[]> getCsv(@PathVariable Integer empresaId, @PathVariable Integer mes, @PathVariable Integer ano) throws Exception {
        byte[] bytes = agendamentoService.getCsv(empresaId, Month.of(mes), Year.of(ano));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=agendamentos.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    /*public void gerarHorariosDisponiveis(Integer idServico, LocalDate data, Integer idEmpresa) {
        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        List<Agendamento> agendamentos = repository.findByDiaAndServicoPreco(data, servicoPrecoRepository.findById(idServico).get());

        List<HorarioFuncionamento> horarios = horarioFuncionamentoRepository.findByEmpresaEquals(empresa.get());
    }*/

    // testando finanças

    @GetMapping("/financas/receita-diaria/{empresaId}/{mes}/{ano}")
    public ResponseEntity<List<Map<String, Object>>> getReceitaDiaria(@PathVariable Integer empresaId,
                                                   @PathVariable Integer mes,
                                                   @PathVariable Integer ano) {
        List<Map<String, Object>> receita = agendamentoService.getReceitaDiaria(empresaId, Month.of(mes), Year.of(ano));
        return new ResponseEntity<>(receita, HttpStatus.OK);
    }

}