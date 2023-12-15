<%--
  Created by IntelliJ IDEA.
  User: mgn63ta
  Date: 13/12/23
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="template-parts/header.jsp"%>
    <link rel="stylesheet" href="css/personalArea.css">
</head>
<body>
<%@include file="template-parts/navbar.jsp"%>
<div class="container">
    <h1>Ciao *Nome e Cognome*</h1>
    <div class="dashboard">
        <div class="options">
            <button>INSERISCI RISTORANTE</button><br>
            <button>MODIFICA DATI PERSONALI</button><br>
            <button> I MIEI RISTORANTI</button>
            <button style="background:linear-gradient(to right,#777777,#777777)!important; color: black"> MODIFICA RISTORANTE <br>(Nessun ristorante trovato)</button><br><br>
        </div>
    </div>
</div>
</body>
</html>
