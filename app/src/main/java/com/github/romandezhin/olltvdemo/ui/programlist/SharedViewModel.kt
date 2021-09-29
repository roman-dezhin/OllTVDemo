package com.github.romandezhin.olltvdemo.ui.programlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.romandezhin.olltvdemo.di.MainModule
import com.github.romandezhin.olltvdemo.domain.interactor.Result
import com.github.romandezhin.olltvdemo.domain.model.Program
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val interactor = MainModule.getProgramInteractorImpl()

    companion object {
        private const val STARTING_DIRECTION = 0
        private const val STARTING_BORDER_ID = 0
    }

    private val programs: MutableLiveData<List<Program>> by lazy {
        MutableLiveData<List<Program>>().also {
            loadPrograms(STARTING_BORDER_ID, STARTING_DIRECTION)
        }
    }

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _error = MutableLiveData<java.lang.Exception>()
    val error: LiveData<java.lang.Exception> = _error

    fun getPrograms(): LiveData<List<Program>> = programs

    fun getProgram(id: Int): Program? {
        return programs.value?.get(id)
    }

    private fun loadPrograms(borderId: Int, direction: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val result = try {
                interactor.fetch(borderId, direction)
            } catch (e: Exception) {
                Result.Error(e)
            }
            _loading.postValue(false)
            when (result) {
                is Result.Success<List<Program>> -> programs.postValue(result.data)
                is Result.Error -> _error.postValue(result.exception)
            }
        }
}