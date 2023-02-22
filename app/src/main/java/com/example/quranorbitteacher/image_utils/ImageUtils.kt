package com.example.quranorbitteacher.image_utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class ImageUtils {
    companion object{

      @SuppressLint("SuspiciousIndentation")
      fun uploadImage(requireContext: Context, filePath: Uri?, imageUri:(String)->Unit) {
         var storage = FirebaseStorage.getInstance()
         var  storageReference = storage!!.reference
            if (filePath != null) {

                // Code for showing progressDialog while uploading
                val progressDialog = ProgressDialog(requireContext)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                // Defining the child of storageReference
                val ref: StorageReference =
                    storageReference!!.child("images/" + UUID.randomUUID().toString())

                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath!!)
                    .addOnCompleteListener { task: Task<UploadTask.TaskSnapshot?> ->
                        if (task.isSuccessful) {
                            ref.downloadUrl
                                .addOnSuccessListener { uri: Uri ->
                                    imageUri.invoke(uri.toString())
                                    progressDialog.dismiss()
                                   // uploadDataToFirebase(uri.toString(), progressDialog)
                                }
                        }
                    }
                    .addOnFailureListener { e: Exception ->

                        // Error, Image not uploaded
                        progressDialog.dismiss()
                        Toast.makeText(requireContext, "Failed " + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnProgressListener { taskSnapshot ->

                        // Progress Listener for loading
                        // percentage on the dialog box
                        val progress =
                            100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
            }
        }

    }
}