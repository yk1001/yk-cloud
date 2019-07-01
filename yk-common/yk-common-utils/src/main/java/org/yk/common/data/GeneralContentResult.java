package org.yk.common.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带有内容的通用返回
 * */
@Data
@NoArgsConstructor
public class GeneralContentResult<T> extends GeneralResult{

	/**
     * 
     */
    private static final long serialVersionUID = 8375036943068586159L;
    private T resultContent;
    
    public GeneralContentResult(String resultCode, String detailDescription, T resultContent) {
        super(resultCode, detailDescription);
        this.resultContent = resultContent;
    }
    
    public GeneralContentResult(String resultCode, T resultContent) {
        super(resultCode);
        this.resultContent = resultContent;
    }
}

