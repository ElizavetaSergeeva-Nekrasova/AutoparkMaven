package Autopark.Servlets;

import Autopark.Facade.DtoService;
import Autopark.Infrastructure.core.impl.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {
    DtoService dtoService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("cars", dtoService
                .getVehicles()
                .stream()
                .filter(vehiclesDto -> id == vehiclesDto.getId()).collect(Collectors.toList()));
        req.setAttribute("rents", dtoService
                .getRents()
                .stream()
                .filter(rentsDto -> id == rentsDto.getVehicleId()).collect(Collectors.toList()));
        getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp").forward(req, resp);
    }
}