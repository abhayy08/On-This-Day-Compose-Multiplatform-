package com.abhay.onthisday.presentation.home_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.abhay.onthisday.domain.model.Event
import com.abhay.onthisday.presentation.components.LoadingContent
import com.abhay.onthisday.presentation.ui.*
import com.abhay.onthisday.presentation.util.formatIsoTimeToDisplay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel,
    onEventClicked: (String, String, String) -> Unit = {_,_,_ ->}
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.padding(bottom = 8.dp),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✦ ON THIS DAY ✦",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF2C1810),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Discover the events that shaped our world",
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF6B4E3D),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFFF7F3E9),
                    scrolledContainerColor = Color(0xFFF7F3E9),
                    titleContentColor = Color(0xFF2C1810)
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = Color(0xFFF7F3E9)
    ) { paddingValues ->
        if(state.value.isLoading) {
            LoadingContent(paddingValues)
        }else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(state.value.events, key = { it.title + it.timeStamp }) { event ->
                    EventCard(
                        animatedVisibilityScope = animatedVisibilityScope,
                        event = event, onClick = { onEventClicked(event.identifierTitle, event.originalImage, event.title) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.EventCard(
    animatedVisibilityScope: AnimatedVisibilityScope,
    event: Event, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color(0xFFD4AF37),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (event.thumbnail.isNotBlank() || event.originalImage.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(
                                    key = "eventImage${event.identifierTitle}",
                                ),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(event.originalImage)
                                .crossfade(true)
                                .build(),
                            contentDescription = event.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFF4E4BC),
                                        Color(0xFFE6D3A3),
                                        Color(0xFFD4AF37).copy(alpha = 0.3f)
                                    )
                                )
                            )
                    )
                }

                // Vignette overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF2C1810).copy(alpha = 0.3f)
                                ),
                                radius = 800f
                            )
                        )
                )

                // Decorative corner elements
                Text(
                    text = "❦",
                    fontSize = 24.sp,
                    color = Color(0xFFD4AF37),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                )

                Text(
                    text = "❦",
                    fontSize = 24.sp,
                    color = Color(0xFFD4AF37),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }

            // Content section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFDF7))
                    .padding(20.dp)
            ) {
                // Divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD4AF37))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date
                Text(
                    text = formatIsoTimeToDisplay(event.timeStamp),
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8B4513),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Title
                Text(
                    text = event.title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C1810),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            rememberSharedContentState(key = "eventTitle${event.identifierTitle}"),
                            animatedVisibilityScope
                        )

                )

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Text(
                    text = event.extract.takeIf { it.isNotBlank() } ?: event.description,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF5D4037),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Justify,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD4AF37))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "❦ Continue Reading ❦",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8B4513),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}