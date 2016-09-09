package pn.eric.wechat;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * @author duwupeng
 * @date
 */
public class BatchSend {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
         Process p = null;
         String fileLac = "";
        Robot robot=null;
         try {
             fileLac = args[0];//要调用的程序路径
             p = rt.exec(fileLac);
             Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
             setClipboardText(sysc, args[1]);
             robot = new Robot();//创建Robot对象
             robot.delay(2000);

             for (int i=0;i<Integer.parseInt(args[2]);i++){
                 pressKeyWithCTRL(robot, KeyEvent.VK_V);
                 keyPress(robot,KeyEvent.VK_ENTER);
             }
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("open failure");
         }
    }

    //往剪切板写文本数据
    protected static void setClipboardText(Clipboard clip, String writeMe) {
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    protected static String getClipboardText() throws Exception{
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
        // 获取剪切板中的内容
        Transferable clipT = clip.getContents(null);
        if (clipT != null) {
            // 检查内容是否是文本类型
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
                return (String)clipT.getTransferData(DataFlavor.stringFlavor);
        }
        return null;
    }

    public static void pressKeyWithCTRL(Robot robot, int keyvalue) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(keyvalue);
        robot.keyRelease(keyvalue);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
    }

    //单个按键
    public static void keyPress(Robot r,int key){
        r.keyPress(key);
        r.keyRelease(key);
        r.delay(1000);
    }
}
