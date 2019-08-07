package com.ishareread.common.utils.poi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.ishareread.common.utils.file.FileUtils;
import com.ishareread.framework.config.IshareConfig;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

public class WordUtil {
	
	/**
	 * 获取下载路径
	 * 
	 * @param filename 文件名称
	 */
	public String getAbsoluteFile(String filename) {
		String downloadPath = IshareConfig.getDownloadPath() + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}
	
	/**
	 * 将HTML输出doc文件
	 * 注：不能输出docx 否则只能用WPS打开
	 * @param body 主体内容 
	 * @param newFileName 生成的新文件名
	 * @param destPath 目标路径
	 * @throws IOException
	 */
	public void writeHtmlToWord(String body,String newFileName,String destPath){
		//拼一个标准的HTML格式文档
		InputStream is = null;
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(ResourceUtils.getFile("classpath:model/wrod_templates.html"))); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); 
            
            //头文件，模板文件，很重要
            StringBuffer headHtml = new StringBuffer();
            String line = null;  
            while ((line = br.readLine()) != null) {  
            	headHtml.append(line);
            }  
			is = new ByteArrayInputStream((headHtml.toString().replace("${body}", body)).getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(destPath+newFileName+".doc");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		POIFSFileSystem fs = new POIFSFileSystem();
		try {
			//对应于org.apache.poi.hdf.extractor.WordDocument
			fs.createDocument(is, "WordDocument");
			fs.writeFilesystem(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(os != null) {
					os.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将HTML输出pdf文件
	 * @param body 主体内容 
	 * @param newFileName 生成的新文件名
	 * @param destPath 目标路径
	 * @throws IOException
	 */
	public void writeHtmlToPdf(String body,String newFileName,String destPath){
		//拼一个标准的HTML格式文档
//		InputStream is = null;
		BufferedReader br = null;
		StringBuffer headHtml = null;
		try {
			//InputStreamReader reader = new InputStreamReader(new FileInputStream(ResourceUtils.getFile("classpath:model/pdf_templates.html"))); // 建立一个输入流对象reader
			InputStreamReader reader = new InputStreamReader(new FileInputStream(IshareConfig.getModelPath()+"pdf_templates.html")); // 建立一个输入流对象reader  
            br = new BufferedReader(reader); 
            
            //头文件，模板文件，很重要
            headHtml = new StringBuffer();
            String line = null;  
            while ((line = br.readLine()) != null) {  
            	headHtml.append(line);
            }  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(destPath+newFileName+".pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		try {
			String osName = System.getProperty("os.name");  
			if(osName.toLowerCase().startsWith("win")){  
				fontResolver.addFont("C://Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				System.out.println("win:"+osName);
			}else {
	           fontResolver.addFont("/usr/share/fonts/chinese/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	           System.out.println("linux:"+osName);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
//		System.out.println((headHtml.toString().replace("${body}", body)));
		
		renderer.setDocumentFromString((headHtml.toString().replace("${body}", body.replace("<br>", "<br/>"))));
		renderer.layout();
		try {
			renderer.createPDF(os);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally {
			if(os!=null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
