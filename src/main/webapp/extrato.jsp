<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Extrato</title>
    <link rel="stylesheet" href="extrato.jsp"
</head>
<body>
    <h2>Extrato da Conta</h2>
    <c:if test="${not empty erro}">
        <p style="color:red;">${erro}</p>
    </c:if>
    <c:choose>
        <c:when test="${not empty transacoes}">
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Data</th>
                    <th>Tipo</th>
                    <th>Valor (R$)</th>
                </tr>
                <c:forEach var="t" items="${transacoes}">
                    <tr>
                        <td><fmt:formatDate value="${t.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td>${t.tipo}</td>
                        <td>${t.valor}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>Nenhuma transação encontrada nesse período.</p>
        </c:otherwise>
    </c:choose>
    <a href="home.jsp">Voltar para a página principal</a>
</body>
</html>