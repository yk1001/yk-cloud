

package org.yk.common.data;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回
 * */
@Data
@NoArgsConstructor
public class GeneralResult implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 908203438033789670L;

	private String resultCode;
	private String detailDescription;
	
	public GeneralResult(String resultCode,String detailDescription){
	    this.resultCode = resultCode;
	    this.detailDescription = detailDescription;
	}
	
	public GeneralResult(String resultCode){
        this(resultCode, null);
    }
}

