package com.mycompany.conta.bancaria.controller;

import com.mycompany.conta.bancaria.model.Cliente;
import com.mycompany.conta.bancaria.model.ExtratoCompleto;
import com.mycompany.conta.bancaria.model.Pagina;
import com.mycompany.conta.bancaria.service.ClienteService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author italo
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {
    private final ClienteService clienteService = new ClienteService();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        String acao = request.getParameter("acao");
        
        switch (acao) {
            case "cadastro":
                {
                    String nome = request.getParameter("nome");
                    String cpf = request.getParameter("cpf");
                    String senha = request.getParameter("senha");
                    String senhaConfirma = request.getParameter("senha-confirma");
                    // cpf sem pontos e traços, só com os numeros
                    String cpfLimpo = cpf.replaceAll("[^\\d]", "");
                    // Verificar se o cpf é válido
                    if (!clienteService.isCpfValido(cpf)) {
                        request.setAttribute("erro", "Formato do CPF é inválido!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        if (dispatcher!=null)
                            dispatcher.forward(request, response);
                        return;
                    }       // Verificar senhas
                    if (!senha.equals(senhaConfirma)) {
                        request.setAttribute("erro", "Senhas não coincidem!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        if (dispatcher!=null)
                            dispatcher.forward(request, response);
                        return;
                    }       // Verifica se já existe cpf cadastrado
                    if (clienteService.checkIfCpfAlreadyExists(cpfLimpo)) {
                        request.setAttribute("erro", "CPF já cadastrado no sistema!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        if (dispatcher!=null)
                            dispatcher.forward(request, response);
                        return;
                    }       try {
                        clienteService.cadastrarCliente(nome, cpf, senha);
                        response.sendRedirect("login.jsp");
                    } catch (SQLException e) {
                        System.err.println("Falha ao salvar novo cliente: " + e.getMessage());
                        
                        request.setAttribute("erro", "Erro ao criar conta!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        
                        dispatcher.forward(request, response);
                    }       break;    
                }
            case "login": {
                    String cpf = request.getParameter("cpf").replaceAll("[^\\d]", "");
                    String senha = request.getParameter("senha");

                    try {
                        Cliente clienteAutenticado = clienteService.autenticarCliente(cpf, senha);

                        // Se a autenticação falhar, clienteAutenticado será nulo
                        if (clienteAutenticado == null) {
                            // 1. Define o atributo "erro" na requisição
                            request.setAttribute("erro", "CPF ou senha inválidos!");

                            // 2. Encaminha (forward) de volta para a página de login, levando a requisição junto
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                            dispatcher.forward(request, response);
                            return; // Encerra a execução aqui
                        }

                        // Se a autenticação for bem-sucedida...
                        HttpSession session = request.getSession();
                        session.setAttribute("cliente", clienteAutenticado);

                        List<ExtratoCompleto> ultimasMovimentacoes = clienteService.buscarUltimasMovimentacoesExtratoPorCpf(clienteAutenticado.getCpf(), 3);
                        session.setAttribute("ultimasMovimentacoes", ultimasMovimentacoes);

                        response.sendRedirect("home.jsp");

                    } catch (Exception e) {
                        // Em caso de erro de banco de dados, etc.
                        System.err.println("Falha no processo de login: " + e.getMessage());
                        request.setAttribute("erro", "Ocorreu um erro no sistema. Tente novamente.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;
                }
            case "logout":
                {
                    HttpSession session = request.getSession(false);

                    if (session != null) {
                        session.invalidate();
                    }

                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    break;
                } 
            case "consultarSaldo":
                {
                    try {

                        String cpf = (String) request.getSession().getAttribute("cpf");
                        if (cpf == null) {
                            request.setAttribute("erro", "Usuário não autenticado.");
                            request.getRequestDispatcher("saldo.jsp").forward(request, response);
                            return;
                        }
                        com.mycompany.conta.bancaria.model.Cliente cliente = clienteService.buscarClientePorCpf(cpf);
                        if (cliente != null) {
                            request.setAttribute("saldo", cliente.getSaldo());
                        } else {
                            request.setAttribute("erro", "Cliente não encontrado.");
                        }
                    } catch (ServletException | IOException | SQLException e) {
                        request.setAttribute("erro", "Erro ao consultar saldo. Tente novamente mais tarde.");
                    }   request.getRequestDispatcher("saldo.jsp").forward(request, response);
                    break;
                }
            case "depositoForm":
                {
                    request.getRequestDispatcher("deposito.jsp").forward(request, response);
                    break;
                }
            case "realizarDeposito": {
                    HttpSession session = request.getSession();
                    Cliente clienteLogado = (Cliente) session.getAttribute("cliente");

                    if (clienteLogado == null) {
                        request.setAttribute("erro", "Usuário não autenticado.");
                        request.getRequestDispatcher("deposito.jsp").forward(request, response);
                        return;
                    }

                    String cpf = clienteLogado.getCpf();
                    String valor = request.getParameter("valor");

                    try {
                        clienteService.realizarDeposito(cpf, valor);
                        request.setAttribute("sucesso", "Depósito realizado com sucesso!");

                        // --- INÍCIO DA ATUALIZAÇÃO DA SESSÃO ---
                        // 1. Busca o cliente atualizado do banco (com saldo novo)
                        Cliente clienteAtualizado = clienteService.buscarClientePorCpf(clienteLogado.getCpf());

                        // 2. Atualiza o cliente na sessão
                        session.setAttribute("cliente", clienteAtualizado);

                        // 3. Atualiza as últimas movimentações na sessão
                        List<ExtratoCompleto> ultimasMovimentacoes = clienteService.buscarUltimasMovimentacoesExtratoPorCpf(clienteAtualizado.getCpf(), 3);
                        session.setAttribute("ultimasMovimentacoes", ultimasMovimentacoes);
                        // --- FIM DA ATUALIZAÇÃO DA SESSÃO ---

                    } catch (Exception e) {
                        request.setAttribute("erro", e.getMessage());
                    }

                    request.getRequestDispatcher("deposito.jsp").forward(request, response);
                    break;
                }


            case "verExtrato":
                {
                    HttpSession session = request.getSession();
                    Cliente clienteLogado = (Cliente) session.getAttribute("cliente");
                    
                    if (clienteLogado == null) {
                        request.setAttribute("erro", "Sessão expirada. Faça o login novamente.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    }
                    
                    final int REGISTROS_POR_PAGINA = 10;
                    
                    int paginaAtual = 1;
                    String paginaParam = request.getParameter("pagina");
                    if (paginaParam != null && !paginaParam.isEmpty()) {
                        try {
                            paginaAtual = Integer.parseInt(paginaParam);
                        } catch (NumberFormatException e) {
                            paginaAtual = 1;
                        }
                    }
                    
                    try {
                        Pagina<ExtratoCompleto> paginaExtrato = clienteService
                                .buscarExtratoPaginadoPorCpf(
                                        clienteLogado.getCpf(), 
                                        paginaAtual, 
                                        REGISTROS_POR_PAGINA);
                        
                        request.setAttribute("paginaExtrato", paginaExtrato);
                    } catch(Exception e) {
                        request.setAttribute("erro", e.getMessage());
                    }
                    
                    
                    request.getRequestDispatcher("extrato.jsp").forward(request, response);
                    break;
                }
            case "saqueForm":
                {   
                    request.getRequestDispatcher("saque.jsp").forward(request, response);
                    break;
                }
            case "realizarSaque": {
                    HttpSession session = request.getSession();
                    Cliente clienteLogado = (Cliente) session.getAttribute("cliente");

                    if (clienteLogado == null) {
                        request.setAttribute("erro", "Usuário não autenticado.");
                        request.getRequestDispatcher("saque.jsp").forward(request, response);
                        return;
                    }

                    String cpf = clienteLogado.getCpf();
                    String valor = request.getParameter("valor");

                    try {
                        clienteService.realizarSaque(cpf, valor);
                        request.setAttribute("sucesso", "Saque realizado com sucesso!");

                        // --- INÍCIO DA ATUALIZAÇÃO DA SESSÃO ---
                        // 1. Busca o cliente atualizado do banco (com saldo novo)
                        Cliente clienteAtualizado = clienteService.buscarClientePorCpf(clienteLogado.getCpf());

                        // 2. Atualiza o cliente na sessão
                        session.setAttribute("cliente", clienteAtualizado);

                        // 3. Atualiza as últimas movimentações na sessão
                        List<ExtratoCompleto> ultimasMovimentacoes = clienteService.buscarUltimasMovimentacoesExtratoPorCpf(clienteAtualizado.getCpf(), 3);
                        session.setAttribute("ultimasMovimentacoes", ultimasMovimentacoes);
                        // --- FIM DA ATUALIZAÇÃO DA SESSÃO ---

                    } catch (Exception e) {
                        request.setAttribute("erro", e.getMessage());
                    }

                    request.getRequestDispatcher("saque.jsp").forward(request, response);
                    break;
                }

            case "realizarTransferencia": 
                {
                    HttpSession session = request.getSession();
                    Cliente clienteLogado = (Cliente) session.getAttribute("cliente");

                    if (clienteLogado == null) {
                        request.setAttribute("erro", "Sessão expirada. Faça o login novamente.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    }

                    String cpfRemetente = clienteLogado.getCpf();
                    String contaDestino = request.getParameter("conta");
                    String valor = request.getParameter("valor");

                    try {
                        clienteService.realizarTransferencia(cpfRemetente, contaDestino, valor);
                        request.setAttribute("sucesso", "Transferência realizada com sucesso!");
                    } catch (Exception e) {
                        request.setAttribute("erro", e.getMessage());
                    }

                    // Busca o cliente atualizado do banco (com saldo novo)
                    Cliente clienteAtualizado = clienteService.buscarClientePorCpf(clienteLogado.getCpf());

                    // Atualiza o cliente na sessão
                    request.getSession().setAttribute("cliente", clienteAtualizado);

                    // Atualiza as movimentações do cliente na sessão
                    List<ExtratoCompleto> ultimasMovimentacoes = clienteService.buscarUltimasMovimentacoesExtratoPorCpf(clienteAtualizado.getCpf(), 3);
                    request.getSession().setAttribute("ultimasMovimentacoes", ultimasMovimentacoes);
                    
                    request.getRequestDispatcher("transferencia.jsp").forward(request, response);
                    break;
                }
            default:
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
