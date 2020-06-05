package utility;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import models.Employee;
import models.Report;

public class DBHandler {
  private static final long serialVersionUID = 20200601L;
  public static int ELEMENTS_UL_PER_PAGE = 15;

  public static Employee getEmployee(String code, String plainPassword){
    EntityManager entityManager = DBConnector.getEntityManager();
    String password = StringEncryptor.makeEncryptedPassword(plainPassword);
    Employee result = null;
    try{
      result = entityManager.createNamedQuery(Employee.CHECK_LOGIN_CODE_AND_PASSWORD, Employee.class)
          .setParameter(WB.K_CODE, code)
          .setParameter(WB.K_PASSWORD, password)
          .getSingleResult();
    }catch (NoResultException e){     }

    entityManager.close();
    return result;
  }

  public static Employee getEmployee(int employeeID){
    EntityManager entityManager = DBConnector.getEntityManager();
    Employee result = null;
    try{
      result = entityManager.find(Employee.class, employeeID);
    }catch (NoResultException e) {}

    entityManager.close();
    return result;
  }

  public static boolean isDuplicatedCode(String code){
    EntityManager entityManager = DBConnector.getEntityManager();
    long result = (long)entityManager.createNamedQuery(Employee.CHECK_REGISTERED_CODE, Long.class)
        .setParameter(WB.K_CODE, code)
        .getSingleResult();
    entityManager.close();
    if(result > 0) return true;
    return false;
  }

  public static void setEmployeeInactivation(int employeeID){
    EntityManager entityManager = DBConnector.getEntityManager();

    Employee employee = entityManager.find(Employee.class, employeeID);
    employee.setDelete_flag(Employee.INACTIVE_USER);
    employee.setUpdatedNow();;

    entityManager.getTransaction().begin();
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public static List<Employee> getEmployees(int page){
    EntityManager entityManager = DBConnector.getEntityManager();
    List<Employee> result = entityManager.createNamedQuery(Employee.GET_ALL_EMPLOYEES, Employee.class)
      .setFirstResult(ELEMENTS_UL_PER_PAGE * (page - 1))
      .setMaxResults(ELEMENTS_UL_PER_PAGE)
      .getResultList();
    entityManager.close();
    return result;
  }

  public static long getEmployeeCount(){
    EntityManager entityManager = DBConnector.getEntityManager();
    long result = (long)entityManager.createNamedQuery(Employee.GET_EMPLOYEE_COUNT, Long.class)
        .getSingleResult();
    entityManager.close();
    return result;
  }

  public static void putANewEmployee2DB(Employee employee){
    EntityManager entityManager = DBConnector.getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(employee);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public static void updateEmployee(Employee employee){
    Employee updated = employee;

    EntityManager entityManager = DBConnector.getEntityManager();

    Employee inDB= entityManager.find(Employee.class, (int)updated.getId());

    inDB.setCode(updated.getCode());
    inDB.setName(updated.getName());
    inDB.setPassword(updated.getPassword());
    inDB.setAdmin_flag(updated.getAdmin_flag());
    inDB.setUpdatedNow();

    entityManager.getTransaction().begin();
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public static List<Report> getReports(Employee employee, int page){
    EntityManager entityManager = DBConnector.getEntityManager();
    List<Report> result = entityManager.createNamedQuery(Report.GET_ALL_MY_REPORTS, Report.class)
      .setParameter(WB.K_EMPLOYEE, employee)
      .setFirstResult(ELEMENTS_UL_PER_PAGE * (page - 1))
      .setMaxResults(ELEMENTS_UL_PER_PAGE)
      .getResultList();
    entityManager.close();
    return result;
  }

  public static long getReportCount(Employee employee){
    EntityManager entityManager = DBConnector.getEntityManager();
    long result = (long)entityManager.createNamedQuery(Report.GET_MY_REPORT_COUNT, Long.class)
        .setParameter(WB.K_EMPLOYEE, employee)
        .getSingleResult();
    entityManager.close();
    return result;
  }

  public static void putANewReport2DB(Report report){
    EntityManager entityManager = DBConnector.getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(report);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public static Report getReport(int reportID){
    EntityManager entityManager = DBConnector.getEntityManager();
    Report result = entityManager.find(Report.class, reportID);
    entityManager.close();
    return result;
  }

  public static List<Report> getAllReports(int page){
    EntityManager entityManager = DBConnector.getEntityManager();
    List<Report> result = null;
    try{
      result = entityManager.createNamedQuery(Report.GET_ALL_REPORTS, Report.class)
          .setFirstResult(ELEMENTS_UL_PER_PAGE * (page - 1))
          .setMaxResults(ELEMENTS_UL_PER_PAGE)
          .getResultList();
    }catch (NoResultException e){     }
    entityManager.close();
    return result;
  }

  public static long getAllReportCount(){
    EntityManager entityManager = DBConnector.getEntityManager();
    long result = (long)entityManager.createNamedQuery(Report.GET_REPORT_COUNT, Long.class)
        .getSingleResult();
    entityManager.close();
    return result;
  }

  public static void updateReport(Report report){
    Report updated = report;

    EntityManager entityManager = DBConnector.getEntityManager();

    Report inDB= entityManager.find(Report.class, (int)updated.getId());

    inDB.setTitle(updated.getTitle());
    inDB.setContent(updated.getContent());
    inDB.setUpdatedNow();

    entityManager.getTransaction().begin();
    entityManager.getTransaction().commit();
    entityManager.close();
  }



}
