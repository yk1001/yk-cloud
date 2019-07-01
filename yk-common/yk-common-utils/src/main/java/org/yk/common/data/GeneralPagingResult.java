package org.yk.common.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带有分页数据的通用返回
 * */
@Data
@NoArgsConstructor
public class GeneralPagingResult<T> extends  GeneralContentResult<T> {

	/**
     * 
     */
    private static final long serialVersionUID = 5861439543969778397L;
    /**
	 * pageInfo: For paging result ONLY.
	 */
	private PageInfo pageInfo;
	
    public GeneralPagingResult(String resultCode, String detailDescription, T resultContent, PageInfo pageInfo) {
        super(resultCode, detailDescription, resultContent);
        this.pageInfo = pageInfo;
    }
	
    public GeneralPagingResult(String resultCode, T resultContent, PageInfo pageInfo) {
        super(resultCode, resultContent);
        this.pageInfo = pageInfo;
    }
	
}

