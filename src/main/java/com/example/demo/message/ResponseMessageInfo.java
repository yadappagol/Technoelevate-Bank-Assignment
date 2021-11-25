package com.example.demo.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.demo.dto.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageInfo implements Serializable {

	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	private boolean error;
	private String message;
	private Object details;

	public ResponseMessageInfo(boolean error, String message, List<AccountHolder> details) {
		this.error = error;
		this.message = message;

		for (AccountHolder accountHolder : details) {
			this.details = accountHolder;
		}

	}

}
