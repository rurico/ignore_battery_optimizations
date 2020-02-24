import Flutter
import UIKit

public class SwiftIgnoreBatteryOptimizationsPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "ignore_battery_optimizations", binaryMessenger: registrar.messenger())
    let instance = SwiftIgnoreBatteryOptimizationsPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
