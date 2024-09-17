//
//  Utils.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

func colorFromInt32(_ int32Color: Int32) -> Color {
    let red = Double((int32Color >> 16) & 0xFF) / 255.0
    let green = Double((int32Color >> 8) & 0xFF) / 255.0
    let blue = Double(int32Color & 0xFF) / 255.0
    
    return Color(red: red, green: green, blue: blue)
}

func int32FromColor(_ color: Color) -> Int32 {
    let uiColor = UIColor(color)
    
    var red: CGFloat = 0
    var green: CGFloat = 0
    var blue: CGFloat = 0
    var alpha: CGFloat = 0
    
    uiColor.getRed(&red, green: &green, blue: &blue, alpha: &alpha)
    
    let redInt = Int32(red * 255) << 16
    let greenInt = Int32(green * 255) << 8
    let blueInt = Int32(blue * 255)
    
    return redInt | greenInt | blueInt
}

func dateToTimestamp(date: Date) -> Int64 {
    return Int64(date.timeIntervalSince1970 * 1000) // Convert seconds to milliseconds
}

func swiftIntToNSNumber(_ value: Int?) -> NSNumber? {
    if let intValue = value {
        return NSNumber(value: intValue)
    } else {
        return nil
    }
}
