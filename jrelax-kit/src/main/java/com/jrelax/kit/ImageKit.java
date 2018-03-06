package com.jrelax.kit;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 图片工具类
 * 裁剪、缩放等
 */
public class ImageKit {
    private int redBins = 4;
    private int greenBins = 4;
    private int blueBins = 4;

    /*
     * 根据尺寸图片居中裁剪
     */
    public static void cutCenterImage(String src, String dest, int w, int h) throws IOException {
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(FileKit.getSuffix(src));
        ImageReader reader = iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2, (reader.getHeight(imageIndex) - h) / 2, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, FileKit.getSuffix(src), new File(dest));

        iis.close();
        in.close();
    }

    /*
     * 图片裁剪二分之一
     */
    public static void cutHalfImage(String src, String dest) throws IOException {
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(FileKit.getSuffix(src));
        ImageReader reader = iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        int width = reader.getWidth(imageIndex) / 2;
        int height = reader.getHeight(imageIndex) / 2;
        Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, FileKit.getSuffix(src), new File(dest));

        iis.close();
        in.close();
    }
    /*
     * 图片裁剪通用接口
     */

    public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(FileKit.getSuffix(src));
        ImageReader reader = iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, FileKit.getSuffix(src), new File(dest));

        iis.close();
        in.close();
    }

    /*
     * 图片缩放
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        Image Itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 计算图片相似度
     *
     * @param img1 图片1
     * @param img2 图片2
     */
    public double matchImage(String img1, String img2) {
        try {
            File desc = new File(img2);
            if (!desc.exists())
                return 0;
            double n = modelMatch(ImageIO.read(new File(img1)), ImageIO.read(desc));

            return n;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setRedBinCount(int redBinCount) {
        this.redBins = redBinCount;
    }

    public void setGreenBinCount(int greenBinCount) {
        this.greenBins = greenBinCount;
    }

    public void setBlueBinCount(int blueBinCount) {
        this.blueBins = blueBinCount;
    }

    /**
     * 计算直方图
     *
     * @param src
     * @param dest
     * @return
     */
    private float[] filter(BufferedImage src, BufferedImage dest) {
        int width = src.getWidth();
        int height = src.getHeight();

        int[] inPixels = new int[width * height];
        float[] histogramData = new float[this.redBins * this.greenBins * this.blueBins];
        getRGB(src, 0, 0, width, height, inPixels);
        int index = 0;
        int redIdx = 0, greenIdx = 0, blueIdx = 0;
        int singleIndex = 0;
        float total = 0;
        for (int row = 0; row < height; row++) {
            int tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                //ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                redIdx = (int) getBinIndex(redBins, tr, 255);
                greenIdx = (int) getBinIndex(greenBins, tg, 255);
                blueIdx = (int) getBinIndex(blueBins, tb, 255);
                singleIndex = redIdx + greenIdx * redBins + blueIdx * redBins * greenBins;
                histogramData[singleIndex] += 1;
                total += 1;
            }
        }

        for (int i = 0; i < histogramData.length; i++) {
            histogramData[i] = histogramData[i] / total;
        }
        return histogramData;
    }

    private float getBinIndex(int binCount, int color, int colorMaxValue) {
        float binIndex = (((float) color) / ((float) colorMaxValue))
                * ((float) binCount);
        if (binIndex >= binCount)
            binIndex = binCount - 1;
        return binIndex;
    }

    private int[] getRGB(BufferedImage image, int x, int y, int width,
                         int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            return (int[]) image.getRaster().getDataElements(x, y, width,
                    height, pixels);
        return image.getRGB(x, y, width, height, pixels, 0, width);
    }

    /**
     * 计算巴氏系数
     *
     * @param sourceImage
     * @param candidateImage
     * @return
     */
    private double modelMatch(BufferedImage sourceImage,
                              BufferedImage candidateImage) {
        float[] sourceData = filter(sourceImage, null);
        float[] candidateData = filter(candidateImage, null);
        double[] mixedData = new double[sourceData.length];
        for (int i = 0; i < sourceData.length; i++) {
            mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
        }
        // The values of Bhattacharyya Coefficient ranges from 0 to 1,
        double similarity = 0;
        for (int i = 0; i < mixedData.length; i++) {
            similarity += mixedData[i];
        }

        return similarity;
    }

    /**
     * 图片压缩
     *
     * @param fileName
     */
    public static void compressImage(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(fileName));

            BufferedImage target = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_USHORT_565_RGB);
            Graphics g = target.getGraphics();

            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

            ImageIO.write(target, "png", new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片压缩
     * @param image
     * @return
     */
    public static Thumbnails.Builder<File> thumb(File image){
        return Thumbnails.of(image);
    }
}
