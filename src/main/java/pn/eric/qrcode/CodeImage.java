package pn.eric.qrcode;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * Created by eric on 16/11/10.
 */
public class CodeImage implements QRCodeImage {

    BufferedImage bufImg;

    public CodeImage(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    public int getHeight() {
        return bufImg.getHeight();
    }

    public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y);
    }

    public int getWidth() {
        return bufImg.getWidth();
    }
}