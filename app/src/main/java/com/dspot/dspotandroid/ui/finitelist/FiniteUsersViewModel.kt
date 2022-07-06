package com.dspot.dspotandroid.ui.finitelist

import androidx.lifecycle.ViewModel
import com.dspot.dspotandroid.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiniteUsersViewModel
@Inject
constructor(
    repository: UserRepository,
) : ViewModel() {
    val users = repository.getUsers()
}
























