package com.example.calcu_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt
import kotlin.math.E

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    var displayText by remember { mutableStateOf("0") }
    var fullOperation by remember { mutableStateOf("") }
    var currentOperation by remember { mutableStateOf<String?>(null) }
    var storedNumber by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pantalla para la operación completa
        Text(
            text = fullOperation,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.End
        )

        // Pantalla para el resultado
        Text(
            text = displayText,
            fontSize = 34.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.End
        )

        // Botones
        CalculatorButtonRow(listOf("C", "(", ")", "/")) { button ->
            handleButtonClick(
                button = button,
                displayText = displayText,
                fullOperation = fullOperation,
                storedNumber = storedNumber,
                currentOperation = currentOperation,
                updateDisplayText = { displayText = it },
                updateFullOperation = { fullOperation = it },
                updateStoredNumber = { storedNumber = it },
                updateCurrentOperation = { currentOperation = it }
            )
        }

        CalculatorButtonRow(listOf("7", "8", "9", "*")) { button ->
            handleButtonClick(
                button = button,
                displayText = displayText,
                fullOperation = fullOperation,
                storedNumber = storedNumber,
                currentOperation = currentOperation,
                updateDisplayText = { displayText = it },
                updateFullOperation = { fullOperation = it },
                updateStoredNumber = { storedNumber = it },
                updateCurrentOperation = { currentOperation = it }
            )
        }

        CalculatorButtonRow(listOf("4", "5", "6", "-")) { button ->
            handleButtonClick(
                button = button,
                displayText = displayText,
                fullOperation = fullOperation,
                storedNumber = storedNumber,
                currentOperation = currentOperation,
                updateDisplayText = { displayText = it },
                updateFullOperation = { fullOperation = it },
                updateStoredNumber = { storedNumber = it },
                updateCurrentOperation = { currentOperation = it }
            )
        }

        CalculatorButtonRow(listOf("1", "2", "3", "+")) { button ->
            handleButtonClick(
                button = button,
                displayText = displayText,
                fullOperation = fullOperation,
                storedNumber = storedNumber,
                currentOperation = currentOperation,
                updateDisplayText = { displayText = it },
                updateFullOperation = { fullOperation = it },
                updateStoredNumber = { storedNumber = it },
                updateCurrentOperation = { currentOperation = it }
            )
        }

        // Añadimos los botones de "e", "√" y ajustamos los otros botones
        CalculatorButtonRow(listOf("0", ".", "e", "√", "=")) { button ->
            handleButtonClick(
                button = button,
                displayText = displayText,
                fullOperation = fullOperation,
                storedNumber = storedNumber,
                currentOperation = currentOperation,
                updateDisplayText = { displayText = it },
                updateFullOperation = { fullOperation = it },
                updateStoredNumber = { storedNumber = it },
                updateCurrentOperation = { currentOperation = it }
            )
        }
    }
}

@Composable
fun CalculatorButtonRow(buttonLabels: List<String>, onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for (label in buttonLabels) {
            Button(
                onClick = { onButtonClick(label) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            ) {
                Text(label)
            }
        }
    }
}

fun handleButtonClick(
    button: String,
    displayText: String,
    fullOperation: String,
    storedNumber: Double,
    currentOperation: String?,
    updateDisplayText: (String) -> Unit,
    updateFullOperation: (String) -> Unit,
    updateStoredNumber: (Double) -> Unit,
    updateCurrentOperation: (String?) -> Unit
) {
    when (button) {
        "C" -> {
            updateDisplayText("0")
            updateFullOperation("")
            updateStoredNumber(0.0)
            updateCurrentOperation(null)
        }
        "=" -> {
            currentOperation?.let {
                val result = calculateResult(storedNumber, displayText.toDoubleOrNull() ?: 0.0, it)
                updateDisplayText(result.toString())
                updateFullOperation("$fullOperation = $result")
                updateStoredNumber(result)
                updateCurrentOperation(null)
            }
        }
        "+", "-", "*", "/" -> {
            if (currentOperation == null) {
                updateStoredNumber(displayText.toDoubleOrNull() ?: 0.0)
                updateFullOperation("$displayText $button")
            } else {
                val result = calculateResult(storedNumber, displayText.toDoubleOrNull() ?: 0.0, currentOperation)
                updateDisplayText(result.toString())
                updateFullOperation("$fullOperation $displayText $button")
                updateStoredNumber(result)
            }
            updateCurrentOperation(button)
            updateDisplayText("0")
        }
        "e" -> {
            updateDisplayText(E.toString())
            updateFullOperation(fullOperation + "e")
        }
        "√" -> {
            val currentNumber = displayText.toDoubleOrNull() ?: 0.0
            val result = sqrt(currentNumber)
            updateDisplayText(result.toString())
            updateFullOperation("√$currentNumber = $result")
            updateStoredNumber(result)
            updateCurrentOperation(null)
        }
        else -> {
            if (displayText == "0") {
                updateDisplayText(button)
            } else {
                updateDisplayText(displayText + button)
            }
            if (currentOperation == null) {
                updateFullOperation(fullOperation + button)
            } else {
                updateFullOperation(fullOperation + button)
            }
        }
    }
}

fun calculateResult(num1: Double, num2: Double, operation: String): Double {
    return when (operation) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN // Maneja la división por cero
        else -> num2
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}


