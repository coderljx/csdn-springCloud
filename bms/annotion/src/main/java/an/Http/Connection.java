package an.Http;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    private final byte b[] = new byte[2048];

    private static HttpURLConnection con(String urlS) throws Exception {
        URL url = new URL(urlS);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty( "Content-Type","application/json;charset=UTF-8");
        urlConnection.setDoOutput(true);//是否输入参数
        urlConnection.setConnectTimeout(5000);
        return urlConnection;
    };

    public static void httpGet (String url) throws Exception {
        HttpURLConnection con = con(url);
    }

    public static void httpPost(String url) throws Exception {
        HttpURLConnection con = con(url);
        con.setRequestMethod("POST");

        con.getOutputStream();

    }


    public static void httpPost(JSONObject object) {
        // *创建默认的httpClient实例(CloseableHttpClient).
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // *创建HttpPost
        //        "http://localhost:5400/es/create"
        HttpPost httppost = new HttpPost("http://192.168.0.104:5400/es/create");
        // *创建参数队列
        UrlEncodedFormEntity uefEntity;
        try {
            HttpEntity httpEntity = new StringEntity(object + "","UTF-8");
//        *发送请求参数
            httppost.setEntity(httpEntity);
//        测试：输出请求地址
//        System.out.println("executing request " + httppost.getURI());
//       *调用HttpClient对象的execute发送请求方法并返回一个httpClient/CloseableHttpResponse对象
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
//       *调用HttpResponse的getEntity()方法可获取HttpEntity对象
//       HttpEntity-->StringEntity  解决乱码问题
                HttpEntity entity = response.getEntity();
//       获取响应内容以及响应头
                String message = EntityUtils.toString(entity, "UTF-8");
                Header[] allHeaders = response.getAllHeaders();
//       *关闭连接,释放资源
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
