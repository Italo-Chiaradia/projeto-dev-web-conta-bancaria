<%--
    Document   : index
    Created on : 1 de jul. de 2025
    Author     : italo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="./assets/icon.png" />
    <link rel="stylesheet" href="./assets/css/styles.css">
    <link rel="stylesheet" href="./assets/css/cadastro.css">
</head>
<body>

    <div class="container">
        <div class="image-section">
            <img src="https://plus.unsplash.com/premium_photo-1661752131478-833305dbcbfa?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="Arte abstrata representando tecnologia e finanças">
        </div>
        <div class="form-section">
            <div class="form-container">
                
                <h1>Crie sua conta</h1>

                <c:if test="${not empty erro}">
                    <div class="error-message">
                        ${erro}
                    </div>
                </c:if>

                <form action="cliente?acao=cadastro" method="post">
                    <div class="input-group">
                        <input id="nome" name="nome" type="text" required />
                        <label for="nome">Nome completo</label>
                    </div>
                    <div class="input-group">
                        <input id="cpf" name="cpf" type="text" required />
                        <label for="cpf">CPF</label>
                    </div>
                    <div class="input-group">
                        <input id="senha" name="senha" type="password" required />
                        <label for="senha">Crie uma senha</label>
                    </div>
                    <div class="input-group">
                        <input id="senha-confirma" name="senha-confirma" type="password" required />
                        <label for="senha-confirma">Confirme a senha</label>
                    </div>
                    <button type="submit" class="submit-btn">Cadastrar</button>
                </form>

                 <a href="login.jsp" class="login-link">Já tem uma conta? Faça login</a>
            </div>
        </div>
    </div>

</body>
</html>