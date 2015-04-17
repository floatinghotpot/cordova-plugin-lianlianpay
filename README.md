# cordova-plugin-lianlianpay
Cordova/PhoneGap plugin for LianLianPay SDK

* [连连支付 LianLian Pay](http://open.lianlianpay.com/)

# Example Code

```javascript

// listen to callback
document.addEventListener('onLLPayEnd', function(e){ 
	var ret = e.ret;
	alert('onLLPayEnd: ' + JSON.stringify(ret));
});

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
		"sign_type":"RSA",	// "RSA" or "MD5"
		"valid_order":"30",
		"sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/" +
		"+p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtXE+vP" +
		"TfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
};

// pass the credit or debit card number
argsExample.card_no = $('input#card_no').val();

// call LLPay SDK
LLPay.startPay(argsExample, function(){
}, function(){
});

```

# Credits

This cordova plugin is developed by Raymond Xie. All rights reserved.

