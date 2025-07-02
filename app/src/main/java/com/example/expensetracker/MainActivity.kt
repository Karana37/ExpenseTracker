package com.example.expensetracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

data class Expense(val name: String, val amount: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                ExpenseTrackerApp()
            }
        }
    }
}

@Composable
fun ExpenseTrackerApp() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    val expenses = remember { mutableStateListOf<Expense>() }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Expense Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (name.text.isNotBlank() && amount.text.isNotBlank()) {
                expenses.add(Expense(name.text, amount.text))
                Toast.makeText(context, "Expense added!", Toast.LENGTH_SHORT).show()
                name = TextFieldValue("")
                amount = TextFieldValue("")
            } else {
                Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Add Expense")
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            itemsIndexed(expenses) { index, expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = expense.name)
                        Text(text = "$${expense.amount}")
                    }

                    Button(onClick = {
                        expenses.removeAt(index)
                    }) {
                        Text("Delete")
                    }
                }
                Divider()
            }
        }
    }
}
