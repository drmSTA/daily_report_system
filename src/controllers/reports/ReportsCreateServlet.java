package controllers.reports;


import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utility.DBHandler;
import utility.StringValidator;
import utility.WB;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet(WB.PATH_REPORT_CREATE)
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String previousToken = (String)request.getParameter(WB.K_TOKEN);
      String currentToken = request.getSession().getId();

      if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
        Employee employee = (Employee)request.getSession().getAttribute(WB.K_LOGIN_EMPLOYEE);
        String _reportDate = request.getParameter(WB.K_REPORT_DATE);
        Date reportDate = StringValidator.isEmpty(_reportDate) ? new Date(System.currentTimeMillis()) : Date.valueOf(_reportDate);
        String title = request.getParameter(WB.K_REPORT_TITLE);
        String contents = request.getParameter(WB.K_REPORT_CONTENTS);

        Report report = new Report(employee, reportDate, title, contents);
        List<String> errorMessages = report.performValidation4NewRegistration();

        if(errorMessages.size() > 0) {
          // if there is error(s) found, back to the page
          request.setAttribute(WB.K_TOKEN, currentToken);
          request.setAttribute(WB.K_REPORT, report);
          request.setAttribute(WB.K_ERRORS, errorMessages);

          request.getRequestDispatcher(WB.PATH_REPORT_NEW_JSP).forward(request, response);
        } else {
          //
          DBHandler.putANewReport2DB(report);
          request.getSession().setAttribute(WB.K_FLUSH, "登録が完了しました。");
          response.sendRedirect(request.getContextPath() + WB.PATH_REPORT_INDEX);
        }
    }
  }
}
