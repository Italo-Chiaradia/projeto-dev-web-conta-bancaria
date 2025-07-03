<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Saldo da Conta</title>
</head>
<body>
    <h2>Saldo da Conta Corrente</h2>
    <c:choose>
        <c:when test="${not empty saldo}">
            <p>Seu saldo atual é: <strong>R$ ${saldo}</strong></p>
        </c:when>
        <c:otherwise>
            <p style="color:red;">${erro != null ? erro : 'Erro ao consultar saldo. Tente novamente mais tarde.'}</p>
        </c:otherwise>
    </c:choose>
    <a href="index.jsp">Voltar para a página principal</a>
</body>
</html> 