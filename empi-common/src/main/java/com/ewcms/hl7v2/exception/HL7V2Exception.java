package com.ewcms.hl7v2.exception;

import org.springframework.core.NestedRuntimeException;

/**
 *
 * @author wu_zhijun
 */
public class HL7V2Exception extends NestedRuntimeException{
	
	private static final long serialVersionUID = 4915257705528608082L;

	public HL7V2Exception(String msg) {
        super(msg);
    }

    public HL7V2Exception(String msg, Throwable cause) {
        super(msg, cause);
    }
}
