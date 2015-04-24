//
//  PaySdkColor.h
//  PaySdkColor
//
//  Created by xuyf on 14-4-23.
//  Copyright (c) 2014年 llyt. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum LLPayResult {
    kLLPayResultSuccess = 0,    // 支付成功
    kLLPayResultFail = 1,       // 支付失败
    kLLPayResultCancel = 2,     // 支付取消，用户行为
    kLLPayResultInitError,      // 支付初始化错误，订单信息有误，签名失败等
    kLLPayResultInitParamError, // 支付订单参数有误，无法进行初始化，未传必要信息等
    kLLPayResultUnknow,         // 其他
}LLPayResult;

@protocol LLPaySdkDelegate <NSObject>

@required
// 调用sdk以后的结果回调
// dic 参数中，ret_msg会有具体错误显示
/* 可能返回的参数含义
 
参数名                     含义
result_pay                  支付结果
oid_partner                 商户编号
dt_order                    商户订单时间
no_order                    商户唯一订单号
￼oid_paybill                 连连支付支付单号
money_order                 交易金额
￼￼settle_date                 清算日期
￼￼info_order                  订单描述
pay_type                    支付类型
bank_code                   银行编号
bank_name                   银行名称
memo                        支付备注
 */

- (void)paymentEnd:(LLPayResult)resultCode withResultDic:(NSDictionary*)dic;

@end

@interface LLPaySdk : NSObject
{
    UIViewController        *presentController;
}
//sdk支付统一入口
- (void)presentPaySdkInViewController:(UIViewController*)viewController withTraderInfo:(NSDictionary*)traderInfo;

//  11-17新增接口
// 快捷支付
- (void)presentQuickPaySdkInViewController:(UIViewController*)viewController withTraderInfo:(NSDictionary*)traderInfo;

// 认证支付
- (void)presentVerifyPaySdkInViewController:(UIViewController*)viewController withTraderInfo:(NSDictionary*)traderInfo;


//sdk认证签约入口
- (void)presentVerifyPaySignInViewController:(UIViewController *)viewContoller withTraderInfo:(NSDictionary*)traderInfo;

@property (nonatomic, assign) NSObject<LLPaySdkDelegate> *sdkDelegate;


+ (void)switchToTestServer:(BOOL)isTestServer;
// 切换正式、测试服务器，YES测试环境，NO正式环境。默认不调用是正式环境，请不要随意使用该函数切换至测试环境

+ (void)setVerifyPayState:(BOOL)isVerifyPay;
// 切换认证支付、快捷支付， YES认证，NO快捷。默认不调用是根据包的配置。(该函数将在后面版本被setLLsdkPayState替代，此处只是为了兼容老用户)

+ (void)setLLsdkPayState:(int)payState;
//认证支付、快捷支付、预授权切换，0快捷 1认证 2预授权。默认不调用是根据包的配置

+ (void)setADView:(UIView *)view;
// 在sdk标题栏下面设定一个广告条或者操作指南bar
@end
