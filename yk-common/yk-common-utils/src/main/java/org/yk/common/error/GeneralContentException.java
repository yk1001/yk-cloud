package org.yk.common.error;

import lombok.Data;

/**
 * 带有数据的通用异常
 * */
@Data
public class GeneralContentException extends GeneralException{

    /**
     * 
     */
    private static final long serialVersionUID = -2391162855854733184L;
    
    private Object resultContent;
    
    public GeneralContentException(String errorCode,String detailDescription,Object resultContent){
        super(errorCode,detailDescription);
        this.resultContent = resultContent;
    }
    
}
