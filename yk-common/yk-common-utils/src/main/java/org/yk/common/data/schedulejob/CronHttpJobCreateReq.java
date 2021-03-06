package org.yk.common.data.schedulejob;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class CronHttpJobCreateReq implements Serializable{

	private String name;
	private String group;
	private String url;
	private String method;
	// json or jsonarray
	private Object content;
	private String cronExpression;
	
}
