package org.yk.common.data.schedulejob;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class CronServiceJobCreateReq implements Serializable{

	private String name;
	private String group;
	private String serviceName;
	private String uri;
	private String method;
	// json or jsonarray
	private Object content;
	private String cronExpression;
	
}
