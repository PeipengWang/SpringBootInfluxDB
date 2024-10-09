package demo;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;

/**
 * @description: 通过异步的方式向InfluxDB写入数据
 */
public class AsyncWrite {

    /** token 操作时需要换成自己的 **/
    private static final char[] token = "RP3Cti5DmGuhU5FB8Ubb5ij57LCXgpxWPA3NLOiqJROerC7FOe0SgdFwmAJPpMQYGMYv3-8D0n0VGEQkfl5ksw==".toCharArray();

    /** 组织名称 操作时需要换成自己的 **/
    private static String org = "telemetry";

    /** 存储桶名称 **/
    private static String bucket = "tp4_tmdata";

    /** InfluxDB服务的url **/
    private static String url = "http://159.75.251.138:8086/";

    public static void main(String[] args) {
        // 0.创建InfluxDB的客户端
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token, org, bucket);
        // 1.异步写入会创建一个守护线程，所以在makWriteApi时可以传递一些配置项，也就是WriteOptions对象
        WriteOptions options = WriteOptions.builder()
                .batchSize(999)
                .flushInterval(10000)
                .build();
        // 2.使用makeWriteApi创建的
        WriteApi writeApi = influxDBClient.getWriteApi(options);
        for (int i = 0; i < 999; i++) {
            writeApi.writeRecord(WritePrecision.MS,"temperature,location=south value=99");
        }

        // 3.关闭连接，此方法会触发一次刷写，将缓冲区中剩下的数据向InfluxDB写入一次。
        influxDBClient.close();
    }
}
