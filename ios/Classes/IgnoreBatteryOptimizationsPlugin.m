#import "IgnoreBatteryOptimizationsPlugin.h"
#import <ignore_battery_optimizations/ignore_battery_optimizations-Swift.h>

@implementation IgnoreBatteryOptimizationsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftIgnoreBatteryOptimizationsPlugin registerWithRegistrar:registrar];
}
@end
