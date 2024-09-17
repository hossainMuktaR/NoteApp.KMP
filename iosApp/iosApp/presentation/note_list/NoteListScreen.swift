//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct NoteListScreen: View {
    private var vm = NoteListViewModel()

    var body: some View {
        VStack{
            if(vm.state.isOrderSectionVisible) {
                OrderSection(noteOrder: vm.state.noteOrder , onOrderChange: { noteOrder in
                    withAnimation{
                        vm.changeNoteOrder(noteOrder: noteOrder)
                    }
                })
            }
            ScrollView{
                VStack(spacing: 16){
                    ForEach(vm.state.notes, id: \.timeStamp) { note in
                        NavigationLink(destination: AddEditNoteScreen(noteId: note.id?.intValue)){
                            
                            NoteItem(note: note, onDeleteClick: { note in
                                //delete note
                                withAnimation{
                                    vm.delete(note: note)
                                }
                            })
                        }
                    }
                }
                .padding()
            }
            
            Spacer()
            HStack(alignment: .bottom){
                Spacer()
                NavigationLink(destination: AddEditNoteScreen(noteId: nil)){
                    Image(systemName: "plus")
                        .foregroundStyle(.white)
                        .padding()
                        .background(Color.blue.opacity(0.8))
                        .clipShape(Circle())
                }
                .padding(.trailing, 16)
                .padding(.bottom, 16)
            }
            
        }
        .navigationTitle("Your Note")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar{
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    //visible and hide orderSection
                    withAnimation{
                        vm.toggleOrderVisibility()
                    }
                }, label: {
                    Image(systemName: "arrow.up.arrow.down")
                })
            }
        }
        .onAppear {
            vm.fetchNoteByOrder(noteOrder: vm.state.noteOrder)
        }
        .navigationTitle("Your Note")
        .navigationBarTitleDisplayMode(.inline)
        
    }
}

#Preview {
    NoteListScreen()
}
