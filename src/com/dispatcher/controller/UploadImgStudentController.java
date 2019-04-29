package com.dispatcher.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/uploadImgStudent")
public class UploadImgStudentController {
	File tmpDir = null;//初始化上传文件的临时存放目录
    File saveDir = null;//初始化上传文件后的保存目录
       
	@RequestMapping("/uploadImgStudent.do")
	@ResponseBody
	public Object uploadImgStudent(HttpServletRequest request) {
		String result = "失败";
		String tmpPath = "d:tmpdir";
		String savePath = "d:updir";
		tmpDir = new File(tmpPath);
		saveDir = new File(savePath);
		if (!tmpDir.isDirectory())
			tmpDir.mkdir();
		if (!saveDir.isDirectory())
			saveDir.mkdir();
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tmpDir);// 指定上传文件的临时目录
				dff.setSizeThreshold(1024000);// 指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setFileSizeMax(5000000);// 指定单个上传文件的最大尺寸
				sfu.setSizeMax(10000000);// 指定一次上传多个文件的总尺寸
				FileItemIterator fii = sfu.getItemIterator(request);// 解析request 请求,并返回FileItemIterator集合
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();// 从集合中获得一个文件流
					if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
						String fileName = fis.getName().substring(fis.getName().lastIndexOf(""));// 获得上传文件的文件名
						BufferedInputStream in = new BufferedInputStream(fis.openStream());// 获得文件输入流
						BufferedOutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(saveDir + fileName)));// 获得文件输出流
						Streams.copy(in, out, true);// 开始把文件写到你指定的上传文件夹
					}
				}

				result = "File upload successfully!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}          
}
