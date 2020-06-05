package controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utility.DBHandler;
import utility.FlushHandler;
import utility.StringValidator;
import utility.WB;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(WB.PATH_LOGIN)
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    // show login page for first visiting
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WB.K_TOKEN, request.getSession().getId());
        request.setAttribute(WB.K_HAS_ERROR, false);
        FlushHandler.redirectFlushFromRequestToResponce(request, response);

        request.getRequestDispatcher(WB.PATH_LOGIN_JSP).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    // perform login action
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String code = request.getParameter(WB.K_CODE);
      String plainPassword = request.getParameter(WB.K_PLAIN_PASSWORD);

      Employee employee = null;

      if(!StringValidator.areEmpty(code, plainPassword)){
        // Database access is only acceptable for both of id and password which is not empty
        employee = DBHandler.getEmployee(code, plainPassword);
      }

      if(employee == null){
        // login fault for (1) invalid id and/or passwords (2) no such user found in DB
        request.setAttribute(WB.K_TOKEN, request.getSession().getId());
        request.setAttribute(WB.K_HAS_ERROR, true);
        request.setAttribute(WB.K_CODE, code);

        request.getRequestDispatcher(WB.PATH_LOGIN_JSP).forward(request, response);
      }else{
        // login succeed
        request.getSession().setAttribute(WB.K_LOGIN_EMPLOYEE, employee);
        request.getSession().setAttribute(WB.K_FLUSH, "ログインしました。");
        response.sendRedirect(request.getContextPath() + "/");
      }
    }
}

