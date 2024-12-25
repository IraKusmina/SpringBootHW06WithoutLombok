package ru.kolomiets.SpringBootNotes.repositories;

import ru.kolomiets.SpringBootNotes.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
