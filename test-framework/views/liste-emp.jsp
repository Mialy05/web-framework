<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <%@page import="app.models.objet.Emp, java.util.Vector"%>
    <%
        Vector<Emp> employes = (Vector<Emp>)request.getAttribute("employes");
    %>
    <h1>Liste des employés</h1>
    <table style="border-collapse: collapse; width: 50%; text-align: left; margin: auto;" >
        <tr style="background-color: aliceblue;" >
            <th style="border-bottom: 1px solid black; padding: 1%;" >Numéro</th>
            <th style="border-bottom: 1px solid black; padding: 1%;" >Nom</th>
        </tr>
        <% for(Emp e : employes) { %>
            <tr>
                <td style="border-bottom: 1px solid black; padding: 1%;" ><%= e.getId() %></td>
                <td style="border-bottom: 1px solid black; padding: 1%;" ><%= e.getNom() %></td>
            </tr>
        <% } %>
    </table>
    <div class="url">
        <p>
            <a href="/test-framework/emp-form.fwk">Ajouter employe</a>
        </p>
        <p>
            <a href="/test-framework/logout.fwk">Se déconnecter</a>
        </p>
    </div>
</body>
</html>