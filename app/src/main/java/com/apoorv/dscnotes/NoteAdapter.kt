package com.apoorv.dscnotes
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.identity.android.legacy.Utility
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NoteAdapter(options: FirestoreRecyclerOptions<Note>, private val context: Context) :
    FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(options) {

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Note) {
        holder.titleTextView.text = model.title
        holder.contentTextView.text = model.content
        holder.timestampTextView.text = com.apoorv.dscnotes.Utility.timestampToString(model.timestamp)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NoteDetails::class.java)
            intent.putExtra("title", model.title)
            intent.putExtra("content", model.content)
            intent.putExtra("docId", snapshots.getSnapshot(position).id)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_note_item, parent, false)
        return NoteViewHolder(view)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.note_title_text_view)
        val contentTextView: TextView = itemView.findViewById(R.id.note_content_text_view)
        val timestampTextView: TextView = itemView.findViewById(R.id.note_date_text_view)
    }
}