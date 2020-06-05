package utility;

public class WB {
  // WB is contracted phrase of "words bank", this manages common words which statically defined in source
  // for reducing "typo" risks.
  private static final long serialVersionUID = 20200601L;

  public static final String K_SALT = "salt";    //String

  // key management in request / response of HttpServlet (session)
  public static final String K_TOKEN = "_token";    //String
  public static final String K_HAS_ERROR = "hasError";    //boolean
  public static final String K_ERRORS = "errors";    //List<String>

  public static final String K_EMPLOYEE = "employee";    //Employee
  public static final String K_EMPLOYEES = "employees";    //List<Employee>
  public static final String K_EMPLOYEE_COUNT = "employeeCount";    //String
  public static final String K_EMPLOYEE_ID = "employeeID";    //int
  public static final String K_CODE = "code";    //String
  public static final String K_NAME = "name";    //String
  public static final String K_PASSWORD = "pass";    //String
  public static final String K_PLAIN_PASSWORD = "plainPassword";    //String
  public static final String K_ADMIN_FLAG = "admin_flag";

  public static final String K_LOGIN_EMPLOYEE = "loginEmployee"; //Employee
  public static final String K_REPORT = "report"; //Report
  public static final String K_REPORT_ID = "reportID";    //int
  public static final String K_REPORT_TITLE = "title"; //String
  public static final String K_REPORT_CONTENTS = "content"; //String
  public static final String K_REPORT_DATE = "report_date"; //String (Date)



  public static final String K_PAGE = "page";         // int
  public static final String K_REPORTS = "reports";//List<Report>
  public static final String K_REPORT_COUNT = "reportCount";//long
  public static final String K_FLUSH = "flush";         //


  // key (tag) management in html




  // path management
  public static final String PATH_INDEX = "/index.html";
  public static final String PATH_INDEX_JSP = "/WEB-INF/views/topPage/index.jsp";

  public static final String PATH_LOGIN = "/login";
  public static final String PATH_LOGIN_JSP = "/WEB-INF/views/login/login.jsp";
  public static final String PATH_LOGOUT = "/logout";

  public static final String PATH_EMPLOYEE_CREATE          = "/employees/create";
  public static final String PATH_EMPLOYEE_SHOW            = "/employees/show";
  public static final String PATH_EMPLOYEE_SHOW_JSP     = "/WEB-INF/views/employees/show.jsp";
  public static final String PATH_EMPLOYEE_DESTROY        = "/employees/destroy";
  public static final String PATH_EMPLOYEE_NEW               = "/employees/new";
  public static final String PATH_EMPLOYEE_NEW_JSP        = "/WEB-INF/views/employees/new.jsp";
  public static final String PATH_EMPLOYEE_INDEX            = "/employees/index";
  public static final String PATH_EMPLOYEE_INDEX_JSP     = "/WEB-INF/views/employees/index.jsp";
  public static final String PATH_EMPLOYEE_EDIT               = "/employees/edit";
  public static final String PATH_EMPLOYEE_EDIT_JSP       = "/WEB-INF/views/employees/edit.jsp";
  public static final String PATH_EMPLOYEE_UPDATE               = "/employees/update";
  public static final String PATH_EMPLOYEE_UPDATE_JSP       = "/WEB-INF/views/employees/edit.jsp";

  public static final String PATH_REPORT_CREATE               = "/reports/create";
  public static final String PATH_REPORT_NEW               = "/reports/new";
  public static final String PATH_REPORT_NEW_JSP               = "/WEB-INF/views/reports/new.jsp";
  public static final String PATH_REPORT_INDEX            = "/reports/index";
  public static final String PATH_REPORT_INDEX_JSP            = "/WEB-INF/views/reports/index.jsp";
  public static final String PATH_REPORT_EDIT               = "/reports/edit";
  public static final String PATH_REPORT_EDIT_JSP               = "/WEB-INF/views/reports/edit.jsp";

  public static final String PATH_REPORT_SHOW               = "/reports/show";
  public static final String PATH_REPORT_SHOW_JSP               = "/WEB-INF/views/reports/show.jsp";
  public static final String PATH_REPORT_UPDATE               = "/reports/update";


}
