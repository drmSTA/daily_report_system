package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnector {
  private static final long serialVersionUID = 20200601L;
  private static final String PERSISTENCE_UNIT_NAME = "daily_report_system";
  private static EntityManagerFactory emf;

  public static EntityManager getEntityManager() {
      return _getEntityManagerFactory().createEntityManager();
  }

  private static EntityManagerFactory _getEntityManagerFactory() {
      if(emf == null) {
          emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
      }

      return emf;
  }

}
