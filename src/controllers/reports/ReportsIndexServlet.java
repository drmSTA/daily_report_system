package controllers.reports;

import java.io.IOException;
import java.util.List;

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
import utility.FlushHandler;
import utility.WB;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet(WB.PATH_REPORT_INDEX)
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int page = 1;
      try{
          page = Integer.parseInt(request.getParameter(WB.K_PAGE));
      } catch(NumberFormatException e) { }
      request.setAttribute(WB.K_PAGE, page);

      List<Report> reports = DBHandler.getAllReports(page);
      request.setAttribute(WB.K_REPORTS, reports);

      long reportCount = DBHandler.getAllReportCount();
      request.setAttribute(WB.K_REPORT_COUNT, reportCount);

      FlushHandler.redirectFlushFromRequestToResponce(request, response);

      request.getRequestDispatcher(WB.PATH_REPORT_INDEX_JSP).forward(request, response);
    }

}