<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des session</title>
</head>
<body>
    <%@page import = "java.util.HashMap, java.util.Map" %>
    <% HashMap<String, Object> variables = (HashMap<String,Object>)request.getAttribute("session"); %>
    <table>
        <% for (Map.Entry<String, Object> v : variables.entrySet()) { %>
            <tr>
                <td><%= v.getKey() %></td>
                <td><%= v.getValue() %></td>
            </tr>
        <% } %>
    </table>
    <div>
        <form action="/test-framework2/add-article.fwk" method="post">
            <input type="text" name="nom">
            <input type="number" name="nombre">
            <input type="submit" value="Ajouter">
        </form>
    </div>
    <div class="url">
        <a href="/test-framework2/clear-session.fwk">Vider</a>
    </div>
</body>
</html>