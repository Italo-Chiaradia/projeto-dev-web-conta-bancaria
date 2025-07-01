/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.service;


import com.mycompany.conta.bancaria.model.Cliente;
import com.mycompany.conta.bancaria.repository.ClienteRepository;
import com.mycompany.conta.bancaria.util.ConnectionFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author italo
 */
public class ClienteDAO {
    private final ClienteRepository repository = new ClienteRepository();
    
    /**
     * Método para cadastrar novo cliente no sistema. Com validação de cpf e
     * senha antes da inserção no banco de dados.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    public void cadastrarCliente(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");
            String senhaConfirma = request.getParameter("senha-confirma");
            
            // cpf sem pontos e traços, só com os numeros
            String cpfLimpo = cpf.replaceAll("[^\\d]", "");
            
            // Verificar se o cpf é válido
            if (!isCpfValido(cpf)) {
                request.setAttribute("erro", "Formato do CPF é inválido!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                if (dispatcher!=null)
                    dispatcher.forward(request, response);
                return;
            }
            
            // Verificar senhas
            if (!senha.equals(senhaConfirma)) {
                request.setAttribute("erro", "Senhas não coincidem!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                if (dispatcher!=null)
                    dispatcher.forward(request, response);
                return;
            }
            
            // Verifica se já existe cpf cadastrado
            if (checkIfCpfAlreadyExists(cpfLimpo)) {
                request.setAttribute("erro", "CPF já cadastrado no sistema!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                if (dispatcher!=null)
                    dispatcher.forward(request, response);
                return;
            }
            
            try {
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

                response.sendRedirect("login.jsp");
                
            } catch (SQLException e) {
                System.err.println("Falha ao salvar novo cliente: " + e.getMessage());
                
                request.setAttribute("erro", "Erro ao criar conta!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                
                dispatcher.forward(request, response);
            }
            
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

}
    