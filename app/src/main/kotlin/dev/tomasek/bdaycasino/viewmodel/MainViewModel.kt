package dev.tomasek.bdaycasino.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tomasek.bdaycasino.model.Prize
import dev.tomasek.bdaycasino.model.Setting
import dev.tomasek.bdaycasino.model.User

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    private val _setting = MutableLiveData<Setting>()

    val user: LiveData<User> get() = _user
    val setting: LiveData<Setting> get() = _setting

    init {
        // Initialize with default values
        _user.value = User(name = "", credits = 0, prize = 0, inventory = mutableListOf())
        _setting.value = Setting(prizes = mutableListOf(), startingCredits = 0)
    }

    fun addPrize(prize: Prize) {
        val currentSetting = _setting.value ?: return
        val updatedPrizes = currentSetting.prizes.toMutableList().apply { add(prize) }
        _setting.value = currentSetting.copy(prizes = updatedPrizes)
    }

    fun deletePrize(prize: Prize) {
        val currentSetting = _setting.value ?: return
        val updatedPrizes = currentSetting.prizes.toMutableList().apply { remove(prize) }
        _setting.value = currentSetting.copy(prizes = updatedPrizes)
    }

    fun setUsername(name: String) {
        _user.value?.name = name
    }

    fun setCredits() {
        _user.value?.credits = _setting.value?.startingCredits!!
    }

    fun setStartingCredits(credits: Int) {
        val currentSetting = _setting.value ?: return
        _setting.value = currentSetting.copy(startingCredits = credits)
    }

    fun purchasePrize(prize: Prize) {
        val userPrizeCredits = _user.value?.prize ?: return
        if (userPrizeCredits >= prize.minimumCreditsToUnlock) {
            _user.value?.prize = userPrizeCredits - prize.minimumCreditsToUnlock

            val currentUser = _user.value ?: return
            val updatedInventory = currentUser.inventory.toMutableList().apply { add(prize) }
            _user.value = currentUser.copy(inventory = updatedInventory)

            val currentSetting = _setting.value ?: return
            val updatedPrizes = currentSetting.prizes.toMutableList().apply { remove(prize) }
            _setting.value = currentSetting.copy(prizes = updatedPrizes)
        } else {
            // Handle insufficient credits (e.g., show a message to the user)
        }
    }
}