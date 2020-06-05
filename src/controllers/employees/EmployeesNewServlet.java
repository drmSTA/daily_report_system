package controllers.employees;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utility.WB;

/**
 * Servlet implementation class EmployeesNewServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_NEW)
public class EmployeesNewServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // リクエストセッションから設定を取得
      request.setAttribute(WB.K_TOKEN, request.getSession().getId());
      request.setAttribute(WB.K_EMPLOYEE, new Employee());

      request.getRequestDispatcher(WB.PATH_EMPLOYEE_NEW_JSP).forward(request, response);
    }
}