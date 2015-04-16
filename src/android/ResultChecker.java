package com.yintong.pay.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

/**
 * 对签名进行验签
 * 
 */
public class ResultChecker{

    public static final int RESULT_INVALID_PARAM          = 0;
    public static final int RESULT_CHECK_SIGN_FAILED      = 1;
    public static final int RESULT_CHECK_SIGN_TYPE_FAILED = -1;
    public static final int RESULT_CHECK_SIGN_SUCCEED     = 2;

    String                  mContent;

    public ResultChecker(String content)
    {
        this.mContent = content;
    }

    /**
     * 对签名进行验签
     * 
     * @return
     */
    public int checkSign()
    {
        int retVal = RESULT_CHECK_SIGN_SUCCEED;

        try
        {
            JSONObject objContent = BaseHelper.string2JSON(this.mContent);

            // 获取待签名数据
            String signContent = getSignContent(objContent);
            Log.i("ResultChecker", "支付结果待签名数据：" + signContent);
            // 获取签名类型
            String signType = objContent.optString("sign_type");
            // 获取签名
            String sign = objContent.optString("sign");
            // 进行验签 返回验签结果
            if (signType.equalsIgnoreCase("RSA"))
            {
                if (!Rsa.doCheck(signContent, sign, EnvConstants.RSA_YT_PUBLIC))
                {
                    retVal = RESULT_CHECK_SIGN_FAILED;
                    Log.e("ResultChecker", "RESULT_CHECK_SIGN_FAILED");
                }
            } else if(signType.equalsIgnoreCase("MD5"))
            {
            	if (!Md5Algorithm.getInstance().doCheck(signContent, sign,  EnvConstants.MD5_KEY))
                {
                    retVal = RESULT_CHECK_SIGN_FAILED;
                    Log.e("ResultChecker", "RESULT_CHECK_SIGN_FAILED");
                }
            }else
            {
                Log.e("ResultChecker", "RESULT_CHECK_SIGN_TYPE_FAILED");
                retVal = RESULT_CHECK_SIGN_TYPE_FAILED;
            }
        } catch (Exception e)
        {
            retVal = RESULT_INVALID_PARAM;
            e.printStackTrace();
        }
        return retVal;
    }

    private String getSignContent(JSONObject objResult)
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for (Iterator<?> it = objResult.keys(); it.hasNext();)
        {
            String key = (String) it.next();
            // ret_code、ret_msg、sign不参与签名
            if ("ret_code".equalsIgnoreCase(key)
                    || "ret_msg".equalsIgnoreCase(key)
                    || "sign".equalsIgnoreCase(key)
                    || "agreementno".equalsIgnoreCase(key))
            {
                continue;
            }
            parameters
                    .add(new BasicNameValuePair(key, objResult.optString(key)));
        }
        String content = BaseHelper.sortParam(parameters);
        return content;
    }
}