package com.pm.reser.service;

import java.util.List;

import com.pm.reser.model.ReserDTO;
import com.pm.reser.model.UserCarDTO;

public interface ReserService {	
	public void insertReser(ReserDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
	
}
