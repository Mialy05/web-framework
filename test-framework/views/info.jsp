<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Info</title>
</head>
<body>
    <%@page import="app.models.objet.Emp"%>
    <% Emp e = (Emp)request.getAttribute("emp"); %>

    Numero: <%= request.getAttribute("numero") %>
    <br>
    Nom: <%= e.getNom() %>
    Adresse: <%= e.getAdresse() %>
    Naissance: <%= e.getNaissance() %>
    Nombre enfants: <%= e.getEnfant() %>

</body>
</html>