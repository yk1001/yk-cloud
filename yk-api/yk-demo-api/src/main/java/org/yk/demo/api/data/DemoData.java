package org.yk.demo.api.data;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "测试类")
public class DemoData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4527129367544644002L;
	
	@ApiModelProperty(notes = "id")
	private String id;
	@ApiModelProperty(notes = "name")
	private String name;
	
}
