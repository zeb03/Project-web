package javaweb.ssm.basedao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ze
 * @creat 2022-11-02-14:47
 */
public class ConnUtil {
//    public static String DRIVER;
//    public static String URL;
//    public static String USER;
//    public static String PWD;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static Properties properties = new Properties();
    static {
        InputStream inputStream = ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
//            DRIVER = properties.getProperty("jdbc.driver");
//            URL = properties.getProperty("jdbc.url");
//            USER = properties.getProperty("jdbc.user");
//            PWD = properties.getProperty("jdbc.pwd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection creatConn() {
        try {
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConn() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = creatConn();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = ConnUtil.getConn();
        if (conn == null) {
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.remove();
        }
    }
}
