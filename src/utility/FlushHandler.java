package utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlushHandler {
  private static final long serialVersionUID = 20200601L;

  public static void redirectFlushFromRequestToResponce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if(request.getSession().getAttribute(WB.K_FLUSH) != null) {
        request.setAttribute(WB.K_FLUSH, request.getSession().getAttribute(WB.K_FLUSH));
        request.getSession().removeAttribute(WB.K_FLUSH);
    }
  }
}
