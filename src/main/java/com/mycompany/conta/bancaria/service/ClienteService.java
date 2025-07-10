/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.service;


import com.mycompany.conta.bancaria.model.Cliente;
import com.mycompany.conta.bancaria.model.Extrato;
import com.mycompany.conta.bancaria.model.ExtratoCompleto;
import com.mycompany.conta.bancaria.model.Transferencias;
import com.mycompany.conta.bancaria.repository.ClienteRepository;
import com.mycompany.conta.bancaria.repository.ExtratoRepository;
import com.mycompany.conta.bancaria.repository.TransferenciaRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author italo
 */
public class ClienteService {
    private final ClienteRepository repository = new ClienteRepository();
    private final ExtratoRepository extratoRepository = new ExtratoRepository();
    private final TransferenciaRepository transferenciaRepository = new TransferenciaRepository();

    
    /**
     * Método para cadastrar novo cliente no sistema
     * @param nome
     * @param cpf
     * @param senha
     * @throws java.sql.SQLException
     */
    public void cadastrarCliente(String nome, String cpf, String senha) throws SQLException {
            
        Cliente novoCliente = new Cliente();

        novoCliente.setNome(nome);
        novoCliente.setCpf(cpf.replaceAll("[^\\d]", ""));

        // Gera o hash da senha
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt(12));
        novoCliente.setSenhaHash(senhaHash);

        // Gera numero da conta
        Long contaBancaria = gerarNumeroContaBancaria();
        novoCliente.setContaBancaria(contaBancaria);

