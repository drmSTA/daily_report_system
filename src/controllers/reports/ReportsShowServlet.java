package controllers.reports;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utility.DBHandler;
import utility.WB;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet(WB.PATH_REPORT_SHOW)
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int reportID = Integer.parseInt(request.getParameter(WB.K_REPORT_ID));
      Report report = DBHandler.getReport(reportID);
      request.setAttribute(WB.K_REPORT, report);

      request.setAttribute(WB.K_TOKEN, request.getSession().getId());

      request.getRequestDispatcher(WB.PATH_REPORT_SHOW_JSP).forward(request, response);
    }

}
