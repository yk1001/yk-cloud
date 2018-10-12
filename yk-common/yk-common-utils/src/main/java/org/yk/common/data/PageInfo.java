

package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageInfo implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -7644066325412964122L;

	/**
	 * Required. currentPage:the No. of current page.
	 */
	private Integer currentPage;
	/**
	 * Required. totalPage: Total count of the pages.
	 */
	private Integer totalPage;
	/**
	 * Required. pageSize: the size of the page.
	 */
	private Integer pageSize;
	/**
	 * Optional. totalRecords: total records of the entities.
	 */
	private Long totalRecords;
}
