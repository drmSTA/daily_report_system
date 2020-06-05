package controllers.employees;

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
import utility.DBConnector;
import utility.DBHandler;
import utility.FlushHandler;
import utility.WB;


/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_INDEX)
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
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

        List<Employee> employees = DBHandler.getEmployees(page);
        request.setAttribute(WB.K_EMPLOYEES, employees);

        long employeeCount = DBHandler.getEmployeeCount();
        request.setAttribute(WB.K_EMPLOYEE_COUNT, employeeCount);

        FlushHandler.redirectFlushFromRequestToResponce(request, response);

        request.getRequestDispatcher(WB.PATH_EMPLOYEE_INDEX_JSP).forward(request, response);
    }
}