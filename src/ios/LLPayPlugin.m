
#import <CommonCrypto/CommonDigest.h>
#import "LLPayPlugin.h"

@implementation LLPayPlugin

- (void)pluginInitialize
{
    [super pluginInitialize];
    
    self.isTesting = false;
    self.logVerbose = false;
    
    self.sdk = [[LLPaySdk alloc] init];
    self.sdk.sdkDelegate = self;
}

- (void)dealloc {
    
}

- (NSString*) __getProductShortName {
    return @"LLPay";
}

- (void) setOptions:(CDVInvokedUrlCommand *)command {
    if([command.arguments count] > 0) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self parseOptions:options];
    }
    
    [self sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] to:command.callbackId];
}

- (void) parseOptions:(NSDictionary*) options
{
    if ((NSNull *)options == [NSNull null]) return;
    
    NSString* str = nil;
    
    str = [options objectForKey:OPT_IS_TESTING];
    if(str) self.isTesting = [str boolValue];
    
    str = [options objectForKey:OPT_LOG_VERBOSE];
    if(str) self.logVerbose = [str boolValue];
}


- (void) startPay:(CDVInvokedUrlCommand *)command {
    NSLog(@"startPay");
    
    NSDictionary* signedDict = [command argumentAtIndex:0 withDefault:[NSNull null]];
    
    [self.sdk presentPaySdkInViewController:[self getViewController]
                             withTraderInfo:signedDict];
    
    [self sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] to:command.callbackId];
}

- (void)paymentEnd:(LLPayResult)resultCode withResultDic:(NSDictionary*)dic {
    
    NSError * err;
    NSData * jsonData = [NSJSONSerialization dataWithJSONObject:dic options:0 error:&err];
    NSString * ret = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    
    [self fireEvent:[self __getProductShortName]
              event:@"onLLPayEnd"
           withData:[NSString stringWithFormat:@"{\"ret\":%@}", ret]];
}

- (NSString*) md5:(NSString*) str
{
    const char *cStr = [str UTF8String];
    unsigned char digest[16];
    CC_MD5( cStr, (CC_LONG)strlen(cStr), digest );
    
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_MD5_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x", digest[i]];
    
    return  output;
}


@end

