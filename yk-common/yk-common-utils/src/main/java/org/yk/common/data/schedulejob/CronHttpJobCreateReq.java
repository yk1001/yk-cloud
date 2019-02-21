package org.yk.common.data.schedulejob;

import java.io.Serializable;

import lombok.Data;

@Data
public class CronHttpJobCreateReq implements Serializable{

	private String name;
	private String group;
	private String url;
	private String method;
	private String content;
	private String contentType;
	private String cronExpression;
	
}
