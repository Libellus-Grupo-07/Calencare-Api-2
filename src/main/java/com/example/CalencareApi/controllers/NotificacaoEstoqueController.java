package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.NotificacaoEstoqueConsultaDto;
import com.example.CalencareApi.entity.NotificacaoEstoque;
import com.example.CalencareApi.service.NotificacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacao-estoque")
public class NotificacaoEstoqueController {

    @Autowired private NotificacaoEstoqueService notificacaoEstoqueService;

    @GetMapping("/buscar/{idEmpresa}")
    public ResponseEntity<List<NotificacaoEstoqueConsultaDto>> buscarNotificacoesNaoExcluidas(@PathVariable Integer idEmpresa) {
        if (idEmpresa == null) {
            return ResponseEntity.badRequest().build();
        }
        if (notificacaoEstoqueService.buscarNotificacoesNaoExcluidas(idEmpresa) == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(201).body(notificacaoEstoqueService.buscarNotificacoesNaoExcluidas(idEmpresa));
    }

    @DeleteMapping("/excluir/{idNotificacao}")
    public void excluirNotificacao(@PathVariable Integer idNotificacao) {
        notificacaoEstoqueService.excluirNotificacao(idNotificacao);
    }

    @PatchMapping("/marcar-como-lida/{idNotificacao}")
    public void marcarComoLida(@PathVariable Integer idNotificacao) {
        notificacaoEstoqueService.marcarComoLida(idNotificacao);
    }

    @PatchMapping("/marcar-todas-como-lidas/{idEmpresa}")
    public void marcarTodasComoLidas(@PathVariable Integer idEmpresa) {
        notificacaoEstoqueService.marcarTodasComoLidas(idEmpresa);
    }

    @GetMapping("/validar-notificacoes-lidas/{idEmpresa}")
    public ResponseEntity<Boolean> validarNotificacoesLidas(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(notificacaoEstoqueService.validarNotificacoesNaoLidas(idEmpresa));
    }
}
