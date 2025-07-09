<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Saque</title>
</head>
<body>
    <h2>Realizar Saque</h2>
    <c:if test="${not empty erro}">
        <p style="color:red;">${erro}</p>
    </c:if>
    <c:if test="${not empty sucesso}">
        <p style="color:green;">${sucesso}</p>
        <c:if test="${not empty novoSaldo}">
            <p>Seu novo saldo é: <strong>R$ ${novoSaldo}</strong></p>
        </c:if>
    </c:if>
    <form action="cliente?acao=realizarSaque" method="post">
        <label for="valor">Valor do saque:</label>
        <input type="text" id="valor" name="valor" required />
        <button type="submit">Sacar</button>
    </form>
    <a href="home.jsp">Voltar para a página principal</a>
</body>
</html> 