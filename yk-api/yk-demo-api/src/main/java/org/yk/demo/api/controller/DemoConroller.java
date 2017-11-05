package org.yk.demo.api.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.data.demoapi.Download;
import org.yk.common.data.demomgmt.DemoData;

import io.swagger.annotations.ApiOperation;

@RestController
public class DemoConroller {

	@RequestMapping(value="/noauth/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData){
		return demoData;
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData){
		return demoData;
	}
	
	@RequestMapping(value="/noauth/download",method = RequestMethod.GET)
	@ApiOperation(value = "服务器文件下载", notes = "服务器文件下载")
	public void download(@RequestAttribute Download download,HttpServletResponse resp){
		try {
			String filename = download.getPath();
			resp.setHeader("Content-Disposition", "attachment;filename="+filename);  
	        //System.out.println(fullFileName);  
	        //读取文件  
	        InputStream in = new FileInputStream(filename);  
	        OutputStream out = resp.getOutputStream();  
	        
	        int b;  
	        while((b=in.read())!= -1)  
	        {  
	            out.write(b);  
	        }  
	          
	        in.close();  
	        out.close();  
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
