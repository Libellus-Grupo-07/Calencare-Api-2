package com.example.CalencareApi.entity;

import com.example.CalencareApi.dto.DashSemanaValorDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@SqlResultSetMapping(
        name = "AgendamentoDashSemanaValorDtoMapping",
        classes = @ConstructorResult(
                targetClass = DashSemanaValorDto.class,
                columns = {
                        @ColumnResult(name = "semana", type = Integer.class),
                        @ColumnResult(name = "valor", type = Double.class)
                }
        )
)
@Getter
@Setter
@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dtHora;
    private LocalDate dia;
    private LocalTime horario;
    private Double preco;
    private Double comissao;
    private Integer duracao;
    private Integer bitStatus;
    private String metodoPagamento;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private ServicoPreco servicoPreco;

    public String getCalendarId() {
        return funcionario.getEmail();
    }

    public String getSummary() {
        return servicoPreco.getServico().getNome();
    }

    public String getDescription() {
        return "Cliente: " + cliente.getNome() + "\n" +
                "Funcionário: " + funcionario.getNome() + "\n" +
                "Serviço: " + servicoPreco.getServico().getNome() + "\n" +
                "Preço: " + servicoPreco.getPreco();
    }

    public String getTimeZoneId() {
    return "America/Sao_Paulo";
    }

    public Date getStartDate() {
        return new Date(dtHora.getYear() - 1900, dtHora.getMonthValue() -1,
                dtHora.getDayOfMonth(), dtHora.getHour(), dtHora.getMinute());
    }

    public Date getEndDate() {
        Date startDate = getStartDate();
        return new Date(startDate.getTime() + (servicoPreco.getDuracao()*60000)); // 1 hora depois
    }
}