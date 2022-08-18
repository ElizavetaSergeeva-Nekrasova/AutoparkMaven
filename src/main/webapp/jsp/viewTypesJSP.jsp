<%@ page import="Autopark.DTO.TypesDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %><%--
  Created by IntelliJ IDEA.
  User: LizaSN
  Date: 18.08.2022
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотр типов машин</title>
    <link rel = stylesheet href = "../resources/css/style.css" />
</head>
<body>
<div class = "center flex full-vh">
<div class="vertical-center">
    <a class="ml-20" href="/">На главную</a>
    <br />
    <br />
    <hr />
    <br />
    <%
        String sortKey = null;
        String order = null;
        if (request.getParameter("name") != null) {sortKey = "name";}
        if (request.getParameter("tax") != null) {sortKey = "tax";}
        if (request.getParameter("asc") != null) {order = "asc";}
        if (request.getParameter("desc") != null) {order = "desc";}
    %>
    <%if(sortKey != null ) {%>
    <%
        String clearPath = "/viewTypes";
        String ascPath = "?" + sortKey + "&asc";
        String descPath = "?" + sortKey + "&desc";
    %>
    <div>
        <a class = "ml-20" href = "<%=descPath%>">Сортировать по убыванию</a>
        <a class = "ml-20" href = "<%=ascPath%>">Сортировать по возрастанию</a>
        <a class = "ml-20" href = "<%=clearPath%>">Очистить параметры поиска</a>
    </div>
    <br />
    <hr />
    <br />
    <%}%>
    <table>
        <caption>Типы машин</caption>
        <tr>
            <th>Название</th>
            <th>Коэффициент дорожного налога</th>
        </tr>
        <%
            List<TypesDto> dtoList = (List<TypesDto>) request.getAttribute("types");
            Comparator<TypesDto> comparator = null;
            if (sortKey != null && sortKey.equals("name")) {
                comparator = Comparator.comparing(TypesDto -> TypesDto.getName());
            }
            if (sortKey != null && sortKey.equals("tax")) {
                comparator = Comparator.comparingDouble(TypesDto -> TypesDto.getCoefTaxes());
            }
            if (order != null && comparator != null && order.equals("desc")) {
                comparator = comparator.reversed();
            }
            if (comparator != null) {
                dtoList.sort(comparator);
            }
            for (TypesDto dto : dtoList) {
        %>
        <tr>
            <td><%=dto.getName()%></td>
            <td><%=dto.getCoefTaxes()%></td>
        </tr>
        <%}%>
    </table>
    <%if (dtoList.size() > 0) {%>
    <p> Минимальный коэффициент: <strong>
        <%=dtoList.stream().map(TypesDto::getCoefTaxes).min(Double::compare).get()%>
    </strong>
    </p>
    <p> Максимальный коэффициент: <strong>
        <%=dtoList.stream().map(TypesDto::getCoefTaxes).max(Double::compare).get()%>
    </strong>
    </p>
    <%}%>
    <br />
    <hr />
    <br />
    <div>
        <% if (request.getParameter("name") == null) {%><a class="ml-20" href="/viewTypes?name">Сортировать по названию</a><%}%>
        <% if (request.getParameter("tax") == null) {%><a class="ml-20" href="/viewTypes?tax">Сортировать по коэффициенту</a><%}%>
    </div>
</div>
</div>
</body>
</html>