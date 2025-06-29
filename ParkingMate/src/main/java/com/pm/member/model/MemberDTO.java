package com.pm.member.model;

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
public class MemberDTO {

	private String id;
	private String pwd;
	private String name;
	private String resident_num;
	private String tel;
	private String email;
	private int mate_use;
	private int point;
}
