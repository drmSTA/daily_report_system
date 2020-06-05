package models;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import utility.DBHandler;
import utility.StringEncryptor;
import utility.StringValidator;

@Table(name = "employees")
@NamedQueries({
    @NamedQuery(
            name = Employee.GET_ALL_EMPLOYEES,
            query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"
            ),
    @NamedQuery(
            name = Employee.GET_EMPLOYEE_COUNT,
            query = "SELECT COUNT(e) FROM Employee AS e"

            ),
    @NamedQuery(
            name = Employee.CHECK_REGISTERED_CODE,
            query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"
            ),
    @NamedQuery(
            name = Employee.CHECK_LOGIN_CODE_AND_PASSWORD,
            query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass"
            )
})
@Entity
public class Employee {
     private static final long serialVersionUID = 20200601L;
     public static final String GET_ALL_EMPLOYEES = "getAllEmployees";
     public static final String GET_EMPLOYEE_COUNT = "getEmployeeCount";
     public static final String CHECK_REGISTERED_CODE = "checkRegisteredCode";
     public static final String CHECK_LOGIN_CODE_AND_PASSWORD = "checkLoginCodeAndPassword";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag; // 0 for general 1 for administrator
    public static final int TYPE_COMMON_EMPLOYEE = 0;
    public static final int TYPE_ADMINISTRATOR = 1;


    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag; // 0 for active 1 for inactive (deleted user)
    public static final int ACTIVE_USER = 0;
    public static final int INACTIVE_USER = 1;

    //=======================
    // constructor
    //=======================
    // constructor for new user (template)
    public Employee() {
      this(0, "","","",0);
      /* contents of initialized "no parameter" object are the same as below
      id = 0 (none user due to count for id starts from 1)
      code = "";
      name = "";
      password = "";
      adminFlag = 0;
      */
    }

    // constructor for new user (from input form)
    public Employee(String code, String name, String password, int adminFlag){
      this(0, code, name, password, adminFlag);
    }

    // constructor for update employee who already exists in DB
    public Employee(int id, String code, String name, String password, int adminFlag){
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      this.id = id;
      this.code = code;
      this.name = name;
      this.password = password;
      this.admin_flag = adminFlag;
      this.created_at = currentTime;
      this.updated_at = currentTime;
      this.delete_flag = Employee.ACTIVE_USER;
    }


    //=======================
    // setter / getter
    //=======================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at){
      this.updated_at = updated_at;
    }

    public void setUpdatedNow() {
        this.updated_at =  new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreated_at() {
      return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
      this.created_at = created_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getUserType(){
      switch (this.admin_flag) {
      case TYPE_ADMINISTRATOR:
        return "管理者";

      case TYPE_COMMON_EMPLOYEE:
      default:
        return "一般";
      }
    }

    // =======================
    // validation
    // =======================
    public List<String> performValidation4NewRegistration(){
      ArrayList<String> result = new ArrayList<>();
      this.validateName(result);
      this.validateCode(result);
      this.validatePassword(result);
      return result;
    }

    public void validateCode(ArrayList<String> errorMessages){
      if(StringValidator.isEmpty(this.code)){
        errorMessages.add("社員番号を入力してください");
      }

      if(DBHandler.isDuplicatedCode(this.code)){
        errorMessages.add("入力された社員番号の情報はすでに存在しています。");
      }
    }

    public void validateName(ArrayList<String> errorMessages){
      if(StringValidator.isEmpty(this.name)){
        errorMessages.add("氏名を入力してください");
      }
    }

    public void validatePassword(ArrayList<String> errorMessages){
      String password4empty = StringEncryptor.makeEncryptedPassword("");
      if(this.password.equals(password4empty)){
        errorMessages.add("パスワードを入力してください");
      }
    }


}