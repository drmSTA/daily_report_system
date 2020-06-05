package listeners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utility.StringEncryptor;
import utility.WB;
/**
 * Application Lifecycle Listener implementation class PropertiesListener
 *
 */
@WebListener
public class PropertiesListener implements ServletContextListener {
    private static final long serialVersionUID = 20200601L;

    /**
     * Default constructor.
     */
    public PropertiesListener() {
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent)  {
        ServletContext context = servletContextEvent.getServletContext();

        String path = context.getRealPath("/META-INF/application.properties");
        try {
            InputStream is = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(is);
            is.close();

            Iterator<String> pit = properties.stringPropertyNames().iterator();
            String key;
            while(pit.hasNext()) {
                key = pit.next();
                context.setAttribute(key, properties.getProperty(key));
                if(key.equals(WB.K_SALT)) StringEncryptor.setSalt(properties.getProperty(key));
            }
        } catch(IOException e) {}
    }
}