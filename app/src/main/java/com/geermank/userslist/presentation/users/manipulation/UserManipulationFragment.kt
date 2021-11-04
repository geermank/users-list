package com.geermank.userslist.presentation.users.manipulation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geermank.userslist.R
import com.geermank.userslist.databinding.FragmentUserManipulationBinding
import com.geermank.userslist.presentation.changeVisibility
import com.geermank.userslist.presentation.setTextIfEmpty
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.geermank.userslist.presentation.setProfilePicOrDefault
import com.geermank.userslist.utils.UriRealPath

const val USER_ARGUMENT_KEY = "USER_MANIPULATION_USER_ARGUMENT_KEY"

@AndroidEntryPoint
class UserManipulationFragment : Fragment() {

    private val viewModel: UserManipulationViewModel by viewModels()

    private lateinit var binding: FragmentUserManipulationBinding

    private val getImageFromGalleryResult = registerForActivityResult(GetContent()) { uri ->
        uri?.let {
            val uriRealPath = UriRealPath(it).getRealPath(requireContext())
            viewModel.changeUserProfilePic(uriRealPath)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserManipulationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserGeneralData()
        observeEditionInitialState()
        observePossibleErrors()
        observeOperationSuccess()
        listenChangeProfilePicIntention()
        listenSaveChangesToUserIntention()
        setHasOptionsMenu(viewModel.shouldShowOptionsMenu())
    }

    private fun observeUserGeneralData() {
        viewModel.editableUser.observe(viewLifecycleOwner) { user ->
            binding.run {
                ivUserProfilePic.setProfilePicOrDefault(user.newProfilePic, R.drawable.ic_default_profile_pic)
                etUserName.setTextIfEmpty(user.newName)
                etUserBio.setTextIfEmpty(user.newBio)
            }
        }
    }

    private fun observeEditionInitialState() {
        viewModel.editionModeEnable.observe(viewLifecycleOwner) { editionEnabled ->
            binding.run {
                etUserName.isEnabled = editionEnabled
                etUserBio.isEnabled = editionEnabled
                btnPickProfilePic.changeVisibility(editionEnabled)
                btnSaveChanges.changeVisibility(editionEnabled)
            }
        }
    }

    private fun observePossibleErrors() {
        viewModel.userManipulationError.observe(viewLifecycleOwner) { errorMessage ->
            Snackbar.make(requireView(), errorMessage.message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observeOperationSuccess() {
        viewModel.userCreatedSuccessfully.observe(viewLifecycleOwner) { successMessage ->
            Toast.makeText(requireContext(), successMessage.message, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_users_manipulation_to_users_list)
        }
    }

    private fun listenChangeProfilePicIntention() {
        binding.btnPickProfilePic.setOnClickListener {
            openGalleryToChooseProfilePic()
        }
    }

    private fun openGalleryToChooseProfilePic() {
        getImageFromGalleryResult.launch("image/*")
    }

    private fun listenSaveChangesToUserIntention() {
        binding.btnSaveChanges.setOnClickListener {
            viewModel.saveUserModifications(
                binding.etUserName.text.toString(),
                binding.etUserBio.text.toString()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_user_manipulation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit_user) {
            viewModel.changeFormEnableState()
        }
        return super.onOptionsItemSelected(item)
    }
}