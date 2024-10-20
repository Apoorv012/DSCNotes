package com.apoorv.dscnotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class NoteDetails : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveNoteBtn: ImageButton
    private lateinit var pageTitleTextView: TextView
//    private lateinit var deleteNoteTextViewBtn: TextView

    private var title: String? = null
    private var content: String? = null
    private var docId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_details)

        titleEditText = findViewById(R.id.notes_title_text)
        contentEditText = findViewById(R.id.content_text)
        saveNoteBtn = findViewById(R.id.save_button)
//        pageTitleTextView = findViewById(R.id.page_title)
//        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn)

        // Receive data
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        docId = intent.getStringExtra("docId")

        if (!docId.isNullOrEmpty()) {
            isEditMode = true
        }

        titleEditText.setText(title)
        contentEditText.setText(content)
        if (isEditMode) {
            pageTitleTextView.text = "Edit your note"
//            deleteNoteTextViewBtn.visibility = View.VISIBLE
        }

        saveNoteBtn.setOnClickListener { saveNote() }
//        deleteNoteTextViewBtn.setOnClickListener { deleteNoteFromFirebase() }

    }

    private fun saveNote() {
        val noteTitle = titleEditText.text.toString()
        val noteContent = contentEditText.text.toString()

        if (noteTitle.isEmpty()) {
            titleEditText.error = "Title is required"
            return
        }

        val note = Note(noteTitle, noteContent, Timestamp.now())
        saveNoteToFirebase(note)
    }
    private fun saveNoteToFirebase(note: Note) {
        val documentReference: DocumentReference = if (isEditMode) {
            Utility.getCollectionReferenceForNotes().document(docId!!)
        } else {
            Utility.getCollectionReferenceForNotes().document()
        }
        documentReference.set(note)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Utility.showToast(this, "Note added successfully")
                    finish()
                } else {
                    Utility.showToast(this, "Failed while adding note")
                }
            }
    }
//    private fun deleteNoteFromFirebase() {
//        val documentReference = Utility.getCollectionReferenceForNotes().document(docId!!)
//        documentReference.delete()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Utility.showToast(this, "Note deleted successfully")
//                    finish()
//                } else {
//                    Utility.showToast(this, "Failed while deleting note")
//                }
//            }
//    }

}