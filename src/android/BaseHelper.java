package com.yintong.pay.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * 工具类
 * 
 */
public class BaseHelper{
    public static final String PARAM_EQUAL = "=";
    public static final String PARAM_AND   = "&";

    /**
     * 流转字符串方法
     * 
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 显示dialog
     * 
     * @param context
     *            环境
     * @param strTitle
     *            标题
     * @param strText
     *            内容
     * @param icon
     *            图标
     */
    public static void showDialog(Activity context, String strTitle,
            String strText, int icon)
    {
        try
        {
            AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
            tDialog.setIcon(icon);
            tDialog.setTitle(strTitle);
            tDialog.setMessage(strText);
            tDialog.setPositiveButton("确定", null);
            tDialog.show();
        } catch (Exception e)
        {

        }
    }

    /**
     * 打印信息
     * 
     * @param tag
     *            标签
     * @param info
     *            信息
     */
    public static void log(String tag, String info)
    {
        Log.i(tag, info);
    }

    /**
     * 获取权限
     * 
     * @param permission
     *            权限
     * @param path
     *            路径
     */
    public static void chmod(String permission, String path)
    {
        try
        {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //
    // show the progress bar.
    /**
     * 显示进度条
     * 
     * @param context
     *            环境
     * @param title
     *            标题
     * @param message
     *            信息
     * @param indeterminate
     *            确定性
     * @param cancelable
     *            可撤销
     * @return
     */
    public static ProgressDialog showProgress(Context context,
            CharSequence title, CharSequence message, boolean indeterminate,
            boolean cancelable)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(false);

        dialog.show();
        return dialog;
    }

    /**
     * 字符串转json对象
     * 
     * @param str
     * @param split
     * @return
     */
    public static JSONObject string2JSON(String str, String split)
    {
        JSONObject json = new JSONObject();
        try
        {
            String[] arrStr = str.split(split);
            for (int i = 0; i < arrStr.length; i++)
            {
                String[] arrKeyValue = arrStr[i].split("=");
                json.put(arrKeyValue[0],
                        arrStr[i].substring(arrKeyValue[0].length() + 1));
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject string2JSON(String str)
    {
        try
        {
            return new JSONObject(str);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static String toJSONString(Object obj)
    {
        JSONObject json = new JSONObject();
        try
        {
            List<NameValuePair> list = bean2Parameters(obj);
            for (NameValuePair nv : list)
            {
                json.put(nv.getName(), nv.getValue());
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 将bean转换成键值对列表
     * 
     * @param bean
     * @return
     */
    public static List<NameValuePair> bean2Parameters(Object bean)
    {
        if (bean == null)
        {
            return null;
        }
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        // 取得bean所有public 方法
        Method[] Methods = bean.getClass().getMethods();
        for (Method method : Methods)
        {
            if (method != null && method.getName().startsWith("get")
                    && !method.getName().startsWith("getClass"))
            {
                // 得到属性的类名
                String value = "";
                // 得到属性值
                try
                {
                    String className = method.getReturnType().getSimpleName();
                    if (className.equalsIgnoreCase("int"))
                    {
                        int val = 0;
                        try
                        {
                            val = (Integer) method.invoke(bean);
                        } catch (InvocationTargetException e)
                        {
                            Log.e("InvocationTargetException", e.getMessage(),
                                    e);
                        }
                        value = String.valueOf(val);
                    } else if (className.equalsIgnoreCase("String"))
                    {
                        try
                        {
                            value = (String) method.invoke(bean);
                        } catch (InvocationTargetException e)
                        {
                            Log.e("InvocationTargetException", e.getMessage(),
                                    e);
                        }
                    }
                    if (value != null && value != "")
                    {
                        // 添加参数
                        // 将方法名称转化为id，去除get，将方法首字母改为小写
                        String param = method.getName().replaceFirst("get", "");
                        if (param.length() > 0)
                        {
                            String first = String.valueOf(param.charAt(0))
                                    .toLowerCase();
                            param = first + param.substring(1);
                        }
                        parameters.add(new BasicNameValuePair(param, value));
                    }
                } catch (IllegalArgumentException e)
                {
                    Log.e("IllegalArgumentException", e.getMessage(), e);
                } catch (IllegalAccessException e)
                {
                    Log.e("IllegalAccessException", e.getMessage(), e);
                }
            }
        }
        return parameters;
    }

    /**
     * 对Object进行List<NameValuePair>转换后按key进行升序排序，以key=value&...形式返回
     * 
     * @param list
     * @return
     */
    public static String sortParam(Object order)
    {
        List<NameValuePair> list = bean2Parameters(order);
        return sortParam(list);
    }

    /**
     * 对List<NameValuePair>按key进行升序排序，以key=value&...形式返回
     * 
     * @param list
     * @return
     */
    public static String sortParam(List<NameValuePair> list)
    {
        if (list == null)
        {
            return null;
        }
        Collections.sort(list, new Comparator<NameValuePair>(){
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs)
            {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        StringBuffer sb = new StringBuffer();
        for (NameValuePair nameVal : list)
        {
        	
            if (null != nameVal.getValue() && !"".equals(nameVal.getValue())
            		&& !nameVal.getName().equals("id_type")
					&& !nameVal.getName().equals("id_no")
					&& !nameVal.getName().equals("acct_name")
					&& !nameVal.getName().equals("flag_modify")
					&& !nameVal.getName().equals("user_id")
					&& !nameVal.getName().equals("no_agree")
					&& !nameVal.getName().equals("card_no")
					&& !nameVal.getName().equals("test_mode"))
            {
                sb.append(nameVal.getName());
                sb.append(PARAM_EQUAL);
                sb.append(nameVal.getValue());
                sb.append(PARAM_AND);
            }
        }
        String params = sb.toString();
        if (sb.toString().endsWith(PARAM_AND))
        {
            params = sb.substring(0, sb.length() - 1);
        }
        Log.v("待签名串", params);
        return params;
    }
}