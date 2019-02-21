package org.yk.common.data.schedulejob;

import java.io.Serializable;

import lombok.Data;

@Data
public class CronHttpJobCreateResp implements Serializable{

	private String name;
	private String group;
}
