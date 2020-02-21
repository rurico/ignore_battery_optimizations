import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:ignore_battery_optimizations/ignore_battery_optimizations.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: FlatButton(
            child: Text('openBatteryOptimizations'),
            onPressed: () async {
              final brand =
                  await IgnoreBatteryOptimizations.openBatteryOptimizations();
              print(brand);
            },
          ),
        ),
      ),
    );
  }
}
