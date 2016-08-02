package pn.eric.web.log;



import pn.eric.web.log.vo.ServiceEntity;
import pn.eric.web.log.vo.ServiceErrorEntity;
import pn.eric.web.log.vo.ServiceRequestEntity;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 */
public class SoaLogAnalyser {


    static DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    static Date time1;
    static Date time2;
    static Date time3;
    static Date time4;
    static Date time5;
    static Date time6;
    static long timeDiffLimit = 300000;
    static long timeAverageLimit = 60000;

    static {
        try {
            time1 = sdf.parse("13:00:00");
            time2 = sdf.parse("19:00:00");
            time3 = sdf.parse("23:00:00");
            time4 = sdf.parse("00:00:00");
            time5 = sdf.parse("14:00:00");
            time6 = sdf.parse("20:00:00");
        } catch (Exception e) {

        }

    }


    public static void main(String[] args) {
        if (args == null) {
            System.out.println("请输入将要分析的文件");
            System.exit(-1);
        } else {
            System.out.println(formatOutPut("服务响应统计 : ", 80));
            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
            invokeAnalysisResponse(args[0]);
            System.out.println();
            if(args.length>=2){
                System.out.println(formatOutPut("服务请求次数统计 : ", 80));
                System.out.println();
                System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
                System.out.println();
                invokeAnalysisRequest(args[1]);
            }
            if (args.length>=3){
                System.out.println();
                System.out.println(formatOutPut("错误统计 : ", 80));
                System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
                invokeAnalysisErrors(args[2]);
            }
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
            String serviceName;
            String methodName;
            String key;

            List<String> lines = Files.readAllLines(Paths.get(fileFullName, new String[0]), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                String[] lineArray = line.split("\\s+");
                if (lineArray.length >= 2) {
                    serviceName = lineArray[0];
                    methodName = lineArray[1];
                    key = serviceName + "-" + methodName;
                    if (map.containsKey(key)) {
                        map.put(key, (map.get(key) + 1));
                    } else {
                        map.put(key, 1);
                    }
                }
            }

            printRequestResult(map);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
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
            String serviceName;
            String methodName;
            long consumedTime = 0;
            String key;
            for (int i = 0; i < lines.size(); i++) {

                line = lines.get(i);

                String[] lineArray = line.split("\\s+");

                if (lineArray.length >= 4&& !timeDiffAllow(lineArray[0])) {
                    serviceName = lineArray[1];
                    methodName = lineArray[3];
                    try {
                        consumedTime = extractime(lineArray[4]);
                    } catch (Exception e) {
                        System.out.println("error line but continue : " + line);
                        continue;
                    }

                    key = serviceName + "-" + methodName;

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
                } else {
                    illgalLine.add(line);
                }

                totoalLine++;

            }
            printResult(map);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
        }

    }

    public static long extractime(String timeString) {
        int len = timeString.length();
        long time = Long.parseLong(timeString.substring(3, len - 2));
        return time;
    }

    public static void printRequestResult(Map<String, Integer> map) {
        List<ServiceRequestEntity> list = new ArrayList<ServiceRequestEntity>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
            serviceRequestEntity.setSeiveName(key.split(":")[1].split("-")[0]);
            serviceRequestEntity.setMethodName(key.split(":")[1].split("-")[1]);
            serviceRequestEntity.setInvokeTimes(entry.getValue());
            list.add(serviceRequestEntity);
        }
        Collections.sort(list, new Comparator<ServiceRequestEntity>() {
            public int compare(ServiceRequestEntity arg0, ServiceRequestEntity arg1) {
                return (arg1.getInvokeTimes() - arg0.getInvokeTimes());
            }
        });
        System.out.println(formatOutPut("服务名", 34) + formatOutPut("方法名", 36) + formatOutPut("请求次数", 30));
        for (ServiceRequestEntity u : list) {
            System.out.println(formatOutPut(u.getSeiveName(), 35) + formatOutPut(u.getMethodName(), 40) + formatOutPut(u.getInvokeTimes() + "", 35));
        }
    }

    public static void printResult(Map<String, List> map) {
        List<ServiceEntity> list = new ArrayList<ServiceEntity>();
        for (Map.Entry<String, List> entry : map.entrySet()) {
            String key = entry.getKey();
            List datas = entry.getValue();
            ServiceEntity serviceEntity = new ServiceEntity();
            serviceEntity.setSeiveName(key.split("-")[0]);
            serviceEntity.setMethodName(key.split("-")[1]);
            serviceEntity.setMaxTime((Long) datas.get(0));
            serviceEntity.setMinTime((Long) datas.get(1));
            serviceEntity.setInvokeTimes((Integer) datas.get(2));
            serviceEntity.setAverageTime((Long) datas.get(3) / (Integer) datas.get(2));
            list.add(serviceEntity);
//            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Collections.sort(list, new Comparator<ServiceEntity>() {
            public int compare(ServiceEntity arg0, ServiceEntity arg1) {
                return ((Long) (arg1.getMaxTime() - arg0.getMaxTime())).intValue();
            }
        });
        System.out.println(formatOutPut("服务名", 34) + formatOutPut("方法名", 35) + formatOutPut("最大耗时", 12) + formatOutPut("最小耗时", 12) + formatOutPut("平均耗时", 12) + formatOutPut("响应次数", 12));
        for (ServiceEntity u : list) {
            System.out.println(formatOutPut(u.getSeiveName(), 35) + formatOutPut(u.getMethodName(), 40) + formatOutPut(u.getMaxTime() + "", 12) + formatOutPut(u.getMinTime() + "", 12) + formatOutPut(u.getAverageTime() + "", 12) + formatOutPut(u.getInvokeTimes() + "", 12));
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

    public static void printIllgalLine(List<String> illgalLines) {
        for (String illgalLine : illgalLines) {
            System.out.println("illgalLines line : " + illgalLine);
        }
    }


    public static Date timeToDate(String currentTime) {
        Date date = null;
        try {
            date = sdf.parse(currentTime.trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean timeDiffAllow(String currentTime) {
        Date timeToDate = timeToDate(currentTime);
        try {
            if (currentTime.startsWith("13:")) {
                return (timeToDate.getTime() - time1.getTime() < timeDiffLimit);
            } else if (currentTime.startsWith("19:")) {
                return (timeToDate.getTime() - time2.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("23:")) {
                return (timeToDate.getTime() - time3.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("00:")) {
                return (timeToDate.getTime() - time4.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("14:")) {
                return (timeToDate.getTime() - time5.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("20:")) {
                return (timeToDate.getTime() - time6.getTime() < timeDiffLimit);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
