package com.example.testapplication1.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun ModifierTest(
    inputText: TextFieldValue,
    onInputTextChanged: (TextFieldValue) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = inputText,
        onValueChange = onInputTextChanged,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .detectLazyListScroll(lazyListState) { focusManager.clearFocus() },
    )
}

private fun Modifier.detectLazyListScroll(
    lazyListState: LazyListState,
    onDrag: () -> Unit,
) = this then DetectLazyListScrollModifier(lazyListState, onDrag)

private data class DetectLazyListScrollModifier(
    val lazyListState: LazyListState,
    val onDrag: () -> Unit,
) : ModifierNodeElement<DetectLazyListScrollNode>() {
    override fun create(): DetectLazyListScrollNode = DetectLazyListScrollNode(lazyListState, onDrag)
    override fun update(node: DetectLazyListScrollNode) = node.update(lazyListState, onDrag)
    override fun InspectorInfo.inspectableProperties() {
        name = "DetectLazyListScrollModifier"
        properties["lazyListState"] = lazyListState
        properties["onDrag"] = onDrag
    }
}

private class DetectLazyListScrollNode(
    var lazyListState: LazyListState,
    var onDrag: () -> Unit,
) : Modifier.Node(), CompositionLocalConsumerModifierNode {
    private var detectJob: Job? = null
    private var actionJob: Job? = null

    fun update(lazyListState: LazyListState, onDrag: () -> Unit) {
        detectJob.cancelIfNotCompleted()
        actionJob.cancelIfNotCompleted()
        this.lazyListState = lazyListState
        this.onDrag = onDrag
        startDetect()
    }

    override fun onAttach() {
        super.onAttach()
        startDetect()
    }

    private fun startDetect() {
        if (!isAttached) return
        actionJob = lazyListState.interactionSource
            .getDraggedStateFlow()
            .filter { it }
            .onEach { onDrag() }
            .launchIn(coroutineScope)
    }

    private fun InteractionSource.getDraggedStateFlow(): StateFlow<Boolean> {
        val isDragged = MutableStateFlow(false)
        detectJob = coroutineScope.launch {
            val dragInteractions = mutableListOf<DragInteraction.Start>()
            interactions.collect { interaction ->
                when (interaction) {
                    is DragInteraction.Start -> dragInteractions.add(interaction)
                    is DragInteraction.Stop -> dragInteractions.remove(interaction.start)
                    is DragInteraction.Cancel -> dragInteractions.remove(interaction.start)
                }
                isDragged.value = dragInteractions.isNotEmpty()
            }
        }
        return isDragged
    }

    private fun Job?.cancelIfNotCompleted() {
        this ?: return
        if (!isCompleted) cancel()
    }
}
