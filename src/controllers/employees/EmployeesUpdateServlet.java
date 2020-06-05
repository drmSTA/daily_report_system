package controllers.employees;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import utility.StringEncryptor;
import utility.StringValidator;
import utility.WB;

/**
 * Servlet implementation class EmployeesUpdateServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_UPDATE)
public class EmployeesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 20200601L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesUpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String previousToken = (String)request.getParameter(WB.K_TOKEN);
      String currentToken = request.getSession().getId();

      if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
        int employeeID = (int)(request.getSession().getAttribute(WB.K_EMPLOYEE_ID));

        String code =request.getParameter(WB.K_CODE);
        String name = request.getParameter(WB.K_NAME);
        String _plainPassword = request.getParameter(WB.K_PLAIN_PASSWORD);
        String password = StringEncryptor.makeEncryptedPassword(_plainPassword);
        int adminFlag = Integer.parseInt(request.getParameter(WB.K_ADMIN_FLAG));

        Employee employeeUpdated = new Employee(employeeID, code, name, password, adminFlag);
        Employee employeeInDB = DBHandler.getEmployee(employeeID);

        ArrayList<String> errorMessages = new ArrayList<>();

        if(!employeeInDB.getCode().equals(employeeUpdated.getCode())){
          employeeUpdated.validateCode(errorMessages);
        }

        if(!employeeInDB.getName().equals(employeeUpdated.getName())){
          employeeUpdated.validateName(errorMessages);
        }

        boolean isNewPasswordNotEmpty = !employeeUpdated.getPassword().equals(StringEncryptor.makeEncryptedPassword(""));
        if(isNewPasswordNotEmpty){
          employeeUpdated.validatePassword(errorMessages);
        }else{
          employeeUpdated.setPassword(employeeInDB.getPassword());
        }

        if(errorMessages.size() > 0) {
          // if there is error(s) found, back to the page
          request.setAttribute(WB.K_TOKEN, currentToken);
          request.setAttribute(WB.K_EMPLOYEE, employeeUpdated);
          request.setAttribute(WB.K_ERRORS, errorMessages);

          request.getRequestDispatcher(WB.PATH_EMPLOYEE_EDIT_JSP).forward(request, response);
        } else {
          DBHandler.updateEmployee(employeeUpdated);
          request.getSession().setAttribute(WB.K_FLUSH, "登録が完了しました。");
          request.getSession().removeAttribute(WB.K_EMPLOYEE_ID);
          response.sendRedirect(request.getContextPath() + WB.PATH_EMPLOYEE_INDEX);
          }
      }
    }
}