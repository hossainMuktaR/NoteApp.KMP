//
//  AddEditNoteScreen.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

@Observable
class AddEditNoteViewModel {
    
    let noteDao = NoteDaoImpl(db: NoteDatabaseFactory(driverFactory: DatabaseDriverFactory()).createDatabase())
    let repository: NoteRepository
    let noteUseCases: NoteUseCases
    
    var noteTitle = NoteTextFieldState(
        hint: "Enter title here...")
    var noteContent = NoteTextFieldState(
        hint: "Enter content here...")
    
    let noteColors: [Color] = [Color.blue, Color.red, Color.green, Color.indigo, Color.cyan]
    
    var noteColor: Color = Color.red
    
    var currentNoteId: Int?
    
    init(noteId: Int?) {
        self.currentNoteId = noteId
        self.repository = NoteRepositoryImpl(noteDao: self.noteDao)
        self.noteUseCases = NoteUseCases(
            getAllNotes: GetAllNotes(repository: self.repository),
            deleteNote: DeleteNote(repository: self.repository),
            addNote: AddNote(repository: self.repository),
            getNoteById: GetNoteById(repository: self.repository))
    }
    
    
    func changeNoteColor(color: Color) {
        self.noteColor = color
    }
    
    func fetchNoteById() {
        guard let id = currentNoteId else {
            print("noteid nil")
            return
        }
        Task{
            do {
                let result = try await noteUseCases.getNoteById.execute(id: Int32(currentNoteId!))
                
                self.noteTitle.text = result!.title
                self.noteContent.text = result!.content
                self.noteColor = colorFromInt32(result!.color)
                
            } catch {
                print(error.localizedDescription)
            }
        }
    }
    
    func save(dismiss: DismissAction) {
        if currentNoteId == nil {
            currentNoteId = Int(UUID().uuidString)
        }
        let note = Note(
                    title: noteTitle.text,
                    content: noteContent.text,
                    timeStamp: dateToTimestamp(date: Date()) ,
                    color: int32FromColor(noteColor),
                    id: currentNoteId as? KotlinInt
                )
        Task{
            do {
                try await noteUseCases.addNote.execute(note: note)
            } catch {
                print(error.localizedDescription)
            }
        }
        
        self.dismisScreen(dismiss: dismiss)
    }
    
    private func dismisScreen(dismiss: DismissAction) {
        dismiss()
    }
    
    
}
