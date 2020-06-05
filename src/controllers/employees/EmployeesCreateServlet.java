package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
import utility.WB;;
/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_CREATE)
public class EmployeesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String previousToken = (String)request.getParameter(WB.K_TOKEN);
        String currentToken = request.getSession().getId();

        //CSRF対策
        if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
          // 前ページ より 入力内容 を取得、Employee インスタンスの作成
          String code =request.getParameter(WB.K_CODE);
          String name = request.getParameter(WB.K_NAME);
          String _plainPassword = request.getParameter(WB.K_PLAIN_PASSWORD);
          String password = StringEncryptor.makeEncryptedPassword(_plainPassword);
          int adminFlag = Integer.parseInt(request.getParameter(WB.K_ADMIN_FLAG));
          Employee employee = new Employee(code, name, password, adminFlag);

          // validation を行う
          List<String> errorMessages = employee.performValidation4NewRegistration();

          if(errorMessages.size() > 0) {
            // 入力内容に不具合があるため errorMessages とともに new.jsp へ転送
            request.setAttribute(WB.K_TOKEN, currentToken);
            request.setAttribute(WB.K_EMPLOYEE, employee);
            request.setAttribute(WB.K_ERRORS, errorMessages);

            request.getRequestDispatcher(WB.PATH_EMPLOYEE_NEW_JSP).forward(request, response);
          } else {
            // DBへ登録
            DBHandler.putANewEmployee2DB(employee);

            // flush を設定し、index ページへリダイレクト
            request.getSession().setAttribute(WB.K_FLUSH, "登録が完了しました。");
            response.sendRedirect(request.getContextPath() + WB.PATH_EMPLOYEE_INDEX);
          }
      }
    }
}