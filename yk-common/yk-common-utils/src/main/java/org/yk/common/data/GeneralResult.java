

package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * 通用返回
 * */
@Data
public class GeneralResult implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 908203438033789670L;

	private String resultCode;
	private String detailDescription;
}

