<%--
    Document   : index
    Created on : 1 de jul. de 2025
    Author     : italo (Versão estilizada por Gemini)
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Criar Conta</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* --- Reset e Estilos Globais --- */
        * {
            box-sizing: border-box;
        }

        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
            background-color: #f4f4f4;
        }

        /* --- Layout Principal (2 Colunas) --- */
        .container {
            display: flex;
            width: 100vw;
            height: 100vh;
        }

        .form-section {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #fff;
            padding: 40px;
        }

        .image-section {
            flex: 1;
            background-image: url("https://images.pexels.com/photos/5474028/pexels-photo-5474028.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); /* <<< SUBSTITUA PELA SUA IMAGEM */
            background-size: cover;
            background-position: center;
        }

        /* --- Conteúdo do Formulário --- */
        .form-container {
            width: 100%;
            max-width: 380px;
        }

        .form-container h1 {
            font-size: 2.2rem;
            color: #333;
            margin-bottom: 30px;
            font-weight: 600;
        }

        .input-group {
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            font-size: 0.9rem;
            color: #555;
            margin-bottom: 8px;
        }

        .input-group input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .input-group input:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
        }

        /* --- Botão e Links --- */
        .submit-btn {
            width: 100%;
            padding: 15px;
            border: none;
            border-radius: 8px;
            background-color: #0d6efd; /* Azul do protótipo */
            color: white;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 10px;
        }

        .submit-btn:hover {
            background-color: #0b5ed7;
        }

        .login-link {
            display: block;
            text-align: center;
            margin-top: 25px;
            color: #555;
            text-decoration: none;
            font-size: 0.9rem;
        }
        
        .login-link:hover {
            text-decoration: underline;
        }
        
        /* --- Mensagem de Erro --- */
        .error-message {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 8px;
            color: #842029;
            background-color: #f8d7da;
            border-color: #f5c2c7;
            text-align: center;
        }

        /* --- Responsividade para Telas Menores --- */
        @media (max-width: 768px) {
            .container {
                flex-direction: column;
            }
            .image-section {
                display: none; /* Oculta a imagem em telas pequenas */
            }
            .form-section {
                width: 100%;
                min-height: 100vh;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="form-section">
            <div class="form-container">
                
                <h1>Criar Conta</h1>

                <c:if test="${not empty erro}">
                    <div class="error-message">
                        ${erro}
                    </div>
                </c:if>

                <form action="cliente?acao=cadastro" method="post">
                    <div class="input-group">
                        <label for="nome">Nome</label>
                        <input id="nome" name="nome" type="text" required />
                    </div>
                    <div class="input-group">
                        <label for="cpf">CPF</label>
                        <input id="cpf" name="cpf" type="text" required />
                    </div>
                    <div class="input-group">
                        <label for="senha">Senha</label>
                        <input id="senha" name="senha" type="password" required />
                    </div>
                    <div class="input-group">
                        <label for="senha-confirma">Confirmar Senha</label>
                        <input id="senha-confirma" name="senha-confirma" type="password" required />
                    </div>
                    <button type="submit" class="submit-btn">Cadastrar</button>
                </form>

                <a href="cliente?acao=consultarSaldo" class="login-link">Consultar Saldo</a>
                <a href="cliente?acao=depositoForm" class="login-link">Fazer Depósito</a>
                <a href="login.jsp" class="login-link">Fazer Login</a>
            </div>
        </div>
        <div class="image-section">
            </div>
    </div>

</body>
</html>