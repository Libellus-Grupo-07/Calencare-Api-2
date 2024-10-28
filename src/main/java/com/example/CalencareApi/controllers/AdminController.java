package com.example.CalencareApi.controllers;

import com.example.CalencareApi.models.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private List<Admin> admins = new ArrayList<>();
    private int id;

    @PostMapping
    public ResponseEntity<Admin> cadastrar(@RequestBody Admin admin) {

        if (existeCpf(admin.getCpf(), admins)) { // Verifica se o cpf já existe
            return ResponseEntity.status(409).build();
        }

        if (!cpfValido(admin.getCpf())) { // Verifica se CPF atende a regra
            return ResponseEntity.status(400).build();
        }
        admin.setId(++id);
        admins.add(admin);
        return ResponseEntity.status(201).body(admin);
    }


    @GetMapping
    public ResponseEntity<List<Admin>> listar() {
        if (admins.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> buscarPorId(@PathVariable int id) {
        Admin admin = buscarAdminPorId(id);
        if (admin == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(admin);
    }

    @GetMapping("/ordenacao")
    public ResponseEntity<List<Admin>> ordenarPNome() {
        for (int i = 0; i < admins.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < admins.size(); j++) {
                if (admins.get(j).getNome().compareTo(admins.get(minIndex).getNome()) < 0) {
                    minIndex = j;
                }
            }
            Admin temp = admins.get(i);
            admins.set(i, admins.get(minIndex));
            admins.set(minIndex, temp);
        }
        return ResponseEntity.status(200).body(admins);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Admin> atualizar(@RequestBody Admin admin, @PathVariable int id) {
        // Busca o carro pelo id e atualiza os dados
        Admin adminAtualizado = buscarAdminPorId(id);

        if (adminAtualizado == null) {
            return ResponseEntity.status(404).build();
        }

        if (admin.getCpf().equals(adminAtualizado.getCpf())) {
            adminAtualizado.setNome(admin.getNome());
            adminAtualizado.setSobrenome(admin.getSobrenome());
            adminAtualizado.setTelefone(admin.getTelefone());
            adminAtualizado.setEmail(admin.getEmail());
            adminAtualizado.setSenha(admin.getSenha());
            adminAtualizado.setStatus(admin.getStatus());

            return ResponseEntity.status(200).body(adminAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deletar(@PathVariable int id) {

        // Busca o carro pelo id
        Admin admin = buscarAdminPorId(id);

        // Se o carro não existir, retorna 404
        if (admin == null) {
            return ResponseEntity.status(404).build();
        }

        // Remove o carro da lista de carros pelo objeto retornado da busca pelo id
        admins.remove(admin);
        return ResponseEntity.status(204).body(admin);
    }

    private Admin buscarAdminPorId(int id) {

        for (Admin admin : admins) {
            if (admin.getId() == id) {
                return admin;
            }
        }

        return null;
    }

    public static Boolean cpfValido(String cpf){
        int somaTotal = 0;
        char[] numeros = cpf.toCharArray();
        for (int i = 0; i < cpf.length(); i++) {
            somaTotal += numeros[i];
        }

        if (somaTotal % 11 == 0) {
            return true;
        }

        return false;
    }

    public static Boolean existeCpf(String cpf, List<Admin> usuarios){
        return usuarios
                .stream()
                .anyMatch(usuario -> usuario.getCpf().equals(cpf));
    }
}
