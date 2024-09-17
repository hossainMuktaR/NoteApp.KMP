//
//  AddEditNoteScreen.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AddEditNoteScreen: View {
    var noteId: Int?
    @State private var vm: AddEditNoteViewModel
    @Environment(\.dismiss) private var dismiss
    @FocusState private var isTextEditorFocused: Bool
    
    init(noteId: Int?){
        _vm = State(initialValue: AddEditNoteViewModel(noteId: noteId))
    }

    var body: some View {
        VStack{
            HStack {
                ForEach(vm.noteColors, id: \.self) { color in
                    Circle()
                        .fill(color)
                        .frame(width: 50, height: 50)
                        .shadow(radius: 15)
                        .overlay(
                            Circle()
                                .stroke(
                                    vm.noteColor == color ? Color.black : Color.clear,
                                    lineWidth: 3
                                )
                        )
                        .onTapGesture{
                            vm.changeNoteColor(color: color)
                        }
                        
                }
            }
            TextField(vm.noteTitle.hint, text: $vm.noteTitle.text)
                .font(.title)
                .padding()
            Divider()
            
            ZStack(alignment: .topLeading) {
                TextEditor(text: $vm.noteContent.text)
                    .focused($isTextEditorFocused)
                    .font(.body)
                    .padding()
                
                
                if vm.noteContent.text.isEmpty {
                    Text(vm.noteContent.hint)
                        .font(.body)
                        .foregroundColor(.gray)
                        .padding(.horizontal, 20)
                        .padding(.vertical, 24)
                        .onTapGesture {
                            isTextEditorFocused = true
                        }
                }
            }
            Spacer()
            HStack(alignment: .bottom){
                Spacer()
                Button(action: {
                    // save note to model container
                    vm.save(dismiss: dismiss)
                    
                }) {
                    Image(systemName: "tray.and.arrow.down")
                        .foregroundStyle(.white)
                        .padding()
                        .background(Color.blue.opacity(0.8))
                        .clipShape(Circle())
                }
                .padding(.trailing, 16)
                .padding(.bottom, 16)
            }
        }
        .onAppear{
            vm.fetchNoteById()
        }
    }
}

#Preview {
    AddEditNoteScreen(noteId: nil)
}
