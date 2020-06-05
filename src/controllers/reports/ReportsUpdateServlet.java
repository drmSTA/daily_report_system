package controllers.reports;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

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
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet(WB.PATH_REPORT_UPDATE)
public class ReportsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsUpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String previousToken = (String)request.getParameter(WB.K_TOKEN);
      String currentToken = request.getSession().getId();

      if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
        int reportID = (Integer)(request.getSession().getAttribute(WB.K_REPORT_ID));

        Employee employee = (Employee) request.getSession().getAttribute(WB.K_LOGIN_EMPLOYEE);
        Date reportDate = Date.valueOf(request.getParameter(WB.K_REPORT_DATE));
        String title = request.getParameter(WB.K_REPORT_TITLE);
        String contents = request.getParameter(WB.K_REPORT_CONTENTS);

        Report reportUpdated = new Report(reportID, employee, reportDate, title, contents);
        Report reportInDB = DBHandler.getReport(reportID);

        ArrayList<String>errorMessages = new ArrayList<>();

        if(!reportInDB.getTitle().equals(reportUpdated.getTitle())){
          reportUpdated.validateTitle(errorMessages);
        }

        if(!reportInDB.getContent().equals(reportUpdated.getContent())){
          reportUpdated.validateContent(errorMessages);
        }

        if(errorMessages.size() > 0) {
          // if there is error(s) found, back to the page
          request.setAttribute(WB.K_TOKEN, currentToken);
          request.setAttribute(WB.K_REPORT, reportUpdated);
          request.setAttribute(WB.K_ERRORS, errorMessages);

          request.getRequestDispatcher(WB.PATH_REPORT_EDIT_JSP).forward(request, response);
        } else {
          DBHandler.updateReport(reportUpdated);
          request.getSession().setAttribute(WB.K_FLUSH, "登録が完了しました。");
          request.getSession().removeAttribute(WB.K_REPORT_ID);
          response.sendRedirect(request.getContextPath() + WB.PATH_REPORT_INDEX);
          }
      }
  }
}