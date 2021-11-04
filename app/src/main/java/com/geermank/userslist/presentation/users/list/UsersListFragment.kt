package com.geermank.userslist.presentation.users.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geermank.userslist.R
import com.geermank.userslist.databinding.FragmentUsersListBinding
import com.geermank.userslist.presentation.ListItemClickListener
import com.geermank.userslist.presentation.remove
import com.geermank.userslist.presentation.show
import com.geermank.userslist.presentation.users.manipulation.USER_ARGUMENT_KEY
import com.geermank.userslist.presentation.users.model.UserUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(), ListItemClickListener<UserUiModel> {

    private val viewModel: UsersListViewModel by viewModels()
    private val adapter: UserListAdapter = UserListAdapter(itemClickListener = this)

    private lateinit var binding: FragmentUsersListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUsersRecyclerView()
        observeUsersList()
        binding.fabAddNewUser.setOnClickListener {
            findNavController().navigate(R.id.action_users_list_to_users_manipulation)
        }
    }

    private fun setUpUsersRecyclerView() {
        binding.rvUsersList.apply {
            adapter = this@UsersListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeUsersList() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            if (users.isEmpty()) {
                showUsersEmptyState()
            } else {
                hideUsersEmptyState()
                showUpdatedUsersList(users)
            }
        }
    }

    private fun showUsersEmptyState() {
        binding.layoutEmptyUsersList.show()
    }

    private fun hideUsersEmptyState() {
        binding.layoutEmptyUsersList.remove()
    }

    private fun showUpdatedUsersList(users: List<UserUiModel>) {
        adapter.clearAndUpdate(users)
    }

    override fun onItemClickListener(item: UserUiModel) {
        findNavController().navigate(R.id.action_users_list_to_users_manipulation, Bundle().also { args ->
            args.putParcelable(USER_ARGUMENT_KEY, item)
        })
    }

    override fun onItemLongClickListener(item: UserUiModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_user_confirmation_title)
            .setMessage(R.string.delete_user_confirmation_message)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                dialog.dismiss()
                viewModel.delete(item)
                adapter.remove(item)
                checkForEmptyAdapter()
            }.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun checkForEmptyAdapter() {
        if (adapter.itemCount == 0) {
            showUsersEmptyState()
        }
    }
}
