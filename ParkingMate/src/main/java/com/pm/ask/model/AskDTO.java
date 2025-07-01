package com.pm.ask.model;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AskDTO {

	private int idx;
	private String id;
	private String type;
	private String title;
	private String content;
	private String upload;
	private String comment;
	private int division;
}
