package com.vs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vs.R
import com.vs.models.Note
import kotlinx.android.synthetic.main.layout_note_item.view.*
import android.text.method.TextKeyListener.clear



/**
 *  Created by Sachin
 */
class NotesAdapter(private val context: Context, private val notes: ArrayList<Note>) :
        androidx.recyclerview.widget.RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindToView(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class ViewHolder(itemView: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val tvNoteTitle = itemView.tvNoteTitle!!
        private val tvNoteDesc = itemView.tvNoteDesc!!

        fun bindToView(note: Note) {
            tvNoteTitle.text = note.title
            tvNoteDesc.text = note.description

        }
    }

}
