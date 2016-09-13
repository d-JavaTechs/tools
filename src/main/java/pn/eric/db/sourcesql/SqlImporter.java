package pn.eric.db.sourcesql;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.io.File;

/**
 * @author Eric
 * @date
 */
public class SqlImporter {

    public static void runSqlScript() {
        SQLExec sqlExec = new SQLExec();
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1/test?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "123456";
        sqlExec.setDriver(driverClass);
        sqlExec.setUrl(url);
        sqlExec.setUserid(username);
        sqlExec.setPassword(password);


        sqlExec.setSrc(new File("D:/sql/user.sql"));
        //如果有出错的语句继续执行.
        sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "continue")));
        sqlExec.setPrint(true);
        sqlExec.setOutput(new File("D:/userinfo.txt"));
        sqlExec.setProject(new Project());
        sqlExec.execute();
    }

    public static void main(String[] args) {
        runSqlScript();
    }
}
