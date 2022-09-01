<%@ page import="Autopark.DTO.VehiclesDto" %>
<%@ page import="java.util.List" %>
<%@ page import="Autopark.Entity.Rents" %>
<%@ page import="Autopark.DTO.RentsDto" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.DecimalFormatSymbols" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: LizaSN
  Date: 18.08.2022
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотр информации о конкретной машине</title>
    <link rel = stylesheet href = "../resources/css/style.css" />
</head>
<body>
<div class = "center-flex-full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">На главную</a>
        <a class="ml-20" href="/viewCars">Назад</a>
        <br />
        <br />
        <hr />
        <br />
        <table>
            <caption>Информация о машине</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Расход</th>
                <th>Коэфф налога</th>
                <th>km на полный бак</th>
            </tr>
            <%
                List<VehiclesDto> carsDtoList = (List<VehiclesDto>) request.getAttribute("cars");
                List<RentsDto> rentsDtoList = (List<RentsDto>) request.getAttribute("rents");

                VehiclesDto vehiclesDto = carsDtoList.get(0);
            %>
            <tr>
                <td><%=vehiclesDto.getTypeName()%></td>
                <td><%=vehiclesDto.getModel()%></td>
                <td><%=vehiclesDto.getStateNumber()%></td>
                <td><%=vehiclesDto.getWeight()%></td>
                <td><%=vehiclesDto.getYear()%></td>
                <td><%=vehiclesDto.getColor()%></td>
                <td><%=vehiclesDto.getEngineName()%></td>
                <td><%=vehiclesDto.getMileage()%></td>
                <td><%=vehiclesDto.getTankCapacity()%></td>
                <td><%=new DecimalFormat("#0.0",  new DecimalFormatSymbols(Locale.US)).format(vehiclesDto.getPer100Kilometers())%></td>
                <td><%=vehiclesDto.getCoefTaxes()%></td>
                <td><%=new DecimalFormat("#0.0",  new DecimalFormatSymbols(Locale.US)).format(vehiclesDto.getMaxKilometers())%></td>
            </tr>
        </table>
        <p> Налог за месяц: <strong>
            <%=new DecimalFormat("#0.0",  new DecimalFormatSymbols(Locale.US)).format(vehiclesDto.getTax())%>
        </strong> </p>
        <br />
        <hr />
        <br />
        <table>
            <caption>Информация об аренде</caption>
            <tr>
                <th>Дата</th>
                <th>Стоимость</th>
            </tr>
            <%for (RentsDto rentsDto : rentsDtoList) {
            %>
            <tr>
                <td><%=rentsDto.getDate()%></td>
                <td><%=rentsDto.getCost()%></td>
            </tr>
            <%}%>
        </table>
        <p> Cумма: <strong>
            <%=new DecimalFormat("#0.0",  new DecimalFormatSymbols(Locale.US)).format(vehiclesDto.getIncome())%>
        </strong></p>
        <p> Доход: <strong>
            <%=new DecimalFormat("#0.0",  new DecimalFormatSymbols(Locale.US)).format(vehiclesDto.getProfit())%>
        </strong></p>
    </div>
</div>
</body>
</html>