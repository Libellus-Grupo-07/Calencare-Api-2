package com.example.CalencareApi.api.via.cep;

import com.example.CalencareApi.entity.Endereco;
import com.example.CalencareApi.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ViaCepService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String VIA_CEP_URL = "https://viacep.com.br/ws";

    public Endereco getEndereco(String cep) {
        String url = UriComponentsBuilder.fromHttpUrl(VIA_CEP_URL)
                .path("/{cep}/json/")
                .buildAndExpand(cep)
                .toUriString();
//        enderecoRepository.save(restTemplate.getForObject(url, Endereco.class));
        return restTemplate.getForObject(url, Endereco.class);
    }
}