package com.example.springbootinfluxdb.service;

import com.example.springbootinfluxdb.config.InfluxDBConfig;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class InfluxDBService {

    @Autowired
    private InfluxDBClient influxDBClient;

    // 写入数据到 InfluxDB
    public void writeData(String measurement, String location, double value) {
        try {
            Point point = Point.measurement(measurement)
                    .addTag("location", location)
                    .addField("value", value)
                    .time(Instant.now(), WritePrecision.NS);

            influxDBClient.getWriteApiBlocking().writePoint(point);
            System.out.println("Data written successfully!");
        } catch (InfluxException e) {
            System.err.println("Failed to write data to InfluxDB: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // 查询数据
    public void queryData() {
        String bucket = "tp4_tmdata";
        String fluxQuery = "from(bucket:\"" + bucket + "\") |> range(start: -24h)";
        try {
            influxDBClient.getQueryApi().query(fluxQuery).forEach(table -> {
                table.getRecords().forEach(record -> {
                    System.out.println("Query result: " + record.getValueByKey("value"));
                });
            });
        } catch (InfluxException e) {
            System.err.println("Failed to query data from InfluxDB: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