        repository.save(novoCliente);
            
    }
    
    /**
     * Realiza a autenticação de um cliente
     *
     * @param cpf
     * @param senha
     * @return 
     * @throws java.sql.SQLException 
     */
    public Cliente autenticarCliente(String cpf, String senha) throws SQLException {
                
        Cliente cliente = repository.findClienteByCpf(cpf);

        if (cliente == null) return null;
       
        boolean senhaValida = BCrypt.checkpw(senha, cliente.getSenhaHash());

        if (!senhaValida) return null;
        
        return cliente;
    }

    
    /**
    * Verifica se o cpf passado como parâmetro já existe
    *
     * @param cpfToCheck
    * @return true, caso exista e false caso contrário
     * @throws java.sql.SQLException
    */
    public boolean checkIfCpfAlreadyExists(String cpfToCheck) throws SQLException {
        return repository.existsByCpf(cpfToCheck);
    }
    
    /**
    * Gera numero aleatorio e unico para conta bancaria
    *
    * @return numero da conta
     * @throws java.sql.SQLException
    */
    public Long gerarNumeroContaBancaria() throws SQLException {
        Random random = new Random();
        
        Long numeroConta;
        Cliente cliente;
        
        do {
            numeroConta = 100000 + random.nextLong(900000);
            cliente = repository.findClienteByContaBancaria(numeroConta);
        } while(cliente != null);
        
        return numeroConta;
    }
    
    /**
    * Valida se uma String corresponde a um número de CPF válido.
    *
    * @param cpf O CPF a ser validado, pode estar formatado ou não.
    * @return {@code true} se o CPF for válido, {@code false} caso contrário.
    */
    public boolean isCpfValido(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");

        return cpfLimpo.length() == 11;
    }

    /**
     * Busca um cliente pelo CPF.
     * @param cpf CPF do cliente
     * @return Cliente encontrado ou null
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Cliente buscarClientePorCpf(String cpf) throws SQLException {
        return repository.findClienteByCpf(cpf);
    }

    /**
     * Realiza depósito na conta do cliente identificado pelo CPF.
     * @param cpf CPF do cliente
     * @param valorDeposito Valor a ser depositado
     * @return novo saldo após depósito
     * @throws Exception se valor inválido ou erro de banco
     */
    public BigDecimal realizarDeposito(String cpf, String valorDeposito) throws Exception {
        if (valorDeposito == null) throw new Exception("Valor não informado");
        BigDecimal valor;
        try {
            valor = new BigDecimal(valorDeposito.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("Valor inválido");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor deve ser maior que zero");
        }
        Cliente cliente = repository.findClienteByCpf(cpf);
        if (cliente == null) throw new Exception("Cliente não encontrado");
        BigDecimal novoSaldo = cliente.getSaldo().add(valor);
        repository.atualizarSaldoPorCpf(cpf, novoSaldo);
        // Registrar no extrato
        Extrato extrato = new Extrato();
        extrato.setCreatedAt(new Date());
        extrato.setTipo("DEPOSITO");
        extrato.setValor(valor);
        extrato.setIdCliente(cliente.getId());
        extratoRepository.registrarTransacao(extrato);
        return novoSaldo;
    }

    /**
     * Busca o extrato (transações) do cliente pelo CPF.
     * @param cpf CPF do cliente
     * @return Lista de transações
     * @throws Exception se cliente não encontrado ou erro de banco
     */
    public java.util.List<Extrato> buscarExtratoPorCpf(String cpf) throws SQLException, Exception {
        Cliente cliente = repository.findClienteByCpf(cpf);
        if (cliente == null) throw new Exception("Cliente não encontrado");
        return extratoRepository.buscarTransacoesPorCliente(cliente.getId());
    }
    
    /**
     * Busca uma quantidade definida das últimas movimentações do extrato de um
     * cliente, identificado pelo CPF.
     * Este método primeiro localiza o cliente pelo CPF. Se não o encontrar, lança
     * uma exceção. Caso contrário, busca as últimas transações para o ID do cliente
     * encontrado.
     *
     * @param cpf O CPF do cliente a ser consultado.
     * @param qtdRegistros A quantidade máxima de últimas movimentações a serem retornadas.
     * @return Uma lista ({@code List<Extrato>}) contendo as últimas movimentações,
     * ordenada da mais recente para a mais antiga. Pode retornar uma lista
     * vazia se não houver transações.
     * @throws SQLException se ocorrer um erro de acesso ao banco de dados na busca
     * das transações.
     * @throws Exception se o cliente com o CPF fornecido não for encontrado.
     */
    public List<ExtratoCompleto> buscarUltimasMovimentacoesExtratoPorCpf(String cpf, int qtdRegistros) throws SQLException, Exception {
        Cliente cliente = repository.findClienteByCpf(cpf);
        if (cliente == null) throw new Exception("Cliente não encontrado");
        return extratoRepository.buscarUltimasTransacoesPorCliente(cliente.getId(), qtdRegistros);
    }

    /**
     * Realiza saque na conta do cliente identificado pelo CPF.
     * @param cpf CPF do cliente
     * @param valorSaque Valor a ser sacado
     * @return novo saldo após saque
     * @throws Exception se valor inválido, saldo insuficiente ou erro de banco
     */
    public BigDecimal realizarSaque(String cpf, String valorSaque) throws Exception {
        if (valorSaque == null) throw new Exception("Valor não informado");
        BigDecimal valor;
        try {
            valor = new BigDecimal(valorSaque.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("Valor inválido");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor deve ser maior que zero");
        }
        Cliente cliente = repository.findClienteByCpf(cpf);
        if (cliente == null) throw new Exception("Cliente não encontrado");
        if (cliente.getSaldo().compareTo(valor) < 0) {
            throw new Exception("Saldo insuficiente");
        }
        BigDecimal novoSaldo = cliente.getSaldo().subtract(valor);
        repository.atualizarSaldoPorCpf(cpf, novoSaldo);
        // Registrar no extrato
        Extrato extrato = new Extrato();
        extrato.setCreatedAt(new Date());
        extrato.setTipo("SAQUE");
        extrato.setValor(valor);
        extrato.setIdCliente(cliente.getId());
        extratoRepository.registrarTransacao(extrato);
        return novoSaldo;
    }
    
    public void realizarTransferencia(String cpfRemetente, String contaDestinoStr, String valorTransferenciaStr) throws Exception {
        // 1. Validação do Valor
        if (valorTransferenciaStr == null || valorTransferenciaStr.isBlank()) {
            throw new Exception("Valor não informado");
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(valorTransferenciaStr.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("Valor inválido");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor da transferência deve ser maior que zero");
        }

        // 2. Validação das Contas
        Cliente remetente = repository.findClienteByCpf(cpfRemetente);
        if (remetente == null) {
            throw new Exception("Falha ao identificar o remetente.");
        }

        Long contaDestinoNumero = Long.parseLong(contaDestinoStr.replaceAll("[^\\d]", ""));
        Cliente destinatario = repository.findClienteByContaBancaria(contaDestinoNumero);

        if (destinatario == null) {
            throw new Exception("Conta de destino não encontrada");
        }

        if (remetente.getId().equals(destinatario.getId())) {
            throw new Exception("Você não pode transferir para a sua própria conta.");
        }

        // 3. Validação do Saldo
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new Exception("Saldo insuficiente");
        }

        // 4. Cria e salva o registro principal da transferência
        Transferencias novaTransferencia = new Transferencias();
        novaTransferencia.setValor(valor);
        novaTransferencia.setCreatedAt(new java.util.Date());
        novaTransferencia.setIdRemetente(remetente.getId());
        novaTransferencia.setIdDestinatario(destinatario.getId());

        novaTransferencia = transferenciaRepository.registrarTransferencia(novaTransferencia);

        // 5. Atualiza o saldo das duas contas
        BigDecimal novoSaldoRemetente = remetente.getSaldo().subtract(valor);
        BigDecimal novoSaldoDestinatario = destinatario.getSaldo().add(valor);

        repository.atualizarSaldoPorCpf(remetente.getCpf(), novoSaldoRemetente);
        repository.atualizarSaldoPorCpf(destinatario.getCpf(), novoSaldoDestinatario);

        // 6. Registra as duas transações no extrato
        Extrato extratoRemetente = new Extrato();
        extratoRemetente.setIdCliente(remetente.getId());
        extratoRemetente.setIdTransferencia(novaTransferencia.getId());
        extratoRemetente.setCreatedAt(new java.util.Date());
        extratoRemetente.setTipo("TRANSFERENCIA_ENVIADA");
        extratoRemetente.setValor(valor.negate());
        extratoRepository.registrarTransacao(extratoRemetente);

        Extrato extratoDestinatario = new Extrato();
        extratoDestinatario.setIdCliente(destinatario.getId());
        extratoDestinatario.setIdTransferencia(novaTransferencia.getId());
        extratoDestinatario.setCreatedAt(new java.util.Date());
        extratoDestinatario.setTipo("TRANSFERENCIA_RECEBIDA");
        extratoDestinatario.setValor(valor);
        extratoRepository.registrarTransacao(extratoDestinatario);
    }
}
    