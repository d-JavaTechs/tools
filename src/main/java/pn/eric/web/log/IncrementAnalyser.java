package pn.eric.web.log;

import pn.eric.shell.JavaShellUtil;
import pn.eric.time.TimeUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Eric
 */
public class IncrementAnalyser {
    private static String logRoot = "/home/weihu/production-logs";
    private static String command = " du -h --max-depth=1 " + logRoot;
    private static String[] kuaisuDir = new String[]{"/home/weihu/production-logs/web-master/kuaisu",
                                                        "/home/weihu/production-logs/web-slave/kuaisu",
                                                        "/home/weihu/production-logs/gray-1/kuaisu"};
    private static String[] kuaisuAdminDir = new String[]{"/home/weihu/production-logs/web-master/kuaisuadmin",
                                                        "/home/weihu/production-logs/web-slave/kuaisuadmin",
                                                        "/home/weihu/production-logs/gray-1/kuaisuadmin"};
    private static String[] ksserviceDir = new String[]{"/home/weihu/production-logs/service-master/ksservices",
                                                        "/home/weihu/production-logs/service-slave/ksservices",
                                                        "/home/weihu/production-logs/gray-1/ksservices"};
    private static String[] soaDir = new String[]{"/home/weihu/production-logs/service-master/dapeng",
                                                "/home/weihu/production-logs/service-slave/dapeng",
                                                "/home/weihu/production-logs/gray-1/dapeng"};

    public static void main(String[] args){
        int dateCount = Integer.parseInt(args[0]);
        getTotlalBusyLogSize(dateCount);

        System.out.println(command);
        try{
            JavaShellUtil.executeShell(command);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Get total fileSize
    private static void getTotlalBusyLogSize(int dateCount){
        String today = TimeUtil.getTime(0);
        String startDate = TimeUtil.getTime(dateCount);
        LinkedHashMap<String, int[]> totals = new LinkedHashMap<String,int[]>();
        while(startDate.compareTo(today)<=0){
            totals.put(startDate, new int[]{getSizeOfDay(ksserviceDir,startDate),getSizeOfDay(kuaisuDir,startDate), getSizeOfDay(kuaisuAdminDir,startDate),getSizeOfDay(soaDir,startDate)});
            startDate =  TimeUtil.getTime(++dateCount);
        }

        List totalList=new ArrayList<Map.Entry<String,int[]>>
                (totals.entrySet());
        for(int i=0;i<totalList.size();i++){
            Map.Entry<String,int[]> entry=(Map.Entry<String,int[]>)totalList.get(i);
            System.out.println(entry.getKey()+"日 \n\r\n\r  ksservice        kuaisu            kuaisuadmin      soa         total");
            int[] value =  entry.getValue();
            int total=0;
            for (int j=0;j<value.length;j++){
                 total +=value[j];
                     System.out.print((value[j] / 1024.0) + "M    ");
                 if(j==value.length-1){
                     System.out.println((total / 1024.0) + "M   ");
                 }
            }
            System.out.println();
        }
    }

    private static int  getSizeOfDay(String[] dirNames, String date){
        StringBuffer stringBuffer = new StringBuffer();
        int sizeBybit = 0;
        for(String dir:dirNames){
            Process pid = null;
            BufferedReader bufferedReader = null;
//            stringBuffer.append("du -s " + dir +"/*"+ date+"*文件统计:").append("\r\n");
            try {
                String[] cmd = { "/bin/sh", "-c", "du -s "+dir+"/*"+ date+"*"};
                // 执行Shell命令
                pid = Runtime.getRuntime().exec(cmd);
                if (pid != null) {
//                    stringBuffer.append("进程号：").append(pid.toString())
//                            .append("\r\n");
                    // bufferedReader用于读取Shell的输出内容
                    bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
                    pid.waitFor();
                } else {
                    stringBuffer.append("没有pid\r\n");
                }
//                stringBuffer.append("Shell命令执行完毕\r\n执行结果为：\r\n");

                String line = null;
                // 读取Shell的输出内容，并添加到stringBuffer中
                while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(line).append("\r\n");
                    sizeBybit +=Integer.parseInt(line.split("\\s+")[0]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return sizeBybit;
    }
}
