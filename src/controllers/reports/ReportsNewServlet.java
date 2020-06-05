package controllers.reports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utility.WB;

/**
 * Servlet implementation class ReportsNewServlet
 */
@WebServlet(WB.PATH_REPORT_NEW)
public class ReportsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WB.K_TOKEN, request.getSession().getId());

        Employee employee = (Employee) request.getSession().getAttribute(WB.K_LOGIN_EMPLOYEE);

        Report report = new Report(employee);
        request.setAttribute(WB.K_REPORT , report);

        request.getRequestDispatcher(WB.PATH_REPORT_NEW_JSP).forward(request, response);
    }

}