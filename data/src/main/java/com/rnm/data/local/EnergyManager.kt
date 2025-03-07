package com.rnm.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnergyManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private var isRecharging = MutableStateFlow(false)
    private var timer: Job? = null

    fun start() {
        checkIfNeedRecharging()
        manageTimer()
    }

    private fun manageTimer() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getEnergyRechargeTime().collectLatest {
                if (it != 0L) {
                    coroutineTimer()
                }
            }
        }
    }

    private fun checkIfNeedRecharging() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getEnergyLevel().collectLatest {
                if (it < 10) {
                    if (dataStoreManager.getEnergyRechargeTime().first() < System.currentTimeMillis()) {
                        val endTime = System.currentTimeMillis() + (600000 * (10 - it))
                        dataStoreManager.setEnergyRechargeTime(endTime)
                    }

                    isRecharging.value = true
                } else {
                    isRecharging.value = false
                }
            }

        }
    }

    private suspend fun coroutineTimer() {
        val endTime = dataStoreManager.getEnergyRechargeTime().first()

        timer = startCoroutineTimer(
            endTime = endTime,
            tickSynchro = 15000,
            onFinish = {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStoreManager.setEnergyLevel(10)
                }
            },
            onTick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val energyLevel = dataStoreManager.getEnergyLevel().first()
                    dataStoreManager.setEnergyLevel(energyLevel + 1)
                }
            },
            onTickSync = {}
        )
        timer?.start()
    }

    private fun startCoroutineTimer(
        endTime: Long,
        tick: Long = 600000,
        delayMillis: Long = 1000,
        tickSynchro: Long = 15000,
        onFinish: () -> Unit,
        onTick: () -> Unit,
        onTickSync: () -> Unit
    ) = CoroutineScope(Dispatchers.Default).launch {

        val currentTime = System.currentTimeMillis()
        var tickTime = currentTime + tick
        var tickSynchroTime = currentTime + tickSynchro

        while (System.currentTimeMillis() < endTime) {
            if (System.currentTimeMillis() > tickTime) {
                onTick()
                tickTime = System.currentTimeMillis() + tick
            }
            if (System.currentTimeMillis() > tickSynchroTime) {
                onTickSync()
                tickSynchroTime = System.currentTimeMillis() + tickSynchro
            }
            delay(delayMillis)
        }
        onFinish()
    }

}