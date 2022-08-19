package Autopark.Servlets;

import Autopark.DTO.VehiclesDto;
import Autopark.Facade.DtoService;
import Autopark.Infrastructure.core.impl.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewReport")
public class ViewReportServlet extends HttpServlet {
    DtoService dtoService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VehiclesDto> vehiclesDtoList =  dtoService.getVehicles();

        double averageTax = CalculationUtils.calculateAverageTax(vehiclesDtoList);
        double averageIncome = CalculationUtils.calculateAverageIncome(vehiclesDtoList);
        double totalProfit = CalculationUtils.calculateTotalProfit(vehiclesDtoList);

        req.setAttribute("cars", vehiclesDtoList);
        req.setAttribute("averageTax", averageTax);
        req.setAttribute("averageIncome", averageIncome);
        req.setAttribute("totalProfit", totalProfit);

        getServletContext().getRequestDispatcher("/jsp/viewReportJSP.jsp").forward(req, resp);
    }
}