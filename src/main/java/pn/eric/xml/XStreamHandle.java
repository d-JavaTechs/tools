package pn.eric.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import pn.eric.xml.vo.Book;
import pn.eric.xml.vo.Books;

import java.util.List;

/**
 * Created by Shadow on 2016/8/17.
 */
public class XStreamHandle {
    private static final String xmlString = "<books><book price=\"108\"><name>Java编程思想</name><author>Bruce Eckel</author></book><book price=\"52\"><name>Effective Java</name><author>Joshua Bloch</author></book><book price=\"118\"><name>Java 7入门经典</name><author>Ivor Horton</author></book></books>";

    public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        /*
         // 以压缩的方式输出XML
         StringWriter sw = new StringWriter();
         xstream.marshal(obj, new CompactWriter(sw));
         return sw.toString();
         */
        // 以格式化的方式输出XML
        return xstream.toXML(obj);
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

    public static void main(String[] args) {
        Books books = toBean(xmlString, Books.class);
        List<Book> list = books.getList();
        for(Book book : list) {
            System.out.println("name=" + book.getName() + "\tauthor=" + book.getAuthor()
                    + "\tprice=" + book.getPrice());
        }
        System.out.println(toXml(books));
    }
}
