<%--
  Created by IntelliJ IDEA.
  User: mgn63ta
  Date: 13/12/23
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>insertRistorante</title>
    <link rel="stylesheet" href="css/insertRistorante.css">
</head>
<body>
<main class="cd__main">
    <!-- Start DEMO HTML (Use the following code into your project)-->
    <form class="booking-form" action="#" method="post">
        <div class="elem-group">
            <label for="name">Nome</label>
            <input type="text" id="name" name="restaurant_name" placeholder="Pizzeria da Mario" pattern="[A-Z\\sa-z]{3,20}" required="">
        </div>
        <div class="elem-group">
            <label for="address">E-mail</label>
            <input type="text" id="address" name="restaurant_address" placeholder="Via Rossi 7,Caserta CE" required="">
        </div>
        <div class="elem-group">
            <label for="phone">Cellulare</label>
            <input type="tel" id="phone" name="restaurant_phone" placeholder="498-348-3872" pattern="(\\d{3})-?\\s?(\\d{3})-?\\s?(\\d{4})" required="">
        </div>
        <hr>
        <button type="submit">Inserisci immagine</button>
        <button style="margin-left: 150px" type="submit">Inserisci menù</button>
        <hr>

        <button style="margin-top:10px" type="submit">Prenota</button>
    </form>
</main>
</body>
</html>
