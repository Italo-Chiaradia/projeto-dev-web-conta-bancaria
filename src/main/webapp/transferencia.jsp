<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Transferência</title>
    <link rel="icon" type="image/x-icon" href="./assets/icon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/home.css">
    
    <link rel="stylesheet" href="assets/css/transferir.css">

</head>
<body>
    <div class="dashboard-container">
        
        <c:import url="header.jsp"/>
        
        <main>
            <div class="form-container">
                <h2>Realizar Transferência</h2>
                <p class="subtitle">Informe os dados da conta de destino e o valor.</p>

                <div class="card">
                    <form action="cliente?acao=realizarTransferencia" method="POST" class="form-layout">
                        
                        <c:if test="${not empty sucesso}">
                            <div class="alert alert-success">${sucesso}</div>
                        </c:if>
                        <c:if test="${not empty erro}">
                            <div class="alert alert-danger">${erro}</div>
                        </c:if>

                        <div class="form-group-inline">
                            <div class="form-group">
                                <label for="agencia">Agência</label>
                                <input type="text" id="agencia" name="agencia" placeholder="0001" value="0001" required>
                            </div>
                            <div class="form-group">
                                <label for="conta">Número da Conta</label>
                                <input type="text" id="conta" name="conta" placeholder="123456" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="valor">Valor (R$)</label>
                            <input type="text" id="valor" name="valor" placeholder="100,00" required>
                        </div>

                        <div class="form-actions">
                             <a href="home.jsp" class="btn btn-secondary">Cancelar</a>
                             <button type="submit" class="btn btn-primary">Transferir</button>
                        </div>
                    </form>
                </div>

                <a href="home.jsp" class="back-link">← Voltar para a página principal</a>
                
            </div>
        </main>
    </div>
</body>
</html>