<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bankly - Extrato</title>

    <link rel="icon" type="image/x-icon" href="./assets/icon-bankly.png" />

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/home.css">
    <link rel="stylesheet" href="assets/css/extrato.css">
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
            <div class="main-container">
                <section class="transactions-section">
                    <h2>Extrato da Conta</h2>

                    <c:if test="${not empty erro}">
                        <p style="color:red;">${erro}</p>
                    </c:if>

                    <div class="card"> <%-- Adiciona o card para envolver a tabela --%>
                        <c:choose>
                            <c:when test="${not empty transacoes}">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Data</th>
                                            <th>Tipo</th>
                                            <th>Valor</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="t" items="${transacoes}">
                                            <tr>
                                                <td><fmt:formatDate value="${t.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td>${t.tipo}</td>
                                                <%-- Lógica para adicionar classe 'positive' ou 'negative' --%>
                                                <td class="transaction-amount ${t.valor > 0 ? 'positive' : 'negative'}">
                                                    <fmt:formatNumber value="${t.valor}" type="currency" currencySymbol="R$ "/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="info-msg">Nenhuma transação encontrada.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <a href="home.jsp" class="back-link">← Voltar para a página principal</a>
                </section>
            </div>
</main>
    </div>
</body>
</html>
