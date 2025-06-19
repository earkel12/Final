package com.pm.member.model;

import java.sql.*;

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
	private String residentnum;
	private String tel;
	private String email;
	private int matenum;
	private int point;
}
