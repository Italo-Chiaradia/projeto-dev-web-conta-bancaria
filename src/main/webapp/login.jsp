<%--
    Document   : login
    Created on : 1 de jul. de 2025, 20:23:32
    Author     : italo (Versão estilizada por Gemini)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bankly - Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="./assets/icon-bankly.png" />
    <link rel="stylesheet" href="./assets/css/styles.css">
    <link rel="stylesheet" href="./assets/css/cadastro.css">
    <style>
        .register-link {
            display: block;
            text-align: center;
            margin-top: 25px;
            color: #820AD1;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="image-section">
             <img src="https://plus.unsplash.com/premium_photo-1661752131478-833305dbcbfa?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="Arte abstrata representando tecnologia e finanças">
        </div>

        <div class="form-section">
            <div class="form-container">
                
                <h1>Faça seu login</h1>

                <%-- Tag para exibir mensagens de erro vindas do Servlet --%>
                <c:if test="${not empty erro}">
                    <div class="error-message">
                        ${erro}
                    </div>
                </c:if>

                <form action="cliente?acao=login" method="post">
                    <div class="input-group">
                        <input id="cpf" name="cpf" type="text" required />
                        <label for="cpf">CPF</label>
                    </div>
                    <div class="input-group">
                        <input id="senha" name="senha" type="password" required />
                        <label for="senha">Senha</label>
                    </div>
                    <button type="submit" class="submit-btn">Entrar</button>
                </form>

                <a href="index.jsp" class="register-link">Não tem uma conta? Crie agora</a>
            </div>
        </div>
    </div>

</body>
</html>