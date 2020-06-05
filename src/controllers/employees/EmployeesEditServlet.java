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
import utility.Dumper;
import utility.WB;

/**
 * Servlet implementation class EmployeesEditServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_EDIT)
public class EmployeesEditServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesEditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int id = 0;
      try{
          id = Integer.parseInt(request.getParameter(WB.K_EMPLOYEE_ID));
      } catch(NumberFormatException e) { }
      request.getSession().setAttribute(WB.K_EMPLOYEE_ID, id);

      Employee employee = DBHandler.getEmployee(id);
      request.setAttribute(WB.K_EMPLOYEE, employee);

      request.setAttribute(WB.K_TOKEN, request.getSession().getId());

      request.getRequestDispatcher(WB.PATH_EMPLOYEE_EDIT_JSP).forward(request, response);
    }

}