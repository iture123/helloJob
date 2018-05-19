package com.helloJob.vto;

import lombok.Data;
@Data
public class ComboboxVto {
	private String text;
	private String id;
	public ComboboxVto(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	public ComboboxVto() {
	}
}
