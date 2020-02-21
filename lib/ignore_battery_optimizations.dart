import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class IgnoreBatteryOptimizations {
  static const MethodChannel _channel =
      const MethodChannel('ignore_battery_optimizations');

  static Future<String> openBatteryOptimizations() async {
    if (Platform.isAndroid) {
      final String brand =
          await _channel.invokeMethod('openBatteryOptimizations');
      return brand;
    }
    return Future.value('other');
  }
}
