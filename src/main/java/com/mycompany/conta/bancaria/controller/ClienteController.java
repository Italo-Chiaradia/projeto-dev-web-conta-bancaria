package com.mycompany.conta.bancaria.controller;

import com.mycompany.conta.bancaria.service.ClienteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author italo
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {
    private final ClienteDAO clienteDAO = new ClienteDAO();
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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String acao = request.getParameter("acao");
        
        if (acao.equals("cadastro")) {            
            clienteDAO.cadastrarCliente(request, response);
        } else if (acao.equals("consultarSaldo")) {
            try {

                String cpf = (String) request.getSession().getAttribute("cpf");
                if (cpf == null) {
                    request.setAttribute("erro", "Usuário não autenticado.");
                    request.getRequestDispatcher("saldo.jsp").forward(request, response);
                    return;
                }
                com.mycompany.conta.bancaria.model.Cliente cliente = clienteDAO.buscarClientePorCpf(cpf);
                if (cliente != null) {
                    request.setAttribute("saldo", cliente.getSaldo());
                } else {
                    request.setAttribute("erro", "Cliente não encontrado.");
                }
            } catch (Exception e) {
                request.setAttribute("erro", "Erro ao consultar saldo. Tente novamente mais tarde.");
            }
            request.getRequestDispatcher("saldo.jsp").forward(request, response);
        } else if (acao.equals("depositoForm")) {
            request.getRequestDispatcher("deposito.jsp").forward(request, response);
        } else if (acao.equals("realizarDeposito")) {
            String cpf = (String) request.getSession().getAttribute("cpf");
            if (cpf == null) {
                request.setAttribute("erro", "Usuário não autenticado.");
                request.getRequestDispatcher("deposito.jsp").forward(request, response);
                return;
            }
            String valor = request.getParameter("valor");
            try {
                java.math.BigDecimal novoSaldo = clienteDAO.realizarDeposito(cpf, valor);
                request.setAttribute("sucesso", "Depósito realizado com sucesso!");
                request.setAttribute("novoSaldo", novoSaldo);
            } catch (Exception e) {
                request.setAttribute("erro", e.getMessage());
            }
            request.getRequestDispatcher("deposito.jsp").forward(request, response);
        } else if (acao.equals("verExtrato")) {
            String cpf = (String) request.getSession().getAttribute("cpf");
            if (cpf == null) {
                request.setAttribute("erro", "Usuário não autenticado.");
                request.getRequestDispatcher("extrato.jsp").forward(request, response);
                return;
            }
            try {
                java.util.List<com.mycompany.conta.bancaria.model.Extrato> transacoes = clienteDAO.buscarExtratoPorCpf(cpf);
                request.setAttribute("transacoes", transacoes);
            } catch (Exception e) {
                request.setAttribute("erro", e.getMessage());
            }
            request.getRequestDispatcher("extrato.jsp").forward(request, response);
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
