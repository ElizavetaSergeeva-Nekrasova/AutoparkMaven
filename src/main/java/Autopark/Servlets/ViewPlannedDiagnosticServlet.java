package Autopark.Servlets;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Facade.DtoService;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.ScheduledCheck;
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
    ScheduledCheck scheduledCheck;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
        workroom = applicationContext.getObject(Workroom.class);
        entityCollection = applicationContext.getObject(EntityCollection.class);
        scheduledCheck = applicationContext.getObject(ScheduledCheck.class);
        scheduledCheck.scheduledCheck(workroom, entityCollection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("brokenCars", scheduledCheck.getRepairedVehiclesId());
        req.setAttribute("cars", dtoService.getVehicles());
        getServletContext().getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp").forward(req, resp);
    }
}