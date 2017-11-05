package org.yk.common.data.demoapi;

import java.io.Serializable;

import lombok.Data;

@Data
public class Download implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6324788107572960582L;
	private Byte type;
	private String path;
	
}
