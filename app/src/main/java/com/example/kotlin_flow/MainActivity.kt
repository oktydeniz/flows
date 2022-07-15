package com.example.kotlin_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_flow.ui.theme.KotlinflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //init
            KotlinflowTheme() {
                val vModel : FViewModel by viewModels()
                SecondScreen(vModel = vModel)
            }

        }
    }
}

@Composable
fun Screen(v :String) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = v, fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(
            Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinflowTheme {
       // Screen()
    }
}

@Composable
fun FirstScreen(vModel : FViewModel) {
    val counter = vModel.countDownTimerFlow.collectAsState(initial = 10)
    KotlinflowTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Screen(counter.value.toString())
        }
    }
}

@Composable
fun SecondScreen(vModel : FViewModel) {
    val liveDataValue = vModel.liveData.observeAsState()

    val stateFlowValue = vModel.stateFLow.collectAsState()

    val sharedFlowValue = vModel.sharedFlow.collectAsState(initial = "")
    
    androidx.compose.material.Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier =  Modifier.align(Alignment.Center)) {


                Text(text = liveDataValue.value ?: "")
                Button(onClick = {
                    vModel.changeLiveDataValue()
                }) {
                    Text(text = "Live Data Button")
                }
                Spacer(modifier = Modifier.height(10.dp))


                Text(text = stateFlowValue.value)
                Button(onClick = {
                    vModel.chanceStateFlowValue()
                }) {
                    Text(text = "State Flow Button")
                }


                Spacer(modifier = Modifier.height(10.dp))

                Text(text = sharedFlowValue.value)
                Button(onClick = {
                    vModel.chanceSharedFlowValue()
                }) {
                    Text(text = "Shared Flow Button")
                }
            }
        }
        
    }
}