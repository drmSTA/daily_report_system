package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;
import utility.WB;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
  private static final long serialVersionUID = 20200605L;
  /**
   * Default constructor.
   */
  public LoginFilter() {
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      String contextPath = ((HttpServletRequest)request).getContextPath();
      String servletPath = ((HttpServletRequest)request).getServletPath();

      if(!servletPath.matches("/css.*")) {
        // CSSフォルダ内は認証処理から除外する
        HttpSession session = ((HttpServletRequest)request).getSession();

        // セッションスコープに保存された従業員（ログインユーザ）情報を取得
        Employee employee = (Employee)session.getAttribute(WB.K_LOGIN_EMPLOYEE);

        if(!servletPath.equals(WB.PATH_LOGIN)) {
          // ログイン画面以外について
          // ログアウトしている状態であれば
          // ログイン画面にリダイレクト
          if(employee == null) {
            ((HttpServletResponse)response).sendRedirect(contextPath + WB.PATH_LOGIN);
            return;
          }

          // 従業員管理の機能は管理者のみが閲覧できるようにする
          if(servletPath.matches("/employees.*") && employee.getAdmin_flag() == 0) {
            ((HttpServletResponse)response).sendRedirect(contextPath + "/");
            return;
          }
        } else {
          // ログイン画面について
          // ログインしているのにログイン画面を表示させようとした場合は
          // システムのトップページにリダイレクト
          if(employee != null) {
              ((HttpServletResponse)response).sendRedirect(contextPath + "/");
              return;
          }
      }
    }
  chain.doFilter(request, response);
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
  }

}
