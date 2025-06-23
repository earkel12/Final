package com.pm.notion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.NotionMapper;

@Service
public class NotionServiceImple implements NotionService {
	@Autowired
	private NotionMapper mapper;
}
