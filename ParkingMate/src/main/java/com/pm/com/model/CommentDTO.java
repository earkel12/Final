package com.pm.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	
	private int idx;
	private int idx2;
	private String id;
	private String content;
	private int ref;
	private int lev;
	private int sunbun;
	
}
