package com.example.CalencareApi.service;

import com.example.CalencareApi.api.via.cep.ViaCepService;
import com.example.CalencareApi.dto.endereco.EnderecoAtualizacaoDto;
import com.example.CalencareApi.dto.endereco.EnderecoConsultaDto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.Endereco;
import com.example.CalencareApi.listaObj.ListaObj;
import com.example.CalencareApi.mapper.EnderecoMapper;
import com.example.CalencareApi.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;
    @Autowired
    private ViaCepService viaCepService;
    @Autowired
    private EmpresaService empresaService;

    public EnderecoConsultaDto criarEndereco(String cep, Integer empresa, String numero, String complemento) {
        Endereco endereco = viaCepService.getEndereco(cep);
        Empresa empresaObj = empresaService.buscarEntidadePorId(empresa);
        endereco.setEmpresa(empresaObj);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco = repository.save(endereco);
        return EnderecoMapper.toDto(endereco);
    }

    public List<EnderecoConsultaDto> listarEnderecos() {
        List<Endereco> enderecos = repository.findAll();
        return EnderecoMapper.toDto(enderecos);
    }

    public EnderecoConsultaDto buscarEnderecoPorId(Integer id) {
        Optional<Endereco> enderecoOptional = repository.findById(id);
        return enderecoOptional.map(EnderecoMapper::toDto).orElse(null);
    }

    public EnderecoConsultaDto buscarEnderecoPorIdEmpresa(Integer idEmpresa){
        Endereco enderecoBusca = this.repository.findByEmpresaId(idEmpresa).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado.")
        );
        return EnderecoMapper.toDto(enderecoBusca);
    }

    public EnderecoConsultaDto atualizarEndereco(Integer id, EnderecoAtualizacaoDto enderecoDto) {
        Optional<Endereco> enderecoOptional = repository.findById(id);
        if (enderecoOptional.isPresent()) {
            Endereco endereco = enderecoOptional.get();
            endereco.setCep(enderecoDto.getCep());
            endereco.setLogradouro(enderecoDto.getLogradouro());
            endereco.setComplemento(enderecoDto.getComplemento());
            endereco.setBairro(enderecoDto.getBairro());
            endereco.setLocalidade(enderecoDto.getLocalidade());
            endereco.setUf(enderecoDto.getUf());
            endereco.setNumero(enderecoDto.getNumero());
            endereco.setEmpresa(enderecoDto.getEmpresa());
            endereco = repository.save(endereco);
            return EnderecoMapper.toDto(endereco);
        }
        return null;
    }

    public void deletarEndereco(Integer id) {
        repository.deleteById(id);
    }

    public List<Endereco> getUltimosAgendamentos() {
        List<Endereco> enderecos = repository.findAll();
        if (enderecos.isEmpty()) {
            return null;
        }

        for (int i = 0; i < enderecos.size() - 1; i++) {
            for (int j = 0; j < enderecos.size() - i - 1; j++) {
                if (enderecos.get(j).getLocalidade().compareTo(enderecos.get(j + 1).getLocalidade()) > 0) {
                    Endereco temp = enderecos.get(j);
                    enderecos.set(j, enderecos.get(j + 1));
                    enderecos.set(j + 1, temp);
                }
            }
        }

        ListaObj<Endereco> lista = new ListaObj<>(10);
        for (int i = 0; i < 10 && i < enderecos.size(); i++) {
            lista.adiciona(enderecos.get(i));
        }
        List<Endereco> result = new ArrayList<>();
        for (int i = 0; i < lista.getTamanho(); i++) {
            result.add(lista.getElemento(i));
        }
        return result;
    }

    public Endereco pesquisaBinariaPorCidade(String cidade) {
        List<Endereco> enderecos = getUltimosAgendamentos();
        int esquerda = 0;
        int direita = enderecos.size() - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            int res = cidade.compareTo(enderecos.get(meio).getLocalidade());

            // Verifica se a cidade está no meio
            if (res == 0)
                return enderecos.get(meio);

            // Se a cidade é maior, ignora a metade esquerda
            if (res > 0)
                esquerda = meio + 1;

                // Se a cidade é menor, ignora a metade direita
            else
                direita = meio - 1;
        }

        return null;
    }
}
