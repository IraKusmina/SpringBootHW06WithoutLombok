package ru.kolomiets.SpringBootNotes.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolomiets.SpringBootNotes.models.Note;
import ru.kolomiets.SpringBootNotes.repositories.NoteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepo noteRepo;

    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Note createNote(Note note) {
        return noteRepo.save(note);
    }

    public List<Note> findAll() {
        return noteRepo.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepo.findById(id);
    }

    public Note updateNote(Note note, Long id) {
        Note noteById = noteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заметка не найдена с id: " + id));
        noteById.setTitle(note.getTitle());
        noteById.setInfo(note.getInfo());
        Note newNote = noteRepo.save(noteById);
        return newNote;
    }

    public void deleteById(Long id) {
        if (!noteRepo.existsById(id)) {
            throw new EntityNotFoundException("Заметка не найдена с id: " + id);
        }
        noteRepo.deleteById(id);
    }
}

