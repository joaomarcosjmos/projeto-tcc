package com.uva.moneyapi.config;

import com.uva.moneyapi.enuns.TipoLancamento;
import com.uva.moneyapi.model.Categoria;
import com.uva.moneyapi.model.Endereco;
import com.uva.moneyapi.model.Lancamento;
import com.uva.moneyapi.model.Pessoa;
import com.uva.moneyapi.repository.CategoriaRepository;
import com.uva.moneyapi.repository.LancamentoRepository;
import com.uva.moneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("aaa")
public class ConfigTest implements CommandLineRunner {

    @Autowired
    public PessoaRepository repository;

    @Autowired
    public CategoriaRepository categoriaRepository;

    @Autowired
    public LancamentoRepository lancamentoRepository;

    @Override
    public void run(String... args) throws Exception {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Marcos Oliveira");
        pessoa.setAtivo(true);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teresópolis");
        endereco.setNumero("105");
        endereco.setComplemento("Apto 202");
        endereco.setBairro("Fluminense");
        endereco.setCep("28941-358");
        endereco.setCidade("São Pedro da Aldeia");
        endereco.setEstado("Rio de Janeiro");

        pessoa.setEndereco(endereco);
        repository.save(pessoa);

        Categoria c1 = new Categoria();
        c1.setNome("Mercado");

        Categoria c2 = new Categoria();
        c2.setNome("Lazer");

        Categoria c3 = new Categoria();
        c3.setNome("Transporte");

        categoriaRepository.saveAll(Arrays.asList(c1,c2,c3));

        Lancamento l1 = new Lancamento();
        l1.setPessoa(pessoa);
        l1.setCategoria(c1);
        l1.setDescricao("Descrição receita");
        l1.setObservacao("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        l1.setDataVencimento(LocalDate.now());
        l1.setDataPagamento(LocalDate.now());
        l1.setValor(new BigDecimal(10000));
        l1.setTipo(TipoLancamento.RECEITA);

        Lancamento l2 = new Lancamento();
        l2.setPessoa(pessoa);
        l2.setCategoria(c2);
        l2.setDescricao("Descrição despesa");
        l2.setObservacao("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        l2.setDataVencimento(LocalDate.now().plusDays(30));
        l2.setDataPagamento(LocalDate.now());
        l2.setValor(new BigDecimal(10000));
        l2.setTipo(TipoLancamento.DESPESA);

        lancamentoRepository.saveAll(Arrays.asList(l1,l2));


    }
}
