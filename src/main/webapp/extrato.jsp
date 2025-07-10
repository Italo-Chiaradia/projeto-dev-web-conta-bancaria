<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    
    
    <style>
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .section-header h2 {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--dark-text);
        }

        .back-link {
            font-size: 0.9rem;
            font-weight: 500;
            color: var(--purple);
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 12px;
            border-radius: 20px;
            transition: background-color 0.2s;
        }

        .back-link:hover {
            background-color: var(--light-purple);
        }

        /* --- Lista de Transações --- */
        /* O estilo para .transaction-list e .transaction-item pode ser o mesmo da home.css. 
           Se não tiver movido para o styles.css, pode duplicar aqui ou, idealmente, mover para o global. */
        .transaction-list {
            list-style: none;
            display: flex;
            flex-direction: column;
        }

        .transaction-item {
            display: flex;
            align-items: center;
            padding: 20px 0;
            border-bottom: 1px solid #f0f0f0; /* Linha separadora */
        }

        .transaction-item:last-child {
            border-bottom: none; /* Remove a linha do último item */
        }

        .transaction-icon {
            font-size: 1.4rem;
            margin-right: 20px;
            color: var(--gray-text);
            width: 30px;
            text-align: center;
        }

        .transaction-details {
            flex-grow: 1;
        }

        .transaction-details p {
            line-height: 1.4;
        }

        .transaction-description {
            font-weight: 500;
            color: var(--dark-text);
        }

        .transaction-date {
            font-size: 0.85rem;
            color: var(--gray-text);
        }

        .transaction-amount {
            font-weight: 600;
            font-size: 1rem;
            text-align: right;
        }

        .amount.positive { color: var(--green); }
        .amount.negative { color: var(--red); }

        /* --- Estilos da Paginação --- */
        .pagination {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #f0f0f0;
        }

        .pagination ul {
            list-style-type: none;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
        }

        .pagination li a {
            display: block;
            padding: 8px 14px;
            border-radius: 8px;
            font-weight: 500;
            font-size: 0.9rem;
            color: var(--gray-text);
            transition: all 0.2s ease-in-out;
        }

        .pagination li a:hover {
            background-color: var(--light-purple);
            color: var(--purple);
        }

        /* Estilo para a página ativa */
        .pagination li.active a {
            background-color: var(--purple);
            color: var(--white);
            font-weight: 600;
        }

        .pagination ul { list-style-type: none; display: flex; gap: 10px; }
        .pagination li a { text-decoration: none; padding: 5px 10px; border: 1px solid #ccc; }
        .pagination li.active a { background-color: #007bff; color: white; border-color: #007bff; }
    </style>
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
            <section class="transactions-section">
                <div class="section-header">
                    <h2>Extrato da Conta</h2>
                    <a href="home.jsp" class="back-link"><i class="fa-solid fa-arrow-left"></i> Voltar</a>
                </div>

                <c:if test="${not empty erro}">
                    <p class="error-message">${erro}</p>
                </c:if>

                <div class="card">
                    <ul class="transaction-list">
                        <c:forEach var="mov" items="${paginaExtrato.conteudo}">
                            <li class="transaction-item">
                                <div class="transaction-icon">
                                    <%-- Define o ícone com base no tipo de movimentação --%>
                                    <c:choose>
                                        <c:when test="${mov.tipo == 'DEPOSITO' || mov.tipo == 'TRANSFERENCIA_RECEBIDA'}">
                                            <i class="fa-solid fa-circle-dollar-to-slot"></i>
                                        </c:when>
                                        <c:when test="${mov.tipo == 'SAQUE'}">
                                            <i class="fa-solid fa-money-bill-wave"></i>
                                        </c:when>
                                        <c:when test="${mov.tipo == 'TRANSFERENCIA_ENVIADA'}">
                                            <i class="fa-solid fa-money-bill-transfer"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa-solid fa-dollar-sign"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="transaction-details">
                                    <p class="transaction-description">
                                        <c:choose>
                                            <%-- CASO 1: É uma transferência (enviada ou recebida) --%>
                                            <c:when test="${mov.tipo == 'TRANSFERENCIA_ENVIADA' || mov.tipo == 'TRANSFERENCIA_RECEBIDA'}">

                                                <%-- Se o ID do cliente na sessão for igual ao ID do remetente, significa que ELE enviou. --%>
                                                <%-- Então, mostramos PARA quem ele enviou. --%>
                                                <c:if test="${sessionScope.cliente.id == mov.idRemetente}">
                                                    Para: <c:out value="${mov.nomeDestinatario}"/>
                                                </c:if>

                                                <%-- Se o ID do cliente na sessão for igual ao ID do destinatário, significa que ELE recebeu. --%>
                                                <%-- Então, mostramos DE quem ele recebeu. --%>
                                                <c:if test="${sessionScope.cliente.id == mov.idDestinatario}">
                                                    De: <c:out value="${mov.nomeRemetente}"/>
                                                </c:if>

                                            </c:when>

                                            <%-- CASO 2: É qualquer outro tipo de movimentação (Depósito, Saque, etc.) --%>
                                            <c:otherwise>
                                                <c:out value="${mov.tipo}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p class="transaction-date">
                                        <%-- Supondo que o nome do campo de data seja 'data' --%>
                                        <fmt:formatDate value="${mov.createdAt}" pattern="dd MMM, yyyy" />
                                    </p>
                                </div>
                                <div class="transaction-amount">
                                    <p class="amount ${mov.valor > 0 ? 'positive' : 'negative'}">
                                        <fmt:formatNumber value="${mov.valor}" type="currency" currencySymbol="R$ "/>
                                    </p>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>

                    <%-- Controles de Paginação --%>
                    <c:if test="${paginaExtrato.totalPaginas > 1}">
                        <nav class="pagination">
                            <ul>
                                <c:if test="${paginaExtrato.paginaAtual > 1}">
                                    <li><a href="cliente?acao=verExtrato&pagina=${paginaExtrato.paginaAtual - 1}"><i class="fa-solid fa-chevron-left"></i></a></li>
                                </c:if>

                                <c:forEach var="i" begin="1" end="${paginaExtrato.totalPaginas}">
                                    <li class="${i == paginaExtrato.paginaAtual ? 'active' : ''}">
                                        <a href="cliente?acao=verExtrato&pagina=${i}">${i}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${paginaExtrato.paginaAtual < paginaExtrato.totalPaginas}">
                                    <li><a href="cliente?acao=verExtrato&pagina=${paginaExtrato.paginaAtual + 1}"><i class="fa-solid fa-chevron-right"></i></a></li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </section>
        </main>
    </div>
</body>
</html>
</html>
