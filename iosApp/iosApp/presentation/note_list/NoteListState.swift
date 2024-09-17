//
//  NoteListState.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

struct NoteListState {
    var notes: [Note] = []
    var noteOrder: NoteOrder = NoteOrder.Date(orderType: OrderType.Descending())
    var isOrderSectionVisible: Bool = false
}
