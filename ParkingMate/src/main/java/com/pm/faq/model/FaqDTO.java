package com.pm.faq.model;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FaqDTO {

	private int idx;
	private String title;
	private String content;
	private Date writedate;
}
