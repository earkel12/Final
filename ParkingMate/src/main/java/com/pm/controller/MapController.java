package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.booking.service.BookingService;
import com.pm.map.model.ParkingLotDTO;
import com.pm.map.service.MapService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService service;
    @Autowired
    private BookingService bservice;
    
    @ResponseBody
    @GetMapping("/list")
    public List<ParkingLotDTO> getParkingList() throws Exception {
        return service.plInfo();  // 전체 주차장 목록 JSON 응답
    }
    
    @ResponseBody
    @GetMapping("/search")
    public List<ParkingLotDTO> searchParking(@RequestParam("name") String name) throws Exception {
        return service.searchPl(name);
    }
    
    // AJAX 호출 응답용
    @ResponseBody
    @GetMapping("/parking")
    public ParkingLotDTO getParkingInfo(@RequestParam("name") String name) throws Exception {
    	
        return service.plbyname(name);
    }
    
    @ResponseBody
	@GetMapping("/count")
	public int bookingCount(int idx) throws Exception {
		
		return bservice.bookingCount(idx);
	}
	
    @GetMapping("/setSession")
    @ResponseBody
    public void setSessionInfo(@RequestParam("idx") int idx,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("price2") int price2,
            HttpSession session) {
        session.setAttribute("pidx", idx);
        session.setAttribute("pname", name);
        session.setAttribute("price", price);
        session.setAttribute("price2", price2);
    }

    // 초기 HTML 페이지 로드
    @GetMapping("/view")
    public String mapView() {
        return "map/mapAPI";  // templates/map/mapAPI.html
    }
    
    
}