package org.yk.common.data.datamgmt;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddEmployeeResp implements Serializable{
	
	private String id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String about;

}
