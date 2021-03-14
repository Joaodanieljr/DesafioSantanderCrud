package com.joaodanieljr.desafio.exceptions;

import com.joaodanieljr.desafio.resources.exceptions.StandardError;

public class FormatException  extends StandardError {

	private static final long serialVersionUID = 1L;
	
	public FormatException(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		
	}

}
