package dev.tomasek.bdaycasino.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tomasek.bdaycasino.model.Setting
import dev.tomasek.bdaycasino.model.User

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<User>().apply { value = User() }
    private val _setting = MutableLiveData<Setting>().apply { value = Setting() }
    val user: LiveData<User> get() = _user
    val setting: LiveData<Setting> get() = _setting

    fun setUsername(name: String) {
        _user.value?.name = name
    }

    fun setCredits() {
        _user.value?.credits = _setting.value?.startingCredits!!
    }

    fun setMaxPrize(value: Int) {
        _setting.value?.maxPrize = value
    }

    fun setStartingCredits(value: Int) {
        _setting.value?.startingCredits = value
    }
}