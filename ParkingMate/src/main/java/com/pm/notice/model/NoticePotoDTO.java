package com.pm.notice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticePotoDTO {
	private int idx;
	private int noticeNum;
	private String poto1;
	private String poto2;
	private String poto3;
	private String poto4;
}
