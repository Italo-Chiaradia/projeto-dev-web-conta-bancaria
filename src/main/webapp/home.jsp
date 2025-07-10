<%-- 
    Document   : home
    Created on : 8 de jul. de 2025, 18:39:18
    Author     : italo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="pt_BR" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- ================================================================== --%>
<%--          BLOCO DE VERIFICAÇÃO DE SESSÃO DO USUÁRIO                 --%>
<%-- ================================================================== --%>
<c:if test="${empty sessionScope.cliente}">
    <c:redirect url="/login.jsp">
        <c:param name="erro" value="Realize o login novamente" />
    </c:redirect>
</c:if>


<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bankly - Home</title>

    <link rel="icon" type="image/x-icon" href="./assets/icon-bankly.png" />
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/home.css">
</head>
<body>

    <div class="dashboard-container">
        <header>
            <div class="header-content">
                <div class="welcome-message">
                    <c:if test="${not empty sessionScope.cliente}">
                        <h1>Olá, <c:out value="${sessionScope.cliente.nome}"/></h1>
                    </c:if>
                </div>

                <div class="user-profile">
                    <div class="user-avatar">
                        <span><c:out value="${sessionScope.cliente.nome.substring(0, 1)}"/></span>
                    </div>

                    <div class="profile-card">
                        <c:if test="${not empty sessionScope.cliente}">
                            <h4><c:out value="${sessionScope.cliente.nome}"/></h4>
                            <p>CPF: <c:out value="${sessionScope.cliente.cpf}"/></p>

                            <p>Agência: <c:out value="${sessionScope.cliente.agencia}"/></p>
                            <p>Conta: <c:out value="${sessionScope.cliente.contaBancaria}"/></p>

                            <hr>

                            <a href="cliente?acao=logout" class="logout-btn">
                                <i class="fa-solid fa-right-from-bracket"></i> Sair
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </header>

        <main>
            <section class="balance-section">
                <div class="card">
                    <div class="card-header">
                        <span>Saldo disponível</span>
                        <i class="fa-regular fa-eye"></i>
                    </div>
                    <p class="card-balance"><fmt:formatNumber value="${sessionScope.cliente.saldo}" type="currency" currencySymbol="R$ "/></p>
                </div>
                <div class="card">
                    <div class="card-header">
                        <span>Dinheiro investido</span>
                        <i class="fa-solid fa-chart-line"></i>
                    </div>
                    <p class="card-balance"><fmt:formatNumber value="${sessionScope.cliente.saldoInvestido}" type="currency" currencySymbol="R$ "/></p>
                </div>
            </section>

          <%-- Em src/main/webapp/home.jsp --%>
            <section class="actions-section">
                <nav class="quick-actions">
                    
                    <a href="transferencia.jsp" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-money-bill-transfer"></i></div>
                        <span>Transferir</span>
                    </a>

                    <a href="#" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-piggy-bank"></i></div>
                        <span>Investir</span>
                    </a>

                    <a href="cliente?acao=verExtrato" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-receipt"></i></div>
                        <span>Extrato</span>
                    </a>
                    
                    <a href="deposito.jsp" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-circle-down"></i></div>
                        <span>Depositar</span>
                    </a>

                  
                    <a href="saque.jsp" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-circle-up"></i></div>
                        <span>Sacar</span>
                    </a>


                </nav>
            </section>
            
            <section class="transactions-section">
                <h2>Últimas movimentações</h2>
                <ul class="transaction-list card">
                    <c:forEach var="movimentacoes" items="${sessionScope.ultimasMovimentacoes}">
                        <li class="transaction-item">
                            <div class="transaction-details">
                                <c:choose>
                                    <c:when test="${movimentacoes.tipo == 'TRANSFERENCIA_ENVIADA'}">
                                        <p class="descricao">
                                            Transferência enviada para <strong>${movimentacoes.nomeDestinatario}</strong>
                                        </p>
                                    </c:when>

                                    <c:when test="${movimentacoes.tipo == 'TRANSFERENCIA_RECEBIDA'}">
                                        <p class="descricao">
                                            Transferência recebida de <strong>${movimentacoes.nomeRemetente}</strong>
                                        </p>
                                    </c:when>

                                    <c:when test="${movimentacoes.tipo == 'SAQUE'}">
                                        <p class="descricao">
                                            <strong>Saque</strong>
                                        </p>
                                    </c:when>
                                        
                                    <c:when test="${movimentacoes.tipo == 'DEPOSITO'}">
                                        <p class="descricao">
                                            <strong>Depósito</strong>
                                        </p>
                                    </c:when>
                                    <%-- Outros casos como DEPOSITO, SAQUE, etc. --%>
                                </c:choose>
                                <p class="transaction-date"><fmt:formatDate value="${movimentacoes.createdAt}" pattern="dd MMM, yyyy" /></p>
                            </div>
                            <div class="transaction-amount">
                                <p class="amount ${movimentacoes.valor < 0 ? 'negative' : 'positive'}">
                                    ${movimentacoes.valor >= 0 ? '+ ' : ''}<fmt:formatNumber value="${movimentacoes.valor}" type="currency" currencySymbol="R$ "/>
                                </p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
        </main>
    </div>

</body>
</html>
