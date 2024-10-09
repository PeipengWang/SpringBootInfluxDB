package com.example.springbootinfluxdb.controller;

import com.example.springbootinfluxdb.service.InfluxDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/influx")
public class InfluxDBController {

    @Autowired
    private InfluxDBService influxDBService;

    @PostMapping("/write")
    public String write(@RequestParam String measurement, 
                        @RequestParam String location, 
                        @RequestParam double value) {
        influxDBService.writeData(measurement, location, value);
        return "Data written successfully!";
    }

    @GetMapping("/query")
    public String query() {
        influxDBService.queryData();
        return "Query executed!";
    }
}
