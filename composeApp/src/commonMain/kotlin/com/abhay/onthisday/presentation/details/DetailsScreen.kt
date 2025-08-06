package com.abhay.onthisday.presentation.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.abhay.onthisday.presentation.components.LoadingContent
import com.abhay.onthisday.presentation.ui.Cream
import onthisday.composeapp.generated.resources.Res
import onthisday.composeapp.generated.resources.grainy_old_background
import onthisday.composeapp.generated.resources.vintage_grunge
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    identifierTitle: String,
    viewModel: DetailsViewModel,
    onBackPressed: () -> Unit = {},
    imageLink: String,
    title: String
) {
    LaunchedEffect(identifierTitle) {
        viewModel.getDetailOf(identifierTitle)
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DetailsTopAppbar(
                title = state.value.title,
                onBackPressed = onBackPressed,
                scrollBehavior
            )
        },
        containerColor = Color(0xFFF7F3E9)
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.vintage_grunge),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            when {
                state.value.error != null -> {
                    ErrorContent(paddingValues, state.value.error!!.asString())
                }

                else -> {
                    DetailContent(
                        animatedVisibilityScope = animatedVisibilityScope,
                        title = title,
                        detail = state.value.detail,
                        imageLink = imageLink,
                        modifier = Modifier
                            .padding(paddingValues),
                        isLoading = state.value.isLoading,
                        paddingValues = paddingValues,
                        identifierTitle = identifierTitle
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppbar(
    title: String,
    onBackPressed: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✦ HISTORICAL CHRONICLE ✦",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF2C1810),
                    textAlign = TextAlign.Center
                )

                if (title.isNotBlank()) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C1810),
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        Cream,
                        RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2C1810)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = Color(0xFF2C1810)
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun ErrorContent(paddingValues: PaddingValues, errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF7))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFCD5C5C), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠️",
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Chronicle Lost",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C1810),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF6B4E3D),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    title: String,
    imageLink: String,
    detail: String,
    isLoading: Boolean,
    paddingValues: PaddingValues,
    identifierTitle: String
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        )
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Image and gradient if image not available
                if (imageLink.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "eventImage${identifierTitle}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(imageLink)
                                .crossfade(true)
                                .build(),
                            contentDescription = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .defaultMinSize(minHeight = 250.dp)
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                .zIndex(0f),
                            contentScale = ContentScale.Fit,
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFF4E4BC),
                                        Color(0xFFE6D3A3),
                                        Color(0xFFD4AF37).copy(alpha = 0.5f)
                                    ),
                                    radius = 600f
                                )
                            )
                            .zIndex(0f)
                    )
                }

                // Vertical Black Gradient over the whole image and title box container
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.6f),
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                        .zIndex(1f)
                )

                // Decorative icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .zIndex(10f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "❦", fontSize = 28.sp, color = Color(0xFFD4AF37))
                    Text(text = "✦", fontSize = 24.sp, color = Color(0xFFD4AF37))
                    Text(text = "❦", fontSize = 28.sp, color = Color(0xFFD4AF37))
                }

                // Title
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(24.dp)
                        .zIndex(10f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp,
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "eventTitle${identifierTitle}"),
                            animatedVisibilityScope
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(4.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xFFD4AF37),
                                        Color(0xFFFFD700),
                                        Color(0xFFD4AF37),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }


            // CONTENT SECTION
            Column(
                modifier = Modifier
                    .background(Cream)
                    .padding(18.dp)
            ) {

                if (isLoading) {
                    LoadingContent(paddingValues = paddingValues)
                } else {
                    EventDetail(
                        detail = detail,
                    )
                }


                // Add decorative footer
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✦ ❦ ✦",
                        fontSize = 20.sp,
                        color = Color(0xFFD4AF37).copy(alpha = 0.7f),
                        letterSpacing = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
fun EventDetail(
    detail: String,
) {
    val parsedLines = detail.lines()
    parsedLines.forEach { line ->
        val trimmedLine = line.trim()
        val isHeading = trimmedLine.startsWith("=")

        if (isHeading) {
            val headingLevel = trimmedLine.takeWhile { it == '=' }.length
            val headingText = trimmedLine.trim('=').trim()

            val fontSize = when (headingLevel) {
                2 -> 22.sp
                3 -> 20.sp
                else -> 16.sp
            }

            val fontWeight = when (headingLevel) {
                2 -> FontWeight.Bold
                3 -> FontWeight.Medium
                else -> FontWeight.Normal
            }

            // Decorative element before major headings
            if (headingLevel == 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "❦ ❦ ❦",
                        fontSize = 16.sp,
                        color = Color(0xFFD4AF37),
                        letterSpacing = 8.sp
                    )
                }
            }

            Text(
                text = headingText,
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF6B4E3D),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (headingLevel == 2) 8.dp else 20.dp,
                        bottom = 10.dp
                    ),
                textAlign = TextAlign.Start
            )
        } else if (trimmedLine.isNotBlank()) {
            Text(
                text = trimmedLine,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF2C1810),
                lineHeight = 28.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .padding(bottom = 12.dp)
            )
        }
    }

}