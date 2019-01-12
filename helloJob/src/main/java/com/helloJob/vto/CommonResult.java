package com.helloJob.vto;

import lombok.Data;

@Data
public class CommonResult {
	private boolean isSuccess;
	private String msg;
	public CommonResult(boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}
	public CommonResult() {
	}
	
}
