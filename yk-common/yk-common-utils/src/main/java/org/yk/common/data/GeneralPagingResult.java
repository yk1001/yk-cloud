package org.yk.common.data;

import lombok.Data;

/**
 * 带有分页数据的通用返回
 * */
@Data
public class GeneralPagingResult<T> extends  GeneralContentResult<T> {

	/**
     * 
     */
    private static final long serialVersionUID = 5861439543969778397L;
    /**
	 * pageInfo: For paging result ONLY.
	 */
	private PageInfo pageInfo;
}

