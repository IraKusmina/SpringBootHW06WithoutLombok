package ru.kolomiets.SpringBootNotes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolomiets.SpringBootNotes.models.Note;
import ru.kolomiets.SpringBootNotes.services.NoteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAllNotes() {
        List<Note> notes = noteService.findAll();
        return notes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note newNote = noteService.createNote(note);
        return ResponseEntity.status(201).body(newNote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findNoteById(@PathVariable Long id) {
        return noteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable Long id) {
        return noteService.findById(id)
                .map(existingNote -> {
                    noteService.updateNote(note, id);
                    return ResponseEntity.ok(note);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        if (!noteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

