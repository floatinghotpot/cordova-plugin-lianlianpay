
#import "CDVPluginExt.h"

@interface LLPayPlugin : CDVPluginExt

- (void) setOptions:(CDVInvokedUrlCommand *)command;

- (void) startPay:(CDVInvokedUrlCommand *)command;

#pragma mark virtual methods

- (void)pluginInitialize;

- (NSString*) __getProductShortName;

@end
