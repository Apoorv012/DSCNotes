package com.apoorv.dscnotes


import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

object Utility {
    fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

    fun getCollectionReferenceForNotes(): CollectionReference {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return FirebaseFirestore.getInstance().collection("notes")
            .document(currentUser!!.uid).collection("my_notes")
    }

     fun timestampToString(timestamp: Timestamp): String {
        return SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate())
    }

//    fun timestampToString(timestamp: Timestamp): String {
//        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
//        return formatter.format(timestamp.toDate())
//    }

}