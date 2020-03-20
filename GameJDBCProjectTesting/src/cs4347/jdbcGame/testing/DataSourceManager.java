/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of every team member to the Provost Office for academic 
 * dishonesty. 
 */

package cs4347.jdbcGame.testing;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Application use the static method getDataSource() to obtain the singleton
 * DataSource. Data sources are used to open connections to the MySQL server.
 * The DBMS connection parameters (url, id, and password) is maintained in a
 * property file 'dbconfig.properties'. The property file must be located on the
 * application's CLASSPATH. See the configuration property file is loaded by the
 * method getPropertiesFromClasspath().
 */
public class DataSourceManager
{

    private static BasicDataSource singletonDS = null;

    public synchronized static DataSource getDataSource() throws IOException
    {
        if (singletonDS == null) {
            System.out.println("Creating Datasource " + new Date());
            Properties props = getPropertiesFromClasspath();

            String url = props.getProperty("url");
            if (url == null || url.isEmpty()) {
                throw new RuntimeException("property 'url' not found in configuration file");
            }

            String id = props.getProperty("id");
            if (id == null || id.isEmpty()) {
                throw new RuntimeException("property 'id' not found in configuration file");
            }

            String passwd = props.getProperty("passwd");
            if (passwd == null || passwd.isEmpty()) {
                throw new RuntimeException("property 'passwd' not found in configuration file");
            }

            singletonDS = new BasicDataSource();
            singletonDS.setUrl(url);
            singletonDS.setUsername(id);
            singletonDS.setPassword(passwd);
        }
        return singletonDS;
    }

    private static final String propFileName = "dbconfig.properties";

    public static Properties getPropertiesFromClasspath() throws IOException
    {
        // Load dbconfig.properties from the classpath
        Properties props = new Properties();
        InputStream inputStream = DataSourceManager.class.getClassLoader().getResourceAsStream(propFileName);

        if (inputStream == null) {
            throw new RuntimeException("property file '" + propFileName + "' not found in the classpath");
        }

        props.load(inputStream);

        return props;
    }

    public static void main(String args[])
    {
        long startTime = System.currentTimeMillis();
        try {
            DataSource ds = DataSourceManager.getDataSource();
            for (int idx = 0; idx < 1; idx++) {
                Connection con = ds.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select count(*) from game");
                if (rs.next()) {
                    System.out.println(idx + " Count: " + rs.getInt(1));
                }
                stmt.close();
                con.close();
            }
            System.out.println("Finished " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}