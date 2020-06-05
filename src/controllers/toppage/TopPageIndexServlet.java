package controllers.toppage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utility.DBHandler;
import utility.FlushHandler;
import utility.WB;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet(WB.PATH_INDEX)
public class TopPageIndexServlet extends HttpServlet {
  private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
    }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Employee loginEmployee = (Employee)request.getSession().getAttribute(WB.K_LOGIN_EMPLOYEE);

    int page = 1;
    try{
        page = Integer.parseInt(request.getParameter(WB.K_PAGE));
    } catch(NumberFormatException e) { }
    request.setAttribute(WB.K_PAGE, page);

    List<Report> reports = DBHandler.getReports(loginEmployee, page);
    request.setAttribute(WB.K_REPORTS, reports);

    long reportCount = DBHandler.getReportCount(loginEmployee);
    request.setAttribute(WB.K_REPORT_COUNT, reportCount);

    FlushHandler.redirectFlushFromRequestToResponce(request, response);

    request.getRequestDispatcher(WB.PATH_INDEX_JSP).forward(request, response);
  }
}
