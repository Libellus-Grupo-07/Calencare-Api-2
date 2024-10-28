package com.example.CalencareApi.service;

/*import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;*/
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfService {

    /*private final DashBoardService dashBoardService;

    public byte[] createPdf(Integer empresaId, LocalDate date, Pageable pageable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");

            // Obtendo dados do serviço
            Optional<Map<String, Integer>> agendamentoStats = dashBoardService.getAgendamentoStats(empresaId, date);
            Optional<Map<String, Object>> lucroTotalDoDia = dashBoardService.getLucroTotalDoDia(empresaId, date);
            Optional<List<Map<String, Object>>> servicoMaisProcurado = dashBoardService.getServicoMaisProcuradoERentabilidadeDoDia(empresaId, date);
            Optional<List<Map<String, Object>>> agendamentosDoDia = dashBoardService.getAgendamentosDoDiaPorProfissional(empresaId, date);
            List<Map<String, Object>> top3Servicos = dashBoardService.getTop3Servicos(empresaId, date, pageable);
            List<Map<String, Object>> top3Profissionais = dashBoardService.getTop3Profissionais(empresaId, date, pageable);
            List<Map<String, Object>> top3Clientes = dashBoardService.getTop3Clientes(empresaId, date, pageable);

            // Adicionando dados ao PDF
            document.add(new Paragraph(new Text("Relatório de Agendamentos e Rentabilidade").setFont(bold)));

            agendamentoStats.ifPresent(stats -> {
                document.add(new Paragraph(new Text("Estatísticas de Agendamentos:").setFont(bold)));
                stats.forEach((key, value) -> {
                    document.add(new Paragraph(key + ": " + value));
                });
            });

            lucroTotalDoDia.ifPresent(lucro -> {
                document.add(new Paragraph(new Text("Lucro Total do Dia:").setFont(bold)));
                Object lucroTotal = lucro.get("LucroTotalDoDia");
                document.add(new Paragraph("Lucro Total do Dia: " + (lucroTotal != null ? lucroTotal.toString() : "")));
            });

            servicoMaisProcurado.ifPresent(servicos -> {
                document.add(new Paragraph(new Text("Serviço Mais Procurado do Dia:").setFont(bold)));
                servicos.forEach(servico -> {
                    document.add(new Paragraph(formatMapToString(servico)));
                });
            });

            agendamentosDoDia.ifPresent(agendamentos -> {
                document.add(new Paragraph(new Text("Agendamentos do Dia por Profissional:").setFont(bold)));
                agendamentos.forEach(agendamento -> {
                    document.add(new Paragraph(formatMapToString(agendamento)));
                });
            });

            if (!top3Servicos.isEmpty()) {
                document.add(new Paragraph(new Text("Top 3 Serviços:").setFont(bold)));
                top3Servicos.forEach(servico -> {
                    document.add(new Paragraph(formatMapToString(servico)));
                });
            }

            if(!top3Profissionais.isEmpty()) {
                document.add(new Paragraph(new Text("Top 3 Profissionais:").setFont(bold)));
                top3Profissionais.forEach(profissional -> {
                    document.add(new Paragraph(formatMapToString(profissional)));
                });
            }

            if(!top3Clientes.isEmpty()) {
                document.add(new Paragraph(new Text("Top 3 Clientes:").setFont(bold)));
                top3Clientes.forEach(cliente -> {
                    document.add(new Paragraph(formatMapToString(cliente)));
                });
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private String formatMapToString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((key, value) -> {
            if (value != null) {
                sb.append(key).append(": ").append(value.toString()).append("\n");
            }
        });
        return sb.toString();
    }*/
}
