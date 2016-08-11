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
public class KuaisuAnalyser  extends AbstractAnalyser {

    public static void main(String[] args) {
        if (args == null) {
            System.out.println("请输入将要分析的文件");
            System.exit(-1);
        } else {
            System.out.println(formatOutPut("URL响应统计 : ", 80));
            System.out.println(formatOutPut("------------------------------------------------------------------------------------------", 80));
            invokeAnalysisResponse(args[0]);
            System.out.println();
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
            String lineArray[];
            String key;
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                lineArray = line.split("\\s+");
                int lineLength = lineArray.length;
                    try {
                        key = lineArray[1];
                        if(key.indexOf("?")>-1){
                            key = key.substring(0,key.indexOf("?"));
                        }
                        consumedTime = Long.parseLong(lineArray[lineLength-1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("error line but continue : " + line);
                        continue;
                    }
                compute(map, consumedTime, key);
                totoalLine++;
            }
            printResult(map);
        } catch (Exception e) {
            System.out.println("error line : " + line);
            e.printStackTrace();
        }
    }

    public static void printResult(Map<String, List> map) {
        List<URLEntity> list = new ArrayList<URLEntity>();
        for (Map.Entry<String, List> entry : map.entrySet()) {
            String key = entry.getKey();
            List datas = entry.getValue();
            URLEntity urlEntity = new URLEntity();
            urlEntity.setUrl(key);
            urlEntity.setMaxTime((Long) datas.get(0));
            urlEntity.setMinTime((Long) datas.get(1));
            urlEntity.setInvokeTimes((Integer) datas.get(2));
            urlEntity.setAverageTime((Long) datas.get(3) / (Integer) datas.get(2));
            list.add(urlEntity);
        }

        Collections.sort(list, new Comparator<URLEntity>() {
            public int compare(URLEntity arg0, URLEntity arg1) {
                return ((Long) (arg1.getMaxTime() - arg0.getMaxTime())).intValue();
            }
        });
        System.out.println(formatOutPut("URL名", 98) + formatOutPut("最大耗时", 10) + formatOutPut("最小耗时", 8) + formatOutPut("平均耗时", 10) + formatOutPut("响应次数", 8));
        for (URLEntity u : list) {
            System.out.println(formatOutPut(u.getUrl(), 100) + formatOutPut(u.getMaxTime() + "", 12) + formatOutPut(u.getMinTime() + "", 12) + formatOutPut(u.getAverageTime() + "", 12) + formatOutPut(u.getInvokeTimes() + "", 12));
        }
    }

    public static String formatOutPut(String str, int spaceTimes) {
        while (str.length() <= spaceTimes) {
            str = str + " ";
        }
        return str;
    }
}
