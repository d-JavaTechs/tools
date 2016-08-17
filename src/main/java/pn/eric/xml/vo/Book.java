package pn.eric.xml.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by Shadow on 2016/8/17.
 */
@XStreamAlias("book")
public class Book {
    // 别名注解，这个别名就是XML文档中的元素名，Java的属性名不一定要与别名一致
    @XStreamAlias("name")
    private String name;

    @XStreamAlias("author")
    private String author;

    // 属性注解，此price就是book的属性，在XML中显示为：<book price="108">
    @XStreamAsAttribute()
    @XStreamAlias("price")
    private String price;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }
}
