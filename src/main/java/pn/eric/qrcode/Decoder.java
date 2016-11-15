package pn.eric.qrcode;

import javax.swing.filechooser.FileSystemView;

/**
 * Created by eric on 16/11/10.
 */
public class Decoder {
    public static void main(String[] args) {
        String desktopDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

        String imgPath = desktopDir+"Desktop/QR_Code.JPG";

        System.out.println("imgPath: " + imgPath);

        String encoderContent = "公司名称";
        Util handler = new Util();
        handler.encoderQRCode(encoderContent, imgPath, "png");
        System.out.println("========二维码生成成功");

    }
}
