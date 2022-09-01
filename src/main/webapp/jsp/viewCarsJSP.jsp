<%@ page import="Autopark.DTO.VehiclesDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: LizaSN
  Date: 18.08.2022
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотр машин</title>
    <link rel = stylesheet href = "../resources/css/style.css" />
</head>
<body>
<div class = "center-flex-full-vh">
    <div class="vertical-center">
        <%
            List<VehiclesDto> dtoList = (List<VehiclesDto>) request.getAttribute("cars");

            Set<String> uniqTypes =
                    dtoList.stream().map(VehiclesDto::getTypeName).collect(Collectors.toSet());
            Set<String> uniqModels =
                    dtoList.stream().map(VehiclesDto::getModel).collect(Collectors.toSet());
            Set<String> uniqColors =
                    dtoList.stream().map(VehiclesDto::getColor).collect(Collectors.toSet());
            Set<String> uniqEngineNames =
                    dtoList.stream().map(VehiclesDto::getEngineName).collect(Collectors.toSet());
            Set<String> uniqWeights =
                    dtoList.stream().map(x -> String.valueOf(x.getWeight())).collect(Collectors.toSet());
            Set<String> uniqYears =
                    dtoList.stream().map(x -> String.valueOf(x.getYear())).collect(Collectors.toSet());

            AtomicReference<Predicate<VehiclesDto>> filter = new AtomicReference<>(vehiclesDto -> true);

            Optional.ofNullable(request.getParameter("type")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> vehiclesDto.getTypeName().equals(s)));
            });
            Optional.ofNullable(request.getParameter("model")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> vehiclesDto.getModel().equals(s)));
            });
            Optional.ofNullable(request.getParameter("color")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> vehiclesDto.getColor().equals(s)));
            });
            Optional.ofNullable(request.getParameter("engine")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> vehiclesDto.getEngineName().equals(s)));
            });
            Optional.ofNullable(request.getParameter("weight")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> String.valueOf(vehiclesDto.getWeight()).equals(s)));
            });
            Optional.ofNullable(request.getParameter("year")).filter(s -> !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehiclesDto -> String.valueOf(vehiclesDto.getYear()).equals(s)));
            });

            dtoList = dtoList.stream().filter(filter.get()).collect(Collectors.toList());
            %>
            <a class="ml-20" href="/">На главную</a>
            <a class="ml-20" href="/viewCars">Очистить фильтры</a>
            <br />
            <br />
            <hr />
            <br />
            <table>
                <caption>Машины</caption>
                <tr>
                    <th>Тип</th>
                    <th>Модель</th>
                    <th>Номер</th>
                    <th>Масса</th>
                    <th>Дата</th>
                    <th>Цвет</th>
                    <th>Модель двигателя</th>
                    <th>Пробег</th>
                    <th>Бак</th>
                    <th>&#x1F4CB</th>
                </tr>
                <%if (dtoList.size() == 0) {%>
                <tr><td colospan = "10">
                    Нет машин, соответствующих параметрам</td></tr>
                <%}%>
                <%
                    for (VehiclesDto dto : dtoList) {
                    %>
                <tr>
                    <td><%=dto.getTypeName()%></td>
                    <td><%=dto.getModel()%></td>
                    <td><%=dto.getStateNumber()%></td>
                    <td><%=dto.getWeight()%></td>
                    <td><%=dto.getYear()%></td>
                    <td><%=dto.getColor()%></td>
                    <td><%=dto.getEngineName()%></td>
                    <td><%=dto.getMileage()%></td>
                    <td><%=dto.getTankCapacity()%></td>
                    <td><a href="<%="/info?id=" + dto.getId()%>">&#x1F4C3</a></td>
                </tr>
                <%}%>
            </table>
        <br />
        <div>
            <hr />
            <br />
            <form method="get" action="/viewCars" class="flex">
                <div class="ml-20">
                    <p>Тип</p>
                    <select name="type">
                        <option value=""
                                <%=request.getParameter("type") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqTypes) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("type") != null &&
                                        s.equals(request.getParameter("type")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Модель</p>
                    <select name="model">
                        <option value=""
                                <%=request.getParameter("model") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqModels) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("model") != null &&
                                        s.equals(request.getParameter("model")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Двигатель</p>
                    <select name="engine">
                        <option value=""
                                <%=request.getParameter("engine") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqEngineNames) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("engine") != null &&
                                        s.equals(request.getParameter("engine")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Цвет</p>
                    <select name="color">
                        <option value=""
                                <%=request.getParameter("color") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqColors) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("color") != null &&
                                        s.equals(request.getParameter("color")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Вес</p>
                    <select name="weight">
                        <option value=""
                                <%=request.getParameter("weight") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqWeights) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("weight") != null &&
                                        s.equals(request.getParameter("weight")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Год</p>
                    <select name="year">
                        <option value=""
                                <%=request.getParameter("year") == null ? "selected":""%>>Не выбрано</option>
                        <%for (String s : uniqYears) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("year") != null &&
                                        s.equals(request.getParameter("year")) ? "selected":"")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <button class="ml-20" type="submit">Выбрать</button>
            </form>
            <br />
            <hr />
        </div>
    </div>
</div>
</body>
</html>