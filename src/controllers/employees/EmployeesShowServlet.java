package controllers.employees;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utility.DBConnector;
import utility.DBHandler;
import utility.WB;

/**
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_SHOW)
public class EmployeesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int employeeID = Integer.parseInt(request.getParameter(WB.K_EMPLOYEE_ID));
      Employee employee = DBHandler.getEmployee(employeeID);
      request.setAttribute(WB.K_EMPLOYEE, employee);

      request.getRequestDispatcher(WB.PATH_EMPLOYEE_SHOW_JSP).forward(request, response);
    }

}

