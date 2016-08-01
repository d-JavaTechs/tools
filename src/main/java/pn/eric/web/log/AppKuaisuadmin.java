package pn.eric.web.log;



import pn.eric.web.log.vo.ServiceErrorEntity;
import pn.eric.web.log.vo.URLEntity;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 */
public class AppKuaisuadmin {

    public static void main(String[] args) {
//      System.out.println(extractime("9580ms"));;
        if (args == null) {
            System.out.println("请输入将要分析的文件");
            System.exit(-1);
        } else {
            System.out.println(formatOutPut("URL响应统计 : ", 80));
            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
            invokeAnalysisResponse(args[0]);
            System.out.println();

            System.out.println(formatOutPut("服务请求次数统计 : ", 80));
            System.out.println();
            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
            System.out.println();
            invokeAnalysisRequest(args[1]);
            System.out.println();


//            System.out.println(formatOutPut("错误统计 : ", 80));
//            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
//            invokeAnalysisErrors(args[2]);
        }
    }

    public static void invokeAnalysisResponse(String fileFullName) {
        // 最大耗时   最小耗时  响应次数
        Map<String, List> map = new HashMap<String, List>();
        List<String> illgalLine = new ArrayList<String>();
        int totoalLine = 0;
        String line = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileFullName, new String[0]), StandardCharsets.UTF_8);
            String url;
            long consumedTime = 0;
            String key;
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
//                System.out.println(line);
                if (line.trim().startsWith("服务接口")) {
                    try {
                        key = line.substring(6);
//                        System.out.println("key: " + key);
                        consumedTime = extractime(lines.get(++i));
                    } catch (Exception e) {
                        e.printStackTrace();

                        System.out.println("error line but continue : " + line);
                        continue;
                    }
                    if (map.containsKey(key)) {
                        List value = map.get(key);
                        long currentMin = (Long) value.get(1);
                        if (currentMin > consumedTime) {
                            value.set(1, consumedTime);
                        }
                        int currentInvokeTimes = (Integer) value.get(2);
                        value.set(2, ++currentInvokeTimes);
                        value.set(3, (Long) value.get(3) + consumedTime);

                        map.put(key, value);
                    } else {
                        List datas = new ArrayList();
                        datas.add(0, consumedTime);//最大耗时
                        datas.add(1, consumedTime);//最小耗时
                        datas.add(2, 1);//调用次数
                        datas.add(3, consumedTime);//耗时平均耗时

                        map.put(key, datas);
                    }
                    ++i;
                    totoalLine++;
                } else {
                    illgalLine.add(line);
                }
            }
            printResult(map);
