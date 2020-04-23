package com.ch.weixincp.controller;

import com.ch.weixincp.utils.OkHttpCli;
import com.ch.weixincp.ase.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Date;

@RestController
public class wxController {
    @Autowired
    private OkHttpCli okHttpCli;

    String TOTKN = "qVTatT49";
    String ENCODING_AESKEY = "wETIijI8kvlO9TJ5ylymGzXgvfxQur6jjJhESo75O42";
    String CORP_ID = "wwa17b0b6533f107bb";

    //AgentId
    //1000013
    //Secret
    //-p7wgAlbzUS2pDh0Q82qI-kEGx5GXZZO8bzcvu_WI3s

//http://web01.findbug.work:8282/checjToken

    /**
     * @Description
     * @Author 林乐福
     * @Date 2020/4/9 16:48
     * @Param [msg_signature, timestamp, nonce, echostr]
     * @Return java.lang.String
     * @Exception
     */
    @GetMapping("/verify")
    public String checkToken(@RequestParam String msg_signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {


        String sEchoStr; //需要返回的明文
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(TOTKN, ENCODING_AESKEY, CORP_ID);
            sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,
                    nonce, echostr);
            System.out.println("verifyurl echostr: " + sEchoStr);
            return sEchoStr;
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            e.printStackTrace();
            return "";
        }

    }

    @PostMapping("/verify")
    public String getUserMessage(@RequestParam String msg_signature, @RequestParam String timestamp, @RequestParam String nonce,@RequestBody String data) {


        String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(TOTKN, ENCODING_AESKEY, CORP_ID);
            String sMsg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, data);
            System.out.println("after decrypt msg: " + sMsg);
            // TODO: 解析出明文xml标签的内容进行处理
            // For example:
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(sMsg);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Content");
            String Content = nodelist1.item(0).getTextContent();
            System.out.println("Content：" + Content);
            return Content;
        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            e.printStackTrace();
            return "";
        }


    }

    @GetMapping("/gettoken")
    public String gettoken(){
        String corpsecret="-p7wgAlbzUS2pDh0Q82qI-kEGx5GXZZO8bzcvu_WI3s";
        String corpid ="wwa17b0b6533f107bb";
String gettoken_url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
        String url = "https://www.baidu.com/";
        String message = okHttpCli.doGet(gettoken_url);
        System.out.printf("access_token = "+message);
        return message;
    }


    @GetMapping("/test")
    public String test() {
        return "test ok";
    }


    @GetMapping("/sendmsg")
    public String sendmsg() {
        Date date=new Date();
        long timestamp=date.getTime();

        String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
        String sReqTimeStamp=timestamp+"";
        String sReqNonce=timestamp+"";

        try{
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(TOTKN, ENCODING_AESKEY, CORP_ID);
            String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
            System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);
            // 加密成功
            // TODO:
            // HttpUtils.SetResponse(sEncryptMsg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            // 加密失败
        }


        return "test ok";
    }


}
