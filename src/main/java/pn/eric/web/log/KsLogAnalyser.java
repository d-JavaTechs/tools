package pn.eric.web.log;

import pn.eric.web.log.vo.ServiceEntity;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 */
public class KsLogAnalyser extends AbstractAnalyser {

    public static void main(String[] args) {
        if (args == null) {
            System.out.println("请输入将要分析的文件");
            System.exit(-1);
        } else {
            System.out.println(formatOutPut("服务响应统计 : ", 80));
            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
            invokeAnalysisResponse(args[0]);
            System.out.println();
        }
    }

    public static void invokeAnalysisResponse(String fileFullName) {
        Map<String, List> map = new HashMap<String, List>();
        List<String> illgalLine = new ArrayList<String>();
        String line = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileFullName, new String[0]), StandardCharsets.UTF_8);
            String serviceName;
            String methodName;
            String consumedTTime;
            long consumedTime = 0;
            String key;
            for (int i = 0; i < lines.size(); i++) {

                line = lines.get(i);

                String[] lineArray = line.split("\\s+");
                if (lineArray.length >= 17) {
                    serviceName = lineArray[6].substring(10);
                    methodName = lineArray[7].substring(0, lineArray[7].length() - 1);
                    consumedTTime = lineArray[15].substring(1);
                }else if(lineArray.length == 10){
                    serviceName = lineArray[6].substring(10);
                    methodName = lineArray[7].substring(0, lineArray[7].length() - 1);
                    consumedTTime = lineArray[8].substring(1);
                }else if(lineArray.length == 9){
                    serviceName = lineArray[6].substring(10);
                    methodName = lineArray[7].substring(0, lineArray[7].length() - 1);
                    consumedTTime = lineArray[8];
                }else if(lineArray.length == 16){
                    serviceName = lineArray[6].substring(10);
                    methodName = lineArray[7].substring(0, lineArray[7].length() - 1);
                    consumedTTime = lineArray[15];
                }else{
                    illgalLine.add(line);
                    continue;
                }

                try {
                    consumedTime = extractime(consumedTTime);
                } catch (Exception e) {
                    if (lineArray.length == 18) {
                        serviceName = lineArray[6].substring(10);
                        methodName = lineArray[7].substring(0, lineArray[7].length() - 1);
                        consumedTTime = lineArray[16].substring(1);
                        consumedTime = extractime(consumedTTime);
                    }else{
                        System.out.println("error line but continue : " + line);
                        continue;
                    }
                }

                key = serviceName + "-" + methodName;
                compute(map, consumedTime, key);

            }
            printResult(map);
            printIllgalLine(illgalLine);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
        }

    }



    public static long extractime(String timeString) {
        int len = timeString.length();
        long time = Long.parseLong(timeString.substring(0, len - 2));
        return time;
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
}
