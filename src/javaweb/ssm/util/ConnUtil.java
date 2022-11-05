package javaweb.ssm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ze
 * @creat 2022-11-02-14:47
 */
public class ConnUtil {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver" ;
    public static final String URL = "jdbc:mysql://localhost:3306/fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String USER = "root";
    public static final String PWD = "abc123";
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static Connection creatConn(){
        try {
            //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public static Connection getConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null){
            conn = creatConn();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = ConnUtil.getConn();
        if (conn == null){
            return;
        }
        if (!conn.isClosed()){
            conn.close();
            threadLocal.remove();
        }
    }
}
