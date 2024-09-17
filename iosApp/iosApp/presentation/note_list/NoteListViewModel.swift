//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Hossain Muktar on 17/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

@Observable
class NoteListViewModel {

    let noteDao = NoteDaoImpl(db: NoteDatabaseFactory(driverFactory: DatabaseDriverFactory()).createDatabase())
    let repository: NoteRepository
    let noteUseCases: NoteUseCases
            
    var state = NoteListState()
    var fetchRunning = false

    init() {
        self.repository = NoteRepositoryImpl(noteDao: self.noteDao)
        self.noteUseCases = NoteUseCases(
            getAllNotes: GetAllNotes(repository: self.repository),
            deleteNote: DeleteNote(repository: self.repository),
            addNote: AddNote(repository: self.repository),
            getNoteById: GetNoteById(repository: self.repository))
    }
    
    func toggleOrderVisibility() {
        state.isOrderSectionVisible = !state.isOrderSectionVisible
    }
    
    func changeNoteOrder(noteOrder: NoteOrder)  {
        state.noteOrder = noteOrder
        fetchNoteByOrder(noteOrder: noteOrder)
    }
    
    func fetchNoteByOrder(noteOrder: NoteOrder = NoteOrder.Title(orderType: OrderType.Ascending())) {
        if fetchRunning {
            return
        }
        self.fetchRunning = true
        Task{
            do{
                self.state.notes = try await self.noteUseCases.getAllNotes.execute(noteOrder: noteOrder)
                self.fetchRunning = false
            } catch {
                self.state.notes = []
            }
        }
    }
    
    func delete(note: Note)  {
        Task{
            do{
                try await noteUseCases.deleteNote.execute(note: note)
            } catch {
                return
            }
            fetchNoteByOrder()
        }
    }

}
