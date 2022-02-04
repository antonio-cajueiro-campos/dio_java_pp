package com.antonio.viaceptest.service;

import com.antonio.viaceptest.model.Cliente;
import com.antonio.viaceptest.model.Endereco;
import com.antonio.viaceptest.model.IClienteRepository;
import com.antonio.viaceptest.model.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author akky
 * @see <a href="http://google.com</a>">google<a/>
 */
@Service
public class ClienteService {
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private IEnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;


    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
