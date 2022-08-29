package Autopark.Servlets;

import Autopark.Facade.DtoService;
import Autopark.Infrastructure.core.impl.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {
    DtoService dtoService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", dtoService.getTypes());
        getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp").forward(req, resp);
    }
}