package com.example.quranorbitteacher.fragments.profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.quranorbitteacher.activities.MainActivity
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.FragmentProfileBinding
import com.example.quranorbitteacher.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.HashMap

class Profile : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding:FragmentProfileBinding

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentProfileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        initViews()
    }

    private fun initViews() {
        fetchUserData()
        if (Common.CURRENT_USER != null) {
            setProfileData()
        } else {
            fetchUserData()
        }
        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))

        }
        binding.profileImage.setOnClickListener {
            SelectImage() }
    }


    private fun setProfileData() {
        binding.profileName.text = Common.CURRENT_USER.userName
        binding.profileEmail.text = Common.CURRENT_USER.userEmail
        binding.profileMobileNo.text = Common.CURRENT_USER.phoneNo

        if (Common.CURRENT_USER.profileImage != null && !Common.CURRENT_USER.profileImage.equals(""))
            Glide.with(requireContext()).load(Common.CURRENT_USER.profileImage)
                .into(binding.profileImage)
        Log.d("Image", "setProfileData: ${Common.CURRENT_USER.profileImage}")


    }

    private fun fetchUserData() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance().getReference("TEACHER")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Common.CURRENT_USER = snapshot.getValue(User::class.java)!!
                        Log.d("profileImage", "onDataChange: $Common.CurrentUser")
                        setProfileData()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun SelectImage() {
        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Image from here..."),
            PICK_IMAGE_REQUEST
        )
    }

    // Override onActivityResult method
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data
            Glide.with(requireActivity()).load(data.data).into(binding.profileImage)
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(requireContext())
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
                                uploadDataToFirebase(uri.toString(), progressDialog)
                            }
                    }
                }
                .addOnFailureListener { e: Exception ->

                    // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Failed " + e.message, Toast.LENGTH_SHORT)
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

    private fun uploadDataToFirebase(imageUrl: String, progressDialog: ProgressDialog) {
        val update = HashMap<String, Any>()
        update["image"] = imageUrl
        FirebaseDatabase.getInstance().getReference("TEACHER")
            .child(Common.CURRENT_USER.uid!!)
            .updateChildren(update)
            .addOnCompleteListener { task: Task<Void?> ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    // Common.CurrentUser.setImage(imageUrl)
                    Toast.makeText(
                        requireContext(),
                        "Profile image uploaded successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener { e: Exception ->
                progressDialog.dismiss()
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
    }
}