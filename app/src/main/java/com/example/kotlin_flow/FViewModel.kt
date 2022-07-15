package com.example.kotlin_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FViewModel : ViewModel() {


    val countDownTimerFlow = flow<Int> {

        val countDownFrom = 10
        var counter  = countDownFrom

        emit(countDownFrom)

        while (counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }

    }
    init {
        collectionInViewModel2()
    }
    private fun collectionInViewModel2() {
        viewModelScope
            .launch {
               /* countDownTimerFlow.filter { it % 3 == 0 }
                    .map {
                        it + it
                    }
                    .collect {
                       // println("Value : $it")
                    }*/
                   countDownTimerFlow.collect{
                       println("counter is: ${it}")
            }}
    }
    //LiveData comparisons
    private val _liveData = MutableLiveData<String>("Kotlin Live Data")
    val liveData : LiveData<String> = _liveData


    fun changeLiveDataValue () {
        _liveData.value = "Live Data"
    }

    //deger zorunlu
    //observable,
    private val _stateFlow = MutableStateFlow("Kotlin State Flow")

    val stateFLow = _stateFlow.asStateFlow()
    fun chanceStateFlowValue () {
        _stateFlow.value = "State Flow"
    }


    // state flow da bir shared flow dur
    // custom flow olusturmak icin kullanilir.
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun chanceSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }


    private fun collectionInViewModel() {

      viewModelScope
           .launch {
           countDownTimerFlow.
               filter { it%3 == 0 }
               .map {
                   it + it
               }
               .collect {
                   println("Value : $it")
           }

               countDownTimerFlow.collectLatest {
                   //akis devam eder fakat bir gecikme oldugu icin en sonuncuyu gosterir
                   //gecikme olmaz ise yine tum veriyi gosterir. illa sonuncuyu alan bir fonksiyon degil
                   //bir sorun olursa ne olacagiyla ilgili bir durum

                   delay(2000)
                   println("C :$it")
               }
           }
        /*
        //baska bir alma yontemi
          countDownTimerFlow.onEach {
              println("Value : $it")
          }.launchIn(viewModelScope) */ }
}