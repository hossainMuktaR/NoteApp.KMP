//
//  OrderSection.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct OrderSection: View {
        var noteOrder: NoteOrder
        var onOrderChange: (NoteOrder) -> Void
        
        var body: some View {
            VStack(alignment: .center) {
                HStack {
                    RadioButton(
                        text: "Title",
                        selected: noteOrder is NoteOrder.Title,
                        onSelect: { onOrderChange(NoteOrder.Title(orderType: noteOrder.orderType)) }
                    )
                    RadioButton(
                        text: "Date",
                        selected: noteOrder is NoteOrder.Date,
                        onSelect: { onOrderChange(NoteOrder.Date(orderType: noteOrder.orderType)) }
                    )
                    RadioButton(
                        text: "Color",
                        selected: noteOrder is NoteOrder.Color,
                        onSelect: { onOrderChange(NoteOrder.Color(orderType: noteOrder.orderType)) }
                    )
                }
                Spacer().frame(height: 8)
                HStack {
                    RadioButton(
                        text: "Ascending",
                        selected: noteOrder.orderType == OrderType.Ascending(),
                        onSelect: { onOrderChange(noteOrder.doCopy(orderType: OrderType.Ascending())) }
                    )
                    RadioButton(
                        text: "Descending",
                        selected: noteOrder.orderType == OrderType.Descending(),
                        onSelect: { onOrderChange(noteOrder.doCopy(orderType: OrderType.Descending())) }
                    )
                }
            }
            .padding()
        }
    }

#Preview {
    OrderSection(
        noteOrder: NoteOrder.Title(orderType: OrderType.Descending()), onOrderChange: { _ in }
    )
}
