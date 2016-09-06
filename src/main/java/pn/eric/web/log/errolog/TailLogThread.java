package pn.eric.web.log.errolog;


import pn.eric.email.EmailUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author eric
 * @date
 */
public class TailLogThread  extends Thread {

    String filePath;
    public TailLogThread(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void run() {
        Map<String, Integer> error = new HashMap();
        try {
            System.out.println(Thread.currentThread().getName());
            String fileName = this.filePath;
            String[] cmd = { "/bin/sh", "-c", "tail -f " + fileName + "| grep Exception | grep -v PROPAGATION_REQUIRED,ISOLATION_DEFAULT | grep -v BaseServiceException | grep -v 'Adding transactional method'"};
            System.out.println("execute command cmd: "+ cmd[2]);
            Process  process = Runtime.getRuntime().exec(cmd);

            InputStream inputStream = process.getInputStream();
            Scanner sc = new Scanner(inputStream, "UTF-8");
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if(!line.trim().startsWith("at")&&!line.trim().startsWith("###")){
                    if(!error.containsKey(line)){
                        error.put(line,1);
                        EmailUtil.sendEmail("duwupeng@iplas.com.cn", this.filePath + "出现新异常", "大家好,异常简短信息为" + line, null);
                    }else{
                        error.put(line, error.get(line)+1);
                    }
                }
                System.out.println(line);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
