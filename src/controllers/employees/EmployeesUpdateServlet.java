package controllers.employees;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utility.DBHandler;
import utility.StringEncryptor;
import utility.StringValidator;
import utility.WB;

/**
 * Servlet implementation class EmployeesUpdateServlet
 */
@WebServlet(WB.PATH_EMPLOYEE_UPDATE)
public class EmployeesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 20200605L;

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

      //CSRF対策
      if(StringValidator.isUnderValidSession(previousToken, currentToken)) {
        // request session からパラメータを取得
        int employeeID = (int)(request.getSession().getAttribute(WB.K_EMPLOYEE_ID));
        String code =request.getParameter(WB.K_CODE);
        String name = request.getParameter(WB.K_NAME);
        String _plainPassword = request.getParameter(WB.K_PLAIN_PASSWORD);
        String password = StringEncryptor.makeEncryptedPassword(_plainPassword);
        int adminFlag = Integer.parseInt(request.getParameter(WB.K_ADMIN_FLAG));

        //更新する employee オブジェクトを新規作成
        Employee employeeUpdated = new Employee(employeeID, code, name, password, adminFlag);
        Employee employeeInDB = DBHandler.getEmployee(employeeID);

        ArrayList<String> errorMessages = new ArrayList<>();

        // 更新employee の validation 処理（DB employee と値が同じならvalidationは行わない)
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

        //validationの結果による条件分岐
        if(errorMessages.size() > 0) {
          // validation の結果エラー有　→　error message を表示、再入力
          request.setAttribute(WB.K_TOKEN, currentToken);
          request.setAttribute(WB.K_EMPLOYEE, employeeUpdated);
          request.setAttribute(WB.K_ERRORS, errorMessages);

          request.getRequestDispatcher(WB.PATH_EMPLOYEE_EDIT_JSP).forward(request, response);
        } else {
          // DB 中のデータを更新
          DBHandler.updateEmployee(employeeUpdated);

          // flush message
          request.getSession().setAttribute(WB.K_FLUSH, "登録が完了しました。");

          // session scope に保存していた id を削除
          request.getSession().removeAttribute(WB.K_EMPLOYEE_ID);

          response.sendRedirect(request.getContextPath() + WB.PATH_EMPLOYEE_INDEX);
          }
      }
    }
}