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
	File tmpDir = null;//��ʼ���ϴ��ļ�����ʱ���Ŀ¼
    File saveDir = null;//��ʼ���ϴ��ļ���ı���Ŀ¼
       
	@RequestMapping("/uploadImgStudent.do")
	@ResponseBody
	public Object uploadImgStudent(HttpServletRequest request) {
		String result = "ʧ��";
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
				DiskFileItemFactory dff = new DiskFileItemFactory();// �����ö���
				dff.setRepository(tmpDir);// ָ���ϴ��ļ�����ʱĿ¼
				dff.setSizeThreshold(1024000);// ָ�����ڴ��л������ݴ�С,��λΪbyte
				ServletFileUpload sfu = new ServletFileUpload(dff);// �����ö���
				sfu.setFileSizeMax(5000000);// ָ�������ϴ��ļ������ߴ�
				sfu.setSizeMax(10000000);// ָ��һ���ϴ�����ļ����ܳߴ�
				FileItemIterator fii = sfu.getItemIterator(request);// ����request ����,������FileItemIterator����
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();// �Ӽ����л��һ���ļ���
					if (!fis.isFormField() && fis.getName().length() > 0) {// ���˵����з��ļ���
						String fileName = fis.getName().substring(fis.getName().lastIndexOf(""));// ����ϴ��ļ����ļ���
						BufferedInputStream in = new BufferedInputStream(fis.openStream());// ����ļ�������
						BufferedOutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(saveDir + fileName)));// ����ļ������
						Streams.copy(in, out, true);// ��ʼ���ļ�д����ָ�����ϴ��ļ���
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
