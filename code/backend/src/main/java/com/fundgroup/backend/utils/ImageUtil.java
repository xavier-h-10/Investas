package com.fundgroup.backend.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class ImageUtil {

  public static String getImgStr(String imgFilePath) {
    // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    File file=new File(imgFilePath);
    InputStream in;
    byte[] data = null;
    // 读取图片字节数组
    try {
      in = new FileInputStream(file);
      data = new byte[in.available()];
      in.read(data);
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Base64.Encoder encoder= Base64.getEncoder();
    return new String(encoder.encode(data));
  }
}