//            printIllgalLine(illgalLine);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
        }

    }

    public static void invokeAnalysisErrors(String fileFullName) {
        String line = null;
        String key;
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileFullName, new String[0]), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                if (line.indexOf("ERROR - error") != -1) {
                    String serviceInfo = line;
                    String errorInfo = lines.get(i + 1);
                    String[] lineArray = serviceInfo.split("\\s+");
                    String serviceName = lineArray[6];
                    String methodName = lineArray[7];
                    key = serviceName + "-" + methodName;
                    if (map.containsKey(key)) {
                        map.put(key, (map.get(key) + 1));
                    } else {
                        map.put(key, 1);
                    }
//                    System.out.println(line);
//                    System.out.println(lines.get(i+1));
                }
            }
            printErrorResult(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void invokeAnalysisRequest(String fileFullName) {
        // 最大耗时   最小耗时  响应次数
        Map<String, Integer> map = new HashMap<String, Integer>();
        String line = null;
        try {
            String key;
            List<String> lines = Files.readAllLines(Paths.get(fileFullName, new String[0]), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                if (line.trim().startsWith("服务接口")) {
                    key = line.substring(6);

                    if (map.containsKey(key)) {
                        map.put(key, (map.get(key) + 1));
                    } else {
                        map.put(key, 1);
                    }
                }

            }
            System.out.println("map size: " + map.size());
            printRequestResult(map);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
        }
    }



    public static long extractime(String timeString) {
//        int len = timeString.length();
//        long time = Long.parseLong(timeString.substring(0, len - 2));
//        return time;
        if(timeString.length()>7){
            return Long.parseLong(timeString.substring(6, timeString.length() - 2));
        }else{
            return 0;
        }
    }

    public static void printRequestResult(Map<String, Integer> map) {
        List<URLEntity> list = new ArrayList<URLEntity>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            URLEntity uRLEntity = new URLEntity();
            uRLEntity.setUrl(key);
            uRLEntity.setInvokeTimes(entry.getValue());
            list.add(uRLEntity);
        }
        Collections.sort(list, new Comparator<URLEntity>() {
            public int compare(URLEntity arg0, URLEntity arg1) {
                return (arg1.getInvokeTimes() - arg0.getInvokeTimes());
            }
        });
        System.out.println(formatOutPut("URL名", 100)  + formatOutPut("请求次数", 30));
        for (URLEntity u : list) {
            System.out.println(formatOutPut(u.getUrl(), 100)  + formatOutPut(u.getInvokeTimes() + "", 35));
        }
    }

    public static void printResult(Map<String, List> map) {
        List<URLEntity> list = new ArrayList<URLEntity>();
        for (Map.Entry<String, List> entry : map.entrySet()) {
            String key = entry.getKey();
            List datas = entry.getValue();
//            ServiceEntity serviceEntity = new ServiceEntity();
//            serviceEntity.setSeiveName(key.split(":")[1].split("-")[0]);
//            serviceEntity.setMethodName(key.split(":")[1].split("-")[1]);
            URLEntity urlEntity = new URLEntity();
            urlEntity.setUrl(key);
            urlEntity.setMaxTime((Long) datas.get(0));
            urlEntity.setMinTime((Long) datas.get(1));
            urlEntity.setInvokeTimes((Integer) datas.get(2));
            urlEntity.setAverageTime((Long) datas.get(3) / (Integer) datas.get(2));
            list.add(urlEntity);
//            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Collections.sort(list, new Comparator<URLEntity>() {
            public int compare(URLEntity arg0, URLEntity arg1) {
                return ((Long) (arg1.getMaxTime() - arg0.getMaxTime())).intValue();
            }
        });
        System.out.println(formatOutPut("URL名", 100) + formatOutPut("最大耗时", 12) + formatOutPut("最小耗时", 12) + formatOutPut("平均耗时", 12) + formatOutPut("响应次数", 12));
        for (URLEntity u : list) {
            System.out.println(formatOutPut(u.getUrl(), 100) + formatOutPut(u.getMaxTime() + "", 12) + formatOutPut(u.getMinTime() + "", 12) + formatOutPut(u.getAverageTime() + "", 12) + formatOutPut(u.getInvokeTimes() + "", 12));
        }
    }

    public static void printErrorResult(Map<String, Integer> map) {
        List<ServiceErrorEntity> list = new ArrayList<ServiceErrorEntity>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            ServiceErrorEntity serviceRequestEntity = new ServiceErrorEntity();
            serviceRequestEntity.setSeiveName(key.split(":")[1].split("-")[0]);
            serviceRequestEntity.setMethodName(key.split(":")[1].split("-")[1]);
            serviceRequestEntity.setInvokeTimes(entry.getValue());
            list.add(serviceRequestEntity);
        }
        Collections.sort(list, new Comparator<ServiceErrorEntity>() {
            public int compare(ServiceErrorEntity arg0, ServiceErrorEntity arg1) {
                return (arg1.getInvokeTimes() - arg0.getInvokeTimes());
            }
        });
        System.out.println(formatOutPut("服务名", 34) + formatOutPut("方法名", 36) + formatOutPut("错误次数", 30));
        for (ServiceErrorEntity u : list) {
            System.out.println(formatOutPut(u.getSeiveName(), 35) + formatOutPut(u.getMethodName(), 40) + formatOutPut(u.getInvokeTimes() + "", 35));
        }
    }

    public static String formatOutPut(String str, int spaceTimes) {
        while (str.length() <= spaceTimes) {
            str = str + " ";
        }
        return str;
    }
}
