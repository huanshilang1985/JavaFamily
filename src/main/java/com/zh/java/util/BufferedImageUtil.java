package com.zh.java.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.AttributedString;
import java.util.Hashtable;
import java.util.UUID;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

/**
 * @Author zhanghe
 * @Desc: BufferedImage 是处理图片的工具
 * @Date 2019/3/27 11:17
 */
@Slf4j
public class BufferedImageUtil {

    /**
     * 二维码生成方法
     *
     * @param content 二维码条码内容
     * @param width   二维码宽
     * @param height  二维码高
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage QrCodeImage(String content, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  //矫错级别
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        //创建比特矩阵（位矩阵）的QR码编码的字符串
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        //使Buffered勾画QRCode（matrixWidth是行二维码像素点）
        int matrixWidth = bitMatrix.getWidth();
        int martrixHeight = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, martrixHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < matrixWidth; x++) {
            for (int y = 0; y < martrixHeight; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 条形码生成方法
     *
     * @param content 生成条码的内容
     * @param width   条码高度
     * @param height  条码高度
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage barcodeImage(String content, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  //矫错级别
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        int codeWidth = 3 + (7 * 6) + 5 + (7 * 6) + 3;
        codeWidth = Math.max(codeWidth, width);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, codeWidth, height, hints);
        int matrixWidth = bitMatrix.getWidth();
        int martrixHeight = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, martrixHeight, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < matrixWidth; x++) {
            for (int y = 0; y < martrixHeight; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 合成图片(本需求是生成商品海报图片)
     *
     * @param qrCodePath 生成二维码所需的Url
     * @param background 图片背景图地址，背景图要jpg格式
     * @param imgPath    要插入的合成图片地址
     * @param skuName    商品名称
     * @param specMemo   商品规格描述
     * @param salePrice  销售价格
     * @param weiPrice   促销价格
     * @param salePriceFlag 是否显示原价
     * @return
     */
    public static File createImage(String qrCodePath, String background, String imgPath, String skuName,String specMemo,String salePrice,String weiPrice, Boolean salePriceFlag) {
        File file = null;
        File tmpDir = FileUtils.getTempDirectory(); //临时路径
        try {
            String fontName = "宋体";  //指定字体
            //1. 创建背景图片
            BufferedImage img = ImageIO.read(new URL(background));
            Graphics2D gd = (Graphics2D) img.getGraphics();//开启画图
            //2. 添加商品图片，符合
            if (StringUtils.isNotBlank(imgPath)) {
                cutPicture(imgPath, gd);
            }
            //5. 写商品名称
            gd.setColor(new Color(34, 44, 60));
            gd.setFont(new Font(fontName, Font.BOLD, 56));

            if (skuName.length() > 25) {
                String skuName1 = skuName.substring(0, 25);
                String skuName2 = skuName.substring(25, skuName.length());
                gd.drawString(skuName1, 60, 828);
                gd.drawString(skuName2, 60, 895);
            } else {
                gd.drawString(skuName, 60, 828);
            }

            //3. 写活动价
            gd.setColor(new Color(255, 49, 49));
            gd.setFont(new Font(fontName, Font.BOLD, 64));
            gd.drawString(weiPrice, 60, 1027);

            if (salePriceFlag) {
                //4. 写原价
                gd.setColor(new Color(187, 187, 187));
                gd.setFont(new Font(fontName, Font.BOLD, 48));
                AttributedString as = new AttributedString(salePrice);   //删除线，  TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON下划线
                as.addAttribute(TextAttribute.FONT, new Font(fontName, Font.BOLD, 48));
                as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 0, salePrice.length());
                gd.drawString(as.getIterator(), (60 + weiPrice.length() * 45), 1030);
            }

            //生成二维码
            BufferedImage qrCode = QrCodeImage(qrCodePath, 376, 376);

            //7. 添加二维码
            gd.setColor(Color.white);
            gd.drawImage(qrCode, 784, 1020, null);
            gd.dispose();   //画完记得关闭g

            file = new File(tmpDir.getPath() + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
            ImageIO.write(img, "jpg", file);
            return file;
        } catch (Exception e) {
            log.error("合成图片失败", e);
        }
        return file;
    }


    /**
     * 裁剪商品图片，当商家图片不符合规格时，不要压缩和拉伸，使图片铺满展示中间部分
     */
    private static void cutPicture(String pictureBackground, Graphics2D gd) throws Exception {
        int width = 1220;
        int height = 748;
        BufferedImage skuImage = ImageIO.read(new URL(pictureBackground));
        int old_w = skuImage.getWidth();  // 得到源图宽
        int old_h = skuImage.getHeight();  // 得到源图长
        if (old_h == height && old_w == width) {
            gd.drawImage(skuImage, 0, 0, null);  //把商品图片加入海报
        } else {
            //商品图片尺寸不同，重新绘制商品图片
            double w = new BigDecimal((float) width / old_w).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            double h = new BigDecimal((float) height / old_h).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            int new_w = 0;  // 放大后源图宽
            int new_h = 0;  // 放大后源图长
            if (w > h) {
                new_w = width;
                new_h = Integer.parseInt(new BigDecimal(old_h * w).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            } else {
                new_w = Integer.parseInt(new BigDecimal(old_w * h).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                new_h = height;
            }
            //重新绘制图片
            BufferedImage newImage = Tosmallerpic(skuImage, new_w, new_h);
            BufferedImage lastImage = null;
            if (height == new_h) {
                /**
                 * 裁剪图片, 参数说明:
                 *     x: 裁剪起点横坐标
                 *     y: 裁剪起点纵坐标
                 *     w: 需要裁剪的宽度
                 *     h: 需要裁剪的高度
                 */
                lastImage = newImage.getSubimage((new_w - width) / 2, 0, width, height);
            } else {
                lastImage = newImage.getSubimage(0, (new_h - height) / 2, width, height);
            }
            gd.drawImage(lastImage, 0, 0, null);  //把商品图片加入海报
        }
    }


    /**
     * 指定长或者宽的最大值来压缩图片
     *
     * @param src: 源图片字母流
     */
    public static BufferedImage Tosmallerpic(BufferedImage src, int new_w, int new_h) {
        try {
//            int maxLength = 380;  //图片的高宽尺寸
            int old_w = src.getWidth();  // 得到源图宽
            int old_h = src.getHeight();  // 得到源图长
            BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = tempImg.createGraphics();
            g.setColor(Color.white);
            // 从原图上取颜色绘制新图
            g.fillRect(0, 0, old_w, old_h);
            g.drawImage(src, 0, 0, old_w, old_h, Color.white, (ImageObserver) null);
            g.dispose();

            BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, (ImageObserver) null);
            return newImg;
        } catch (Exception e) {
            log.error("重新绘制商品图片异常", e);
        }
        return null;
    }

}
