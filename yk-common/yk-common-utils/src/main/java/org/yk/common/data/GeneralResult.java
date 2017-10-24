

package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class GeneralResult implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 908203438033789670L;

	private String resultCode;
	private String detailDescription;
}

