package controllers.employees;


import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utility.DBConnector;
import utility.DBHandler;
import utility.StringValidator;
import utility.WB;

/**
 * Servlet implementation class EmployeesDestroyServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_DESTROY)
public class EmployeesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String previousToken = (String)request.getParameter(WB.K_TOKEN);
      String currentToken = request.getSession().getId();

      if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
        int id = (Integer)(request.getSession().getAttribute(WB.K_EMPLOYEE_ID));

        DBHandler.setEmployeeInactivation(id);
        request.getSession().setAttribute(WB.K_FLUSH, "削除が完了しました。");

        response.sendRedirect(request.getContextPath() + WB.PATH_EMPLOYEE_INDEX);
        }
    }

}