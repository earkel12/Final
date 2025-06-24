package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.map.model.ParkingLotDTO;
import com.pm.map.service.MapService;

@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService service;

    // AJAX 호출 응답용
    @ResponseBody
    @GetMapping("/parking")
    public ParkingLotDTO getParkingInfo(@RequestParam("name") String name) throws Exception {
        return service.plInfo(name);
    }

    // 초기 HTML 페이지 로드
    @GetMapping("/view")
    public String mapView() {
        return "map/mapAPI";  // templates/map/mapAPI.html
    }
}