package com.paulo.pdm.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import com.paulo.pdm.todo.R
import com.paulo.pdm.todo.data.TaskRequest
import com.paulo.pdm.todo.model.Task
import com.paulo.pdm.todo.ui.theme.ToDo


@Composable
fun TaskItemView(task: Task, taskRequest: TaskRequest?) {
    var checked = remember { mutableStateOf(task.is_done) }
    var maxSize = remember { mutableStateOf(1) }
    var visibleState = remember { mutableStateOf(false) }


    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier =
        Modifier
            .padding(horizontal = 5.dp, vertical = 2.5.dp)
            .fillMaxWidth()
    ) {
        ShowDialog(visible = visibleState, task = task, taskRequest = taskRequest)
        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            visibleState.value = true;
                        },
                        onTap = {
                            if (maxSize.value == 1) maxSize.value = 1000 else maxSize.value = 1
                        }
                    )
                }){
            Box(
                modifier =
                Modifier
                    .background(if (task.is_urgent) Color.Red else Color.Green)
                    .width(20.dp)
                    .fillMaxHeight()
            )
            Text(
                task.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxSize.value,
                style = TextStyle(
                    textDecoration = TextDecoration.None,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onSecondary
                ),
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .align(alignment = Alignment.CenterVertically)
            )
            Checkbox(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = !checked.value
                    task.is_done = checked.value
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.onSecondary,
                ),
                modifier = Modifier
                    .width(50.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
            Text(
                stringResource(id = R.string.txt_done),
                color = MaterialTheme.colors.onSecondary,
                modifier =
                Modifier
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    textDecoration = TextDecoration.None,
                    fontStyle = FontStyle.Normal,

                    )
            )
        }
    }
}

@Composable
fun ShowDialog(visible: MutableState<Boolean>, task: Task, taskRequest: TaskRequest?) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            dismissButton = {
                TextButton(onClick = { visible.value = false }) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    visible.value = false
                    taskRequest?.deleteTask(task = task)
                }) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            },
            title = { Text(text = "Delete", color = MaterialTheme.colors.onSecondary) },
            text = { Text(text = "Want to Delete this Task?", color = MaterialTheme.colors.onSecondary) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTaskItemView() {
    val task = Task(null, false, "Tarefa 1", true)
    ToDo(darkTheme = false) {
        TaskItemView(task, null)
    }
}
