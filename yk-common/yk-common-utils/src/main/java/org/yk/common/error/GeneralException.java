package org.yk.common.error;

import lombok.Data;

/**
 * 通用异常
 * */
@Data
public class GeneralException extends RuntimeException{

	/**
     * 
     */
    private static final long serialVersionUID = -5613902686926706934L;

    private String errorCode;
    private String detailDescription;
    
    public GeneralException(String errorCode,String detailDescription){
		this.errorCode = errorCode;
		this.detailDescription = detailDescription;
	}
	
}
