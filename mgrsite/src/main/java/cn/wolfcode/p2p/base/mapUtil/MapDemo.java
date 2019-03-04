package cn.wolfcode.p2p.base.mapUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Component
@Slf4j
public class MapDemo {

    private static final Logger logger = LoggerFactory.getLogger(MapDemo.class);
    public static void main(String[] args) {
        //StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A");
        stringBuilder.append("B");
        stringBuilder.append("C");
        int arr[] = {1,2,3};
        System.out.println(stringBuilder);
//        logger.info(stringBuffer.toString());
//        System.out.println(arr[1]);
//        logger.info(MapDemo.getLatAndLogByName("成都市天府五街"));
    }

    /**
     * 高德api 根据地址获取经纬度
     *
     * @param name
     * @return
     */
    public static String getLatAndLogByName(String name) {
        String msg = null;
        try {
            name = URLEncoder.encode(name,"UTF-8");
            String serverUrl = "https://restapi.amap.com/v3/geocode/geo?address=" + name + "&output=JSON&key=9a405fcfb77b3da8dbd1a7b5b644be3c";
            logger.info("请求url :" + serverUrl);
            StringBuffer s = new StringBuffer();
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                s.append(line);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(s.toString());
            JSONArray arr = jsonObject.getJSONArray("geocodes");
            JSONObject joRoad = null;
            for (int i = 0; i < arr.length(); i++) {
                joRoad = arr.getJSONObject(0);
            }
            msg = (String) joRoad.get("location");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

}
