package com.fundgroup.backend.utils.verifyCodeUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

public class VerifyCode {

//  our paras of vc: len of vc code
  private final int len = 4;
//  干扰线 条数
  private final int num = 5;
  private final int width = 100;
  private final int height = 50;
  private final String[] fontNames = {"宋体", "楷体", "隶书", "微软雅黑"};

//  bg = white
  private final Color bgColor = new Color(255, 255, 255);
  private final Random random = new Random();

//  fixed: from this set
  private final String codes;
//  our verify code at last
  private String text;

  public VerifyCode() {
    StringBuilder stringBuilder = new StringBuilder("");
    for(char c = '0'; c <= '9'; ++c){
      stringBuilder.append(c);
    }
    for(char c = 'a'; c <= 'z'; ++c){
      stringBuilder.append(c);
    }
    for(char c = 'A'; c <= 'Z'; ++c){
      stringBuilder.append(c);
    }
    codes = stringBuilder.toString();
  }

  public static void output(BufferedImage image, OutputStream out) throws IOException {
    ImageIO.write(image, "JPEG", out);
  }

  /**
   * A random color, just tool class
   *
   * @return
   */
  private Color randomColor() {
    int red = random.nextInt(150);
    int green = random.nextInt(150);
    int blue = random.nextInt(150);
    return new Color (red, green, blue);
  }

  /**
   * a random font
   *
   * @return
   */
  private Font randomFont() {
    String name = fontNames[random.nextInt(fontNames.length)];
    int style = random.nextInt(4);
    int size = random.nextInt(5) + 24;
    return new Font (name, style, size);
  }

  /**
   * a random char
   *
   * @return
   */
  private char randomChar() {
    return codes.charAt(random.nextInt(codes.length()));
  }

  /**
   * an empty BufferedImage
   *
   * @return
   */
  private BufferedImage createImage() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = (Graphics2D) image.getGraphics();
    g2.setColor(bgColor);// 设置验证码图片的背景颜色
    g2.fillRect(0, 0, width, height);
    return image;
  }

  public BufferedImage getImage() {
    BufferedImage image = createImage();
    Graphics2D g2 = (Graphics2D) image.getGraphics();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < len; i++) {
      String s = randomChar() + "";
      sb.append(s);
      g2.setColor(randomColor());
      g2.setFont(randomFont());
      float x = i * width * 1.0f / 4;
      g2.drawString(s, x, height - 15);
    }
    this.text = sb.toString();
    drawLine(image);
    return image;
  }

  /**
   * 绘制干扰线
   *
   * @param image
   */
  private void drawLine(BufferedImage image) {
    Graphics2D g2 = (Graphics2D) image.getGraphics();

    for (int i = 0; i < num; i++) {
      int x1 = random.nextInt(width);
      int y1 = random.nextInt(height);
      int x2 = random.nextInt(width);
      int y2 = random.nextInt(height);
      g2.setColor(randomColor());
      g2.setStroke(new BasicStroke(1.5f));
      g2.drawLine(x1, y1, x2, y2);
    }
  }

  public String getText() {
    return text;
  }
}
