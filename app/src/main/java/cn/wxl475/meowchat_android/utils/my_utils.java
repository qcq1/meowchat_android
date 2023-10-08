package cn.wxl475.meowchat_android.utils;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class my_utils {
    public static String concat_url_params(String url, LinkedHashMap<String,String> params){
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            String value=null;
            try {
                value= URLEncoder.encode(values.next(),"utf-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()).append("=").append(value);
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
        }
        return url + stringBuffer.toString();
    }
}
