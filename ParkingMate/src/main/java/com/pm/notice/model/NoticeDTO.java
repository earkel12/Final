package com.pm.notice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

	private int idx;
	private String id;
	private String title;
	private String content;
	private Date writedate;
	private int readnum;
	private int division;

}
