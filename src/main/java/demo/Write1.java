package demo;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;

import java.time.Instant;

/**
 */
public class Write1 {

    /** token 操作时需要换成自己的 **/
    private static final char[] token = "RP3Cti5DmGuhU5FB8Ubb5ij57LCXgpxWPA3NLOiqJROerC7FOe0SgdFwmAJPpMQYGMYv3-8D0n0VGEQkfl5ksw==".toCharArray();

    /** 组织名称 操作时需要换成自己的 **/
    private static String org = "telemetry";

    /** 存储桶名称 **/
    private static String bucket = "tp4_tmdata";

    /** InfluxDB服务的url **/
    private static String url = "http://159.75.251.138:8086/";

    public static void main(String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token, org, bucket);
        WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();

        // 0. 使用InflxuDB行协议写入
//        writeApiBlocking.writeRecord(WritePrecision.MS,"temperature,location=north value=50");

        // 1. 使用Point写入
//        Point point = Point.measurement("temperature")
//                .addTag("location", "west")
//                .addField("value", 38.0)
//                .time(Instant.now(),WritePrecision.NS)
//                ;
//        writeApiBlocking.writePoint(point);

        // 2. 使用POJO类写入
        DemoPOJO demoPOJO = new DemoPOJO("east", 22.2, Instant.now());
        writeApiBlocking.writeMeasurement(WritePrecision.MS,demoPOJO);

        // 3. 调用close方法会关闭并释放一些比如守护线程之类的对象。
        influxDBClient.close();
    }
}
