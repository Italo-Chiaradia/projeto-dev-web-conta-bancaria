<%-- 
    Document   : home
    Created on : 8 de jul. de 2025, 18:39:18
    Author     : italo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bankly - Home</title>

    <link rel="icon" type="image/png" href="URL_DO_SEU_ICONE_AQUI.png" />

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>
        /* --- Reset e Estilos Globais --- */
        :root {
            --purple: #820AD1;
            --light-purple: #f3e5f5;
            --dark-text: #111111;
            --gray-text: #666666;
            --background: #F0F1F5;
            --white: #FFFFFF;
            --green: #00A34D;
            --red: #E83D5A;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: var(--background);
            color: var(--dark-text);
        }

        a {
            text-decoration: none;
            color: inherit;
        }

        /* --- Estrutura Principal --- */
        .dashboard-container {
            width: 100%;
            display: flex;
            flex-direction: column;
        }

        /* --- Cabeçalho --- */
        header {
            background-color: var(--purple);
            padding: 25px 5%;
            color: var(--white);
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
        }

        .header-content h1 {
            font-size: 1.5rem;
            font-weight: 600;
        }

        .user-avatar {
            width: 50px;
            height: 50px;
            background-color: var(--light-purple);
            color: var(--purple);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1.5rem;
            font-weight: 600;
        }

        /* --- Conteúdo Principal --- */
        main {
            padding: 20px 5%;
            max-width: 1200px;
            margin: 0 auto;
            width: 100%;
        }

        /* --- Seção de Saldos --- */
        .balance-section {
            display: flex;
            flex-direction: column;
            gap: 20px;
            margin-bottom: 30px;
        }

        .card {
            background-color: var(--white);
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: var(--gray-text);
            font-size: 1rem;
            margin-bottom: 10px;
        }

        .card-header i {
            font-size: 1.2rem;
        }

        .card-balance {
            font-size: 2rem;
            font-weight: 600;
            color: var(--dark-text);
        }

        /* --- Seção de Ações Rápidas --- */
        .actions-section h2 {
            font-size: 1.2rem;
            margin-bottom: 15px;
            font-weight: 600;
        }
        
        .quick-actions {
            display: flex;
            gap: 15px;
            overflow-x: auto; /* Permite rolar horizontalmente em telas pequenas */
            padding-bottom: 15px;
        }

        .action-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 10px;
            text-align: center;
        }

        .action-icon {
            width: 70px;
            height: 70px;
            background-color: var(--light-purple);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1.5rem;
            color: var(--purple);
            transition: background-color 0.2s;
        }
        
        .action-item span {
            font-weight: 500;
            font-size: 0.9rem;
        }

        .action-item:hover .action-icon {
            background-color: #e9d8f5;
        }

        /* --- Seção de Movimentações --- */
        .transactions-section {
            margin-top: 30px;
        }

        .transactions-section h2 {
            font-size: 1.2rem;
            margin-bottom: 15px;
            font-weight: 600;
        }

        .transaction-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        
        .transaction-item {
            display: flex;
            align-items: center;
            padding: 15px;
            background-color: var(--white);
            border-radius: 8px;
        }
        
        .transaction-icon {
            font-size: 1.5rem;
            margin-right: 15px;
            color: var(--gray-text);
        }
        
        .transaction-details {
            flex-grow: 1;
        }
        
        .transaction-details p {
            line-height: 1.4;
        }
        
        .transaction-description {
            font-weight: 500;
        }
        
        .transaction-date {
            font-size: 0.8rem;
            color: var(--gray-text);
        }

        .transaction-amount {
            font-weight: 600;
            font-size: 1rem;
        }
        
        .amount.positive { color: var(--green); }
        .amount.negative { color: var(--red); }
        
    </style>
</head>
<body>

    <div class="dashboard-container">
        <header>
            <div class="header-content">
                <div class="welcome-message">
                    <h1>Olá, Italo</h1>
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
