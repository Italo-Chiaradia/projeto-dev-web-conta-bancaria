<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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