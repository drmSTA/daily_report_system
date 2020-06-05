package controllers.reports;


import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utility.DBConnector;
import utility.DBHandler;
import utility.WB;

/**
 * Servlet implementation class ReportsEditServlet
 */
@WebServlet(WB.PATH_REPORT_EDIT)
public class ReportsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsEditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int reportID = Integer.parseInt(request.getParameter(WB.K_REPORT_ID));
      Report report = DBHandler.getReport(reportID);
      Employee loginEmployee = (Employee)request.getSession().getAttribute(WB.K_LOGIN_EMPLOYEE);

      // if login employee is report author
      if(report != null && loginEmployee.getId() == report.getEmployee().getId()) {
        request.setAttribute(WB.K_REPORT, report);
        request.setAttribute(WB.K_TOKEN, request.getSession().getId());
        request.getSession().setAttribute(WB.K_REPORT_ID, report.getId());
      }
      request.getRequestDispatcher(WB.PATH_REPORT_EDIT_JSP).forward(request, response);
    }

}