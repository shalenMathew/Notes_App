package com.example.notescleanarch.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notescleanarch.feature_note.data.data_source.NotesDatabase
import com.example.notescleanarch.feature_note.data.repository.NotesRepositoryImpl
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository
import com.example.notescleanarch.feature_note.domain.use_cases.AddNote
import com.example.notescleanarch.feature_note.domain.use_cases.DeleteNote
import com.example.notescleanarch.feature_note.domain.use_cases.GetNote
import com.example.notescleanarch.feature_note.domain.use_cases.GetNotes
import com.example.notescleanarch.feature_note.domain.use_cases.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNotesDatabase(application: Application):NotesDatabase{ // room will provide the application by itself
        return Room.databaseBuilder(application,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesNotesRepository(db:NotesDatabase):NotesRepository{
        return NotesRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCases(notesRepository: NotesRepository):NotesUseCases{
        return NotesUseCases(GetNotes(notesRepository), DeleteNote(notesRepository), AddNote(notesRepository),
            GetNote(notesRepository)
        )
    }

}