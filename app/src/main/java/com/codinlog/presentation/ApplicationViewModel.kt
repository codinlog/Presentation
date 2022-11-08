package com.codinlog.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(@ApplicationContext ctx:Context) :AndroidViewModel(ctx as Application) {

}