
var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

var llpayExport = {};

/*
 * set options:
 *  {
 *   }
 */
llpayExport.setOptions = function(options, successCallback, failureCallback) {
	  if(typeof options === 'object') {
		  cordova.exec( successCallback, failureCallback, 'LLPay', 'setOptions', [options] );
	  } else {
		  if(typeof failureCallback === 'function') {
			  failureCallback('options should be specified.');
		  }
	  }
};

/*
var argsExample = {
		"oid_partner":"201103171000000000",
		"dt_order":"20130515094013",
		"no_order":"2013051500001",
		"busi_partner":"101001",
		"name_goods":"羽毛球",
		"info_order":"用户13958069593购买羽毛球3桶",
		"money_order":"210.97",
		"notify_url":"http://payhttp.xiaofubao.com/xxx/back.shtml",
		"pay_type":"2",
		"bank_code":"01020000",
		"force_bank":"1",
		"sign_type":"RSA",
		"valid_order":"30",
		"sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/" +
		"+p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtXE+vP" +
		"TfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
};
*/

llpayExport.startLLPay = function(args, successCallback, failureCallback) {
	  if(typeof options === 'object') {
			cordova.exec( successCallback, failureCallback, 'LLPay', 'startPay', [ args ] );
	  } else {
		  if(typeof failureCallback === 'function') {
			  failureCallback('args should be specified.');
		  }
	  }
};

/*
document.addEventListener('onLLPayEnd', function(data){
	var retCode = data.retCode;
	var dict = data.dict;
	
});

var retExample = {
		"ret_code":"0000",
		"ret_msg":"交易成功",
		"oid_partner":"201103171000000000",
		"dt_order":"20130515094013",
		"no_order":"2013051500001",
		"oid_paybill":"2013051613121201",
		"money_order":"210.97",
		"result_pay":"SUCCESS",
		"settle_date":"20130516",
		"info_order":"用户13958069593购买了3桶羽毛球",
		"sign_type":"RSA",
		"sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/" +
		"+p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtX"
};

*/

module.exports = llpayExport;

