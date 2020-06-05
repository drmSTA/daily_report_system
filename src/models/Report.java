package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import utility.StringValidator;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
            name = Report.GET_ALL_REPORTS,
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = Report.GET_REPORT_COUNT,
            query = "SELECT COUNT(r) FROM Report AS r"
            ),
    @NamedQuery(
        name = Report.GET_ALL_MY_REPORTS,
        query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
        ),
@NamedQuery(
        name = Report.GET_MY_REPORT_COUNT,
        query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
        )
})
@Entity
public class Report {
    public static final String GET_ALL_REPORTS            = "getAllReports";
    public static final String GET_REPORT_COUNT         = "getReportCount";
    public static final String GET_ALL_MY_REPORTS      = "getAllMyReports";
    public static final String GET_MY_REPORT_COUNT   = "getMyReportCount";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private static int TITLE_LENGTH_UL = 255;
    // review this restriction of maximum length for title if necessary
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    //=======================
    // constructor
    //=======================

    // constructor for DB driver
    protected Report() {
      this(0, new Employee(), new Date(System.currentTimeMillis()), "","");
    }

    // constructor for new report (template)
    public Report(Employee employee) {
      this(0, employee, new Date(System.currentTimeMillis()), "","");
      /* contents of initialized "no parameter" object are the same as below
      id = 0 (none report due to count for id starts from 1)
      reportData = now
      title and content = "";
      */
    }

    // constructor for new report (from input form)
    public Report(Employee employee, Date reportDate, String title, String contents){
      this(0, employee, reportDate, title, contents);
    }

    // constructor for update report which already exists in DB
    public Report(int id, Employee employee, Date reportDate, String title, String contents){
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      this.id = id;
      this.employee = employee;
      this.report_date = reportDate;
      this.title = title;
      this.content = contents;
      this.created_at = currentTime;
      this.updated_at = currentTime;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setUpdatedNow() {
      this.updated_at = new Timestamp(System.currentTimeMillis());
    }


    // =======================
    // validation
    // =======================
    public List<String> performValidation4NewRegistration(){
      ArrayList<String> result = new ArrayList<>();
      this.validateTitle(result);
      this.validateContent(result);
      return result;
    }

    public void validateTitle(ArrayList<String> errorMessages){
      if(StringValidator.isEmpty(this.title)){
        errorMessages.add("タイトルを入力してください");
        return;
      }

      if(this.title.length() > TITLE_LENGTH_UL){
        errorMessages.add("タイトルの最大文字が" + TITLE_LENGTH_UL + "以下となるよう調整ください");
      }
    }

    public void validateContent(ArrayList<String> errorMessages){
      if(StringValidator.isEmpty(this.content)){
        errorMessages.add("内容を入力してください");
      }
    }
}