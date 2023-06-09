<%@page import="etu1834.framework.utils.FileUpload"  %>

<%
    FileUpload photo = (FileUpload)request.getAttribute("photo");
%>

<h4>Fichier uploadé</h4>
<p><%= photo.getName() %></p>
<p><%= photo.getFile().length %></p>
