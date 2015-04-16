package com.rjfun.cordova.lianlianpay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.rjfun.cordova.ext.CordovaPluginExt;
import com.yintong.pay.utils.BaseHelper;
import com.yintong.pay.utils.Constants;
import com.yintong.pay.utils.MobileSecurePayer;

public class LLPayPlugin extends CordovaPluginExt {
	private static final String LOGTAG = "LLPayPlugin";
	
    /** Cordova Actions. */
	public static final String ACTION_SET_OPTIONS = "setOptions";
	public static final String ACTION_START_LLPAY = "startLLPay";

    /* options */
    public static final String OPT_IS_TESTING = "isTesting";
    public static final String OPT_LOG_VERBOSE = "logVerbose";

	protected boolean isTesting = false;
	protected boolean logVerbose = false;

	protected String __getProductShortName() {
		return "LLPay";
	}

    @Override
    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;
        
    	if (ACTION_SET_OPTIONS.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            this.setOptions(options);
            result = new PluginResult(Status.OK);
            
        } else if (ACTION_START_LLPAY.equals(action)) {
            JSONObject args = inputs.optJSONObject(0);
        	
        	boolean isOk = this.startLLPay( args );
        	result = new PluginResult(isOk ? Status.OK : Status.ERROR);
            
        } else {
            Log.w(LOGTAG, String.format("Invalid action passed: %s", action));
            result = new PluginResult(Status.INVALID_ACTION);
        }
        
        if(result != null) sendPluginResult(result, callbackContext);
        
        return true;
    }
    
    @Override
    protected void pluginInitialize() {
    	super.pluginInitialize();
    	
	}
	
    public void setOptions(JSONObject options) {
    	if(options != null) {
    		if(options.has(OPT_IS_TESTING)) this.isTesting = options.optBoolean(OPT_IS_TESTING);
    		if(options.has(OPT_LOG_VERBOSE)) this.logVerbose = options.optBoolean(OPT_LOG_VERBOSE);
    	}
    }
    
    public final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
	
	public boolean startLLPay(JSONObject args) {
    	Log.d(LOGTAG, "startLLPay" );
    	
    	String content4Pay = args.toString();
    	MobileSecurePayer msp = new MobileSecurePayer();
        boolean bRet = msp.pay(content4Pay, mHandler, Constants.RQF_PAY, this.getActivity(), false);
    	
    	return bRet;
	}
	
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String strRet = (String) msg.obj;
            switch (msg.what) {
                case Constants.RQF_PAY: {
                	fireEvent("LLPay","onLLPayEnd", strRet);
                	
                    //JSONObject objContent = BaseHelper.string2JSON(strRet);
                	//fireEvent("LLPay","onLLPayEnd",objContent.toString());
                }
                break;
            }
            super.handleMessage(msg);
        }
    };

}
