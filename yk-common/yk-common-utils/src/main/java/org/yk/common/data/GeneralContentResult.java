package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;


@Data
public class GeneralContentResult<T> implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -8104955278209569617L;

	private String resultCode;
	private String detailDescription;
	private T resultContent;
}

