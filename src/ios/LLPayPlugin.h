
#import "CDVPluginExt.h"
#import "LLPaySdk.h"

#define OPT_IS_TESTING      @"isTesting"
#define OPT_LOG_VERBOSE     @"logVerbose"

@interface LLPayPlugin : CDVPluginExt<LLPaySdkDelegate>

- (void) setOptions:(CDVInvokedUrlCommand *)command;

- (void) startPay:(CDVInvokedUrlCommand *)command;

@property (assign) BOOL isTesting;
@property (assign) BOOL logVerbose;

@property (nonatomic, retain) LLPaySdk * sdk;

#pragma mark virtual methods

- (void)pluginInitialize;

- (NSString*) __getProductShortName;

- (void) parseOptions:(NSDictionary*) options;
- (NSString*) md5:(NSString*) s;

@end
