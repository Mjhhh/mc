package com.edu.message.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FileUtils {
	/**
	 * 根据url拿取file
	 * 
	 * @param url
	 * @param suffix
	 *            文件后缀名
	 */
	public static File createFileByUrl(String url, String suffix) {
		byte[] byteFile = getImageFromNetByUrl(url);
		if (byteFile != null) {
			File file = getFileFromBytes(byteFile, suffix);
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl
	 *            网络连接地址
	 * @return
	 */
	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	// 创建临时文件
	private static File getFileFromBytes(byte[] b, String suffix) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = File.createTempFile("pattern", "." + suffix);
			System.out.println("临时文件位置：" + file.getCanonicalPath());
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	public static void main(String[] args) {
		// WebFileUtils.createFileByUrl("http://122.152.205.72:88/group1/M00/00/01/CpoxxFr7oIaAZ0rOAAC0d3GKDio580.png",
		// "png");
		// WebFileUtils.createImg("http://122.152.205.72:88/group1/M00/00/01/CpoxxFr7oIaAZ0rOAAC0d3GKDio580.png");
	}

	public static boolean base64ToFile(String filePath, String base64Data)  throws Exception {
		String dataPrix = "";
        String data = "";
        
        if(base64Data == null || "".equals(base64Data)){
            return false;
        }else{
            String [] d = base64Data.split("base64,");
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else{
                return false;
            }
        }

        // 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bs = Base64Utils.decodeFromString(data);
        // 使用apache提供的工具类操作流
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), bs);
        
        return true;
	}

	/**
	 * 本地图片转换成base64字符串
	 * @param imageName	图片名称
	 * @reture 图片Base64
	 */
	public static String ImageToBase64ByLocal(String imageName) throws IOException {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			//获取图片路径
			File file = new File(imageName);
			in = new FileInputStream(file.getPath());

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64Utils.encodeToString(data);
	}
}
