package com.tlw.streakwolf.ui.home

import androidx.lifecycle.ViewModel
import com.tlw.streakwolf.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {

}