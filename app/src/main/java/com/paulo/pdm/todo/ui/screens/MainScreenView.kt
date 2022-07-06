package com.paulo.pdm.todo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulo.pdm.todo.R
import com.paulo.pdm.todo.data.TaskRequest
import com.paulo.pdm.todo.data.TasksSingleton
import com.paulo.pdm.todo.model.Task
import com.paulo.pdm.todo.ui.components.TaskItemView

@Composable
fun MainScreenView(taskRequest: TaskRequest) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 40.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        },
        bottomBar = {
            bottom(taskRequest)
        }
    ) { innerPadding ->
        taskList(innerPadding = innerPadding, taskRequest)
    }
}


@Composable
fun taskList(innerPadding: PaddingValues, taskRequest: TaskRequest) {

    Box(modifier = Modifier.padding(innerPadding)) {
        Column {
            LazyColumn {
                items(TasksSingleton.getTasks()) { task ->
                    TaskItemView(task = task, taskRequest = taskRequest)
                }
            }
        }
    }
}

@Composable
fun bottom(taskRequest: TaskRequest) {
    val isUrgent = remember { mutableStateOf(false) }
    val taskName = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Row {
            Text(
                text = stringResource(id = R.string.txt_isUrgent),
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSecondary
            )
            Switch(
                checked = isUrgent.value,
                onCheckedChange = {
                    isUrgent.value = !isUrgent.value
                },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.secondary,
                    checkedThumbColor = MaterialTheme.colors.onSecondary
                )
            )
        }
        Column {
            OutlinedTextField(
                value = taskName.value,
                onValueChange = { value ->
                    taskName.value = value
                },
                colors = outlinedTextFieldColors(textColor = MaterialTheme.colors.onSecondary),
                placeholder = { Text(stringResource(id = R.string.txt_input)) },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
            )
        }
        Column {
            Button(
                onClick = {
                    if (taskName.value.text != "") {
                        taskRequest.createTask(
                            Task(
                                is_urgent = isUrgent.value,
                                description = taskName.value.text,
                                is_done = false
                            )
                        )
                        taskName.value = TextFieldValue("")
                        isUrgent.value = false
                    }
                },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    stringResource(id = R.string.txt_btn),
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}
