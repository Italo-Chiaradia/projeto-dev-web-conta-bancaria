<%-- 
    Document   : home
    Created on : 8 de jul. de 2025, 18:39:18
    Author     : italo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <div class="user-avatar">
                    <span>I</span>
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
                    <p class="card-balance">R$ 4.250,75</p>
                </div>
                <div class="card">
                    <div class="card-header">
                        <span>Dinheiro investido</span>
                        <i class="fa-solid fa-chart-line"></i>
                    </div>
                    <p class="card-balance">R$ 12.800,00</p>
                </div>
            </section>

            <section class="actions-section">
                <nav class="quick-actions">
                    <a href="#" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-money-bill-transfer"></i></div>
                        <span>Transferir</span>
                    </a>
                    <a href="#" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-piggy-bank"></i></div>
                        <span>Investir</span>
                    </a>
                    <a href="#" class="action-item">
                        <div class="action-icon"><i class="fa-solid fa-receipt"></i></div>
                        <span>Extrato</span>
                    </a>
                </nav>
            </section>
            
            <section class="transactions-section">
                <h2>Últimas movimentações</h2>
                <ul class="transaction-list card">
                    <li class="transaction-item">
                        <div class="transaction-icon">
                            <i class="fa-solid fa-cart-shopping"></i>
                        </div>
                        <div class="transaction-details">
                            <p class="transaction-description">Compra no iFood</p>
                            <p class="transaction-date">07 JUL, 2025</p>
                        </div>
                        <div class="transaction-amount">
                            <p class="amount negative">- R$ 54,90</p>
                        </div>
                    </li>
                    <li class="transaction-item">
                        <div class="transaction-icon">
                            <i class="fa-solid fa-circle-dollar-to-slot"></i>
                        </div>
                        <div class="transaction-details">
                            <p class="transaction-description">Depósito via Pix</p>
                            <p class="transaction-date">06 JUL, 2025</p>
                        </div>
                        <div class="transaction-amount">
                            <p class="amount positive">+ R$ 800,00</p>
                        </div>
                    </li>
                    <li class="transaction-item">
                        <div class="transaction-icon">
                            <i class="fa-solid fa-user-group"></i>
                        </div>
                        <div class="transaction-details">
                            <p class="transaction-description">Transferência para Maria</p>
                            <p class="transaction-date">05 JUL, 2025</p>
                        </div>
                        <div class="transaction-amount">
                            <p class="amount negative">- R$ 250,00</p>
                        </div>
                    </li>
                </ul>
            </section>
        </main>
    </div>

</body>
</html>
