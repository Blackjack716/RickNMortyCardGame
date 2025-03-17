package com.rnm.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnergyManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private var timer: Job? = null
    private val energyLevel = MutableStateFlow(10)
    private val _endTime = MutableStateFlow(0L)

    fun start() {
        manageTimer()
    }

    private fun manageTimer() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getEnergyRechargeTime().collectLatest {
                _endTime.value = it
                checkEnergy(it)
            }
        }
    }

    private fun checkEnergy(endTime: Long) {
        if (endTime <= System.currentTimeMillis()) {
            energyLevel.value = 10
        } else {
            val difference = endTime - System.currentTimeMillis()
            val differenceInUses = (difference / RECHARGE_TIME).toInt()
            val additionalTime = difference % RECHARGE_TIME

            energyLevel.value = 10 - (differenceInUses + 1)
            timer?.cancel()
            setSimpleTimer(
                endTime = if (additionalTime > 1)
                    additionalTime + System.currentTimeMillis()
                else
                    RECHARGE_TIME + System.currentTimeMillis(),
                onFinish = {
                    energyLevel.value += 1
                    checkEnergy(_endTime.value)
                })
            timer?.start()
        }
    }

    fun setEnergyLevel(energy: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val endTime = dataStoreManager.getEnergyRechargeTime().first()
            if (endTime > System.currentTimeMillis()) {
                dataStoreManager.setEnergyRechargeTime(endTime + (RECHARGE_TIME * energy * -1))
            } else {
                dataStoreManager.setEnergyRechargeTime(System.currentTimeMillis() + (RECHARGE_TIME * energy * -1))
            }
        }

    }

    fun getEnergyLevel(): Flow<Int> {
        return energyLevel.asStateFlow()
    }

    private fun setSimpleTimer(
        endTime: Long,
        delayMillis: Long = 10000,
        onFinish: () -> Unit,
    ) = CoroutineScope(Dispatchers.Default).launch {
        while (System.currentTimeMillis() < endTime) {
            delay(delayMillis)
        }
        onFinish()
    }

    companion object {
        const val RECHARGE_TIME = 600000L
    }

}