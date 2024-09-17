//
//  RadioButton.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RadioButton: View {
    var text: String
    var selected: Bool
    var onSelect: () -> Void
    
    var body: some View {
        HStack {
            // RadioButton UI
            Circle()
                .stroke(selected ? Color.blue : Color.gray, lineWidth: 2)
                .background(
                    Circle()
                        .fill(selected ? Color.blue : Color.clear)
                )
                .frame(width: 20, height: 20)
                .onTapGesture {
                    onSelect()
                }
            
            // Spacer between the radio button and text
            Spacer().frame(width: 8)
            
            // Text label
            Text(text)
                .font(.body)
                .foregroundColor(.primary)
        }
        .padding(.vertical, 4)
        .contentShape(Rectangle()) // Ensure the whole area is tappable
        .onTapGesture {
            onSelect()
        }
    }
}


#Preview {
    RadioButton(
        text: "Title",
        selected: true,
        onSelect: {  }
    )
}
