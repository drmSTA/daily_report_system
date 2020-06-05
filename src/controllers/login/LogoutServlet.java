package controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.WB;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(WB.PATH_LOGOUT)
public class LogoutServlet extends HttpServlet {
  private static final long serialVersionUID = 20200605L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(WB.K_LOGIN_EMPLOYEE);

        request.getSession().setAttribute(WB.K_FLUSH, "ログアウトしました。");
        response.sendRedirect(request.getContextPath() + WB.PATH_LOGIN);
    }
}