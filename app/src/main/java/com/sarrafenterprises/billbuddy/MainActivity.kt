package com.sarrafenterprises.billbuddy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarrafenterprises.billbuddy.ui.theme.BillBuddyTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillBuddyTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Bill Buddy Desk", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally))

                        splitter(modifier = Modifier.padding(10.dp))
                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()){

                    Text(text = "Made with ❤ in India by Rajveer", fontSize = 8.sp)
                    }
                }
                }


            }
        }
    }
}

@Composable
fun splitter(modifier: Modifier,
                initiallyOpened : Boolean = false
) {
    var total by remember { mutableIntStateOf(0) }
    var split by remember { mutableIntStateOf(1) }
    var showDropDown by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var totalAmount by remember { mutableIntStateOf(total) }

    var isOpen by remember { mutableStateOf(initiallyOpened) }
    var alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec =  tween(
            durationMillis = 300
        ))
    var rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec =  tween(
            durationMillis = 300
        ))


    Card(modifier = modifier) {

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {


            OutlinedTextField(value = if (total != 0) total.toString() else "", onValueChange = {input ->
                // Convert the input to an integer, or default to 0 if the input is invalid
                total = input.toIntOrNull() ?: 0
            }, label = { Text("Enter amount to add on") },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(75f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Button(onClick = {
                if (total != 0) {
                    totalAmount += total
                    total = 0
                } }, modifier = Modifier.weight(25f)) {
                Text(text = "Add")
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(10.dp),
        ) {
            Text(text = "Total Amount: ")

            Text(text = totalAmount.toString())
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ){



            Text(text = "Split:", modifier = Modifier
                .weight(20f)
                .padding(start = 10.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )

            Button(onClick = { if (split > 1) split = (split - 1) }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Down arrow")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(text = split.toString(), fontSize = 20.sp)

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = { split = (split + 1) }) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Down arrow")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                total = 0
                split = 1
                totalAmount = 0
                isOpen = false
            }) {
                Text(text = "Reset", fontSize = 15.sp)
            }

            Button(onClick = {
                if (totalAmount != 0) {
                    isOpen = !isOpen
                } else {
                    Toast.makeText(context, "Please enter the amount!", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                Text(text = "Calculate", fontSize = 15.sp)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

    }
    Box (contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                transformOrigin = TransformOrigin(0.5f, 0f)
                rotationX = rotateX.value
            }
            .alpha(alpha.value)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Yellow) ) {
            Text(text = "Individual Amount: ", fontSize = 15.sp,color = Color.Black)
            var individualAmount = totalAmount.toFloat() / split
            Text(text = "Rs. ${individualAmount}", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Color.Black)
        }
    }
}
