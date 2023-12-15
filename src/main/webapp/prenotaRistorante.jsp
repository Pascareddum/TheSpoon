<%--
  Created by IntelliJ IDEA.
  User: mgn63ta
  Date: 13/12/23
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prenota</title>
    <link rel="stylesheet" href="css/prenotaRistorante.css">
</head>
<body>
<main class="cd__main">
    <!-- Start DEMO HTML (Use the following code into your project)-->
    <form class="booking-form" action="#" method="post">
        <div class="elem-group">
            <label for="name">Nome</label>
            <input type="text" id="name" name="visitor_name" placeholder="Carmine Gravino" pattern="[A-Z\\sa-z]{3,20}" required="">
        </div>
        <div class="elem-group">
            <label for="email">E-mail</label>
            <input type="email" id="email" name="visitor_email" placeholder="CarmineGravino@email.com" required="">
        </div>
        <div class="elem-group">
            <label for="phone">Cellulare</label>
            <input type="tel" id="phone" name="visitor_phone" placeholder="498-348-3872" pattern="(\\d{3})-?\\s?(\\d{3})-?\\s?(\\d{4})" required="">
        </div>
        <hr>
        <div class="elem-group inlined">
            <label for="adult">Adulti</label>
            <input type="number" id="adult" name="total_adults" placeholder="2" min="1" required="">
        </div>
        <div class="elem-group inlined">
            <label for="child">Bambini(Minori di 5 anni)</label>
            <input type="number" id="child" name="total_children" placeholder="2" min="0" required="">
        </div>
        <div class="elem-group inlined">
            <label for="check-date">Data</label>
            <input type="date" id="check-date" name="checkin" required="" min="2023-12-07">
        </div>
        <div class="elem-group inlined">
            <label for="hour">Ora</label>
            <input type="time" id="hour" name="check-hour" required="" min="">
        </div>
        <hr>

        <button type="submit">Prenota</button>
    </form>
</main>

</body>
</html>
