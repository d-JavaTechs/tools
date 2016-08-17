package pn.eric.xml.vo;

/**
 * Created by Shadow on 2016/8/17.
 */
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("books")
public class Books {

    // 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
    @XStreamImplicit
    private List<Book> list;

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}