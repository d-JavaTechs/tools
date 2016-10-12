package pn.eric.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric
 * @date
 */
public class CsvUtil {

    public static void main(String[] args) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(new File("./1.csv")),',');
        List<String[]> alList=new ArrayList<String[]>();
        List<String> list=new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        alList.add(list.toArray(new String[list.size()]));

        list=new ArrayList<String>();
        list.add("dd");
        list.add("ee");
        list.add("ff");
        alList.add(list.toArray(new String[list.size()]));

        writer.writeAll(alList);
        writer.close();
        System.out.println(writer.toString());

    }

    public static void main1(String[] args) throws Exception{
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Pojo.class);
        Pojo value = new Pojo(1,"dw2","dwp");
        String csv = mapper.writer(schema).writeValueAsString(value);
        System.out.println("csv: "+ csv);
    }
}
