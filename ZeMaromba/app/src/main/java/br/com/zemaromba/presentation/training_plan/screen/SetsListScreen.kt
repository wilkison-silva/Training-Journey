package br.com.zemaromba.presentation.training_plan.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.core_ui.ui.theme.Spacing
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.model.SetView
import br.com.zemaromba.presentation.training_plan.screen.state.SetListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetsListScreen(
    state: SetListState,
    onNavigateBack: () -> Unit,
    onOpenSet: (setId: Long) -> Unit,
    onCreateSet: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenYoutubeApp: (videoId: String) -> Unit,
    onCompleteSet: (setId: Long, isCompleted: Boolean) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }, title = {
                    Text(
                        modifier = Modifier.padding(start = Spacing.space_12dp),
                        text = state.trainingName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge
                    )
                }, actions = {
                    IconButton(
                        onClick = {
                            onOpenSettings()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Spacing.space_20dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onCreateSet()
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = Spacing.space_4dp),
                    text = stringResource(R.string.add_exercise),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            if (state.showMessage) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(Spacing.space_200dp),
                        painter = painterResource(id = R.drawable.ic_dumbell_wrist),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(Spacing.space_20dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.space_64dp),
                        text = stringResource(id = R.string.how_about_create_your_first_set),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.space_12dp),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(Spacing.space_24dp))
                    }
                    itemsIndexed(items = state.setListView,
                        itemContent = { _: Int, setView: SetView ->
                            SetCardItem(
                                setView = setView,
                                onClick = {
                                    onOpenSet(setView.id)
                                },
                                onOpenDemonstrationVideo = { videoId: String ->
                                    onOpenYoutubeApp(videoId)
                                },
                                onCompleteSet = { setId: Long, isCompleted: Boolean ->
                                    onCompleteSet(setId, isCompleted)
                                }
                            )
                        }
                    )
                    item {
                        Spacer(modifier = Modifier.height(Spacing.space_96dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SetCardItem(
    setView: SetView,
    onClick: () -> Unit,
    onOpenDemonstrationVideo: (videoId: String) -> Unit,
    onCompleteSet: (setId: Long, isCompleted: Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.space_20dp)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Spacing.space_20dp,
                        top = Spacing.space_20dp,
                        bottom = Spacing.space_28dp,
                        end = Spacing.space_20dp
                    ),
                text = setView.exerciseView.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            IconWithText(
                drawableRes = R.drawable.ic_repeat,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_series),
                    caption = setView.quantity.toString()
                ) + labelWithCaption(
                    label = stringResource(R.string.set_repetition),
                    caption = setView.repetitions.toString()
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_weight,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_weight),
                    caption = "${setView.weight} Kg"
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_stopwatch,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_resting_time),
                    caption = "${setView.restingTime} segundos"
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_tag,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_observation),
                    caption = setView.observation
                )
            )
            setView.exerciseView.videoId?.let { videoIdOnYoutube ->
                IconWithText(
                    drawableRes = R.drawable.ic_play_video_youtube,
                    labelAndDescription = AnnotatedString(text = stringResource(R.string.set_how_to_do_this_exercise)),
                    onClickText = {
                        onOpenDemonstrationVideo(videoIdOnYoutube)
                    }
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(all = Spacing.space_20dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onCompleteSet(setView.id, setView.completed)
                }
            ) {
                if (setView.completed) {
                    Icon(
                        modifier = Modifier.size(Spacing.space_20dp),
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Spacing.space_8dp))
                }
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    text = if (setView.completed) {
                        stringResource(R.string.set_completed)
                    } else {
                        stringResource(R.string.set_to_be_completed)
                    }
                )
            }
        }
    }
}

@Composable
fun IconWithText(
    @DrawableRes drawableRes: Int?,
    labelAndDescription: AnnotatedString,
    onClickText: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Spacing.space_20dp,
                bottom = Spacing.space_20dp,
                end = Spacing.space_20dp
            ),
        verticalAlignment = Alignment.Top
    ) {
        drawableRes?.let {
            Icon(
                modifier = Modifier
                    .padding(top = Spacing.space_2dp)
                    .size(Spacing.space_16dp),
                painter = painterResource(id = it),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(Spacing.space_12dp))
        }
        Text(
            modifier = Modifier
                .clickable(enabled = onClickText != null) {
                    if (onClickText != null) {
                        onClickText()
                    }
                },
            text = labelAndDescription,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

fun labelWithCaption(
    label: String,
    caption: String
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("$label: ")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Medium,
            )
        ) {
            append(caption)
        }
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SetsListScreenPreview() {
    val setsSampleList = listOf(
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                videoId = "12345667"
            ),
            weight = 12.0,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60.0
        ),
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                videoId = "12345667"
            ),
            weight = 12.0,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60.0
        ),
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                videoId = "12345667"
            ),
            weight = 12.0,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60.0
        )
    )
    ZeMarombaTheme {
        SetsListScreen(
            state = SetListState(
                setListView = setsSampleList,
                trainingName = "Semana 09 - Dia 02"
            ),
            onNavigateBack = {

            },
            onOpenSet = {

            },
            onCreateSet = {

            },
            onOpenSettings = {

            },
            onOpenYoutubeApp = {

            },
            onCompleteSet = { _, _ ->

            }
        )
    }
}