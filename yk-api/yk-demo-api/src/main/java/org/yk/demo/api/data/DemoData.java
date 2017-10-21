package org.yk.demo.api.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class DemoData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4527129367544644002L;
	
	private String id;
	private String name;
	
}
