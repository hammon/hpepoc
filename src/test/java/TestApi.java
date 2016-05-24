import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TestApi {

    Jetty jetty = null;
    HttpUtils http = null;

    private static final String BASE_URL = "http://127.0.0.1:9090/api/test";

    @BeforeClass
    public  void init(){
        jetty = new Jetty();

        jetty.start(9090);

        http = new HttpUtils();
    }

    @Test
    public void test200(){

        String res = http.get(BASE_URL + "?responseCode=200");

        Assert.assertEquals(200,http.getResponseCode());

        Assert.assertEquals(res,"200");
    }

    @Test
    public void test500(){

        String res = http.get(BASE_URL + "?responseCode=500");

        Assert.assertEquals(500,http.getResponseCode());

        Assert.assertEquals(res,"Server returned HTTP response code: 500 for URL: " + BASE_URL + "?responseCode=500");
    }

    @Test
    public void testHello(){
        String res = http.get(BASE_URL);

        Assert.assertEquals(200,http.getResponseCode());

        Assert.assertEquals(res,"Hello!");
    }

    @Test
    public void testContent(){
        String content = "{'user':'Michael','msg':'hello Michael'}";
        String res = null;

        String encoded = null;
        try {
            encoded = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        res = http.get(BASE_URL + "?content=" + encoded);

        //Assert.assertEquals(200,http.getResponseCode());

        Assert.assertEquals(res,content);
    }
}
