package Autopark.Servlets;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Facade.DtoService;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.Workroom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewPlannedDiagnostic")
public class ViewPlannedDiagnosticServlet extends HttpServlet {
    DtoService dtoService;
    Workroom workroom;
    EntityCollection entityCollection;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
        workroom = applicationContext.getObject(Workroom.class);
        entityCollection = applicationContext.getObject(EntityCollection.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("brokenCars", workroom.getRepairedVehiclesId(entityCollection.getVehiclesList()));
        req.setAttribute("cars", dtoService.getVehicles());
        getServletContext().getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp").forward(req, resp);
    }
}