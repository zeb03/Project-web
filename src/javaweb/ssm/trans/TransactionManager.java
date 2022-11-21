package javaweb.ssm.trans;


import javaweb.ssm.util.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ze
 * @creat 2022-11-02-14:56
 */
public class TransactionManager {
    //开启事务
    public static void beginTrans() throws SQLException {

        ConnUtil.getConn().setAutoCommit(false);
    }

    //提交事务
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
    }

    //回滚事务
    public static void rollback() throws SQLException {
        ConnUtil.getConn().rollback();
        ConnUtil.closeConn();
    }
}
