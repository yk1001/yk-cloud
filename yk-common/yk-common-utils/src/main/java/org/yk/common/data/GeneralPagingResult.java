package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class GeneralPagingResult<T> implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 1540315626080625718L;

	private String resultCode;
	private String detailDescription;
	private T resultContent;
	
	/**
	 * pageInfo: For paging result ONLY.
	 */
	private PageInfo pageInfo;
}

