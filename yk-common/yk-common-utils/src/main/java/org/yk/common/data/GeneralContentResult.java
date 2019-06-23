package org.yk.common.data;

import lombok.Data;

/**
 * 带有内容的通用返回
 * */
@Data
public class GeneralContentResult<T> extends GeneralResult{

	/**
     * 
     */
    private static final long serialVersionUID = 8375036943068586159L;
    private T resultContent;
}

