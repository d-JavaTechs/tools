package pn.eric.qrcode;

import javax.swing.filechooser.FileSystemView;

/**
 * Created by eric on 16/11/10.
 */
public class Encoder {
    public static void main(String[] args) {
        String desktopDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        String imgPath = desktopDir+"/Desktop/QR_Code.JPG";
        System.out.println("imgPath: " + imgPath);
        Util handler = new Util();
        String decoderContent = handler.decoderQRCode(imgPath);
        System.out.println("解析结果如下：");
        System.out.println(decoderContent);
        System.out.println("========二维码解析成功!!!");
    }
}
