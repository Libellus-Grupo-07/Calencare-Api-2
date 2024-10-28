/*    package com.example.crudAdm.controllers;

    import com.example.crudAdm.service.PdfService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.format.annotation.DateTimeFormat;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.io.ByteArrayInputStream;
    import java.time.LocalDate;

    @RestController
    @RequestMapping("/pdfs")
    public class PdfController {

        @Autowired
        private PdfService pdfService;

        @GetMapping("/{empresaId}")
        public ResponseEntity<byte[]> getPdfReport(
                @PathVariable Integer empresaId,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "3") int size) {

            Pageable pageable = PageRequest.of(page, size);
            byte[] pdfBytes = pdfService.createPdf(empresaId, date, pageable);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=relatorio_"+ date +".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }
    }
*/