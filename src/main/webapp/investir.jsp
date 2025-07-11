<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bankly - Investir</title>
    <link rel="icon" type="image/x-icon" href="./assets/icon-bankly.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/home.css">
    <link rel="stylesheet" href="assets/css/transferir.css"> <%-- Reutilizando o CSS dos formulários --%>
</head>
<body>
    <div class="dashboard-container">
        
        <c:import url="header.jsp"/>

        <main>
            <div class="form-container">
                <h2>Aplicar em Investimento</h2>
                <p class="subtitle">Informe o valor que deseja mover do seu saldo para a sua carteira de investimentos.</p>

                <div class="card">
                    <form action="cliente?acao=realizarInvestimento" method="POST" class="form-layout">
                        
                        <c:if test="${not empty sucesso}">
                            <div class="alert alert-success">${sucesso}</div>
                        </c:if>
                        <c:if test="${not empty erro}">
                            <div class="alert alert-danger">${erro}</div>
                        </c:if>

                        <div class="form-group">
                            <label for="valor">Valor da Aplicação (R$)</label>
                            <input type="text" id="valor" name="valor" placeholder="100,00" required>
                        </div>

                        <div class="form-actions">
                             <a href="home.jsp" class="btn btn-secondary">Cancelar</a>
                             <button type="submit" class="btn btn-primary">Investir</button>
                        </div>
                    </form>
                </div>
                
                <a href="home.jsp" class="back-link">← Voltar para a página principal</a>
            </div>
        </main>
    </div>
</body>
</html>