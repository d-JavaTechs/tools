package pn.eric.db.conn;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Eric  on 2016/8/17.
 */
public class ConnUtil {
    public static final String url = "jdbc:mysql://XXX.XXX.XXX.XXX:3306/dbadapter";
    public static final String user = "root";
    public static final String password = "XXXXXX";

    /**
     * 得到连接
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection establishConn() throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭连接
     * @param conn
     * @throws SQLException
     */
    public static void close(Connection conn) throws SQLException{
        if(conn != null){
            conn.close();
            conn = null;
        }
    }

    /**
     * 关闭PreparedStatement
     * @param pstmt
     * @throws SQLException
     */
    public static void close(PreparedStatement pstmt) throws SQLException{
        if(pstmt != null){
            pstmt.close();
            pstmt = null;
        }
    }

    /**
     * 关闭结果集
     * @param rs
     * @throws SQLException
     */
    public static void close(ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
            rs = null;
        }
    }
}
