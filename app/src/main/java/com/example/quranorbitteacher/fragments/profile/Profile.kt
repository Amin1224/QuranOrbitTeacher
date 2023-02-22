package com.example.quranorbitteacher.fragments.profile

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.quranorbitteacher.activities.Registration
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.FragmentProfileBinding
import com.example.quranorbitteacher.image_utils.ImageUtils
import com.example.quranorbitteacher.model.FullRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.collections.HashMap

class Profile : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

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
        binding.backTo.setOnClickListener {
            requireActivity().onBackPressed()
        }
        if (Common.CURRENT_USERS != null) {
            setProfileData()
        } else {
            fetchUserData()
        }
        binding.logOut.setOnClickListener {
            sinOut()
        }
        binding.profileImage.setOnClickListener {
            SelectImage()
        }
    }
    private fun sinOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(), Registration::class.java))
        requireActivity().finish()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setProfileData() {
        binding.profileName.text = Common.CURRENT_USERS.name
        binding.lastName.text=Common.CURRENT_USERS.lName
        binding.addressTxt.text=Common.CURRENT_USERS.address
        binding.profileEmail.text = Common.CURRENT_USERS.userEmail
        binding.profileMobileNo.text = Common.CURRENT_USERS.mobileNo
//        Glide.with(requireContext()).load(Common.CURRENT_USERS.profileImage).into(binding.profileImage)

        if (Common.CURRENT_USERS.profileImage != null && !Common.CURRENT_USERS.profileImage.equals(""))
            Glide.with(requireContext()).load(Common.CURRENT_USERS.profileImage)
                .into(binding.profileImage)
             Log.d("Image", "setProfileData: ${Common.CURRENT_USERS.profileImage}")
    }

    private fun fetchUserData() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance().getReference("CSR")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Common.CURRENT_USERS = snapshot.getValue(FullRegistration::class.java)!!
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            Glide.with(requireActivity()).load(data.data).into(binding.profileImage)
            ImageUtils.uploadImage(requireContext(),filePath){
                uploadDataToFirebase(it)
            }
        }
    }

    private fun uploadDataToFirebase(imageUrl: String) {
        val update = HashMap<String, Any>()
        update["profileImage"] = imageUrl
        FirebaseDatabase.getInstance().getReference("CSR")
            .child(Common.CURRENT_USERS.uid!!)
            .updateChildren(update)
            .addOnCompleteListener { task: Task<Void?> ->
               // progressDialog.dismiss()
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
            //    progressDialog.dismiss()
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
    }
}