package com.chimbori.box.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chimbori.box.R

private val defaultTypography = Typography()

private fun googleSansFlex(weight: Int, opsz: Float, roundness: Float = 0f) = FontFamily(
  Font(
    R.font.googlesans_flex,
    weight = FontWeight(weight),
    variationSettings = FontVariation.Settings(
      FontVariation.weight(weight),
      FontVariation.width(100f),
      FontVariation.Setting("opsz", opsz),
      FontVariation.Setting("ROND", roundness),
    )
  )
)

internal val emphasized = Typography(
  displayLarge = defaultTypography.displayLarge.copy(
    fontFamily = googleSansFlex(500, 57f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 57.sp,
    lineHeight = 64.sp,
    letterSpacing = 0.sp,
  ),
  displayMedium = defaultTypography.displayMedium.copy(
    fontFamily = googleSansFlex(500, 45f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 45.sp,
    lineHeight = 52.sp,
    letterSpacing = 0.sp,
  ),
  displaySmall = defaultTypography.displaySmall.copy(
    fontFamily = googleSansFlex(500, 36f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 36.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.sp,
  ),
  headlineLarge = defaultTypography.headlineLarge.copy(
    fontFamily = googleSansFlex(500, 32f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp,
  ),
  headlineMedium = defaultTypography.headlineMedium.copy(
    fontFamily = googleSansFlex(500, 28f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp,
  ),
  headlineSmall = defaultTypography.headlineSmall.copy(
    fontFamily = googleSansFlex(500, 24f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
  ),
  titleLarge = defaultTypography.titleLarge.copy(
    fontFamily = googleSansFlex(500, 22f, roundness = 100f),
    fontWeight = FontWeight.W500,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
  ),
  titleMedium = defaultTypography.titleMedium.copy(
    fontFamily = googleSansFlex(600, 16f, roundness = 100f),
    fontWeight = FontWeight.W600,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
  ),
  titleSmall = defaultTypography.titleSmall.copy(
    fontFamily = googleSansFlex(600, 14f, roundness = 100f),
    fontWeight = FontWeight.W600,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
  ),
)

internal val baseline = Typography(
  displayLarge = defaultTypography.displayLarge.copy(
    fontFamily = googleSansFlex(400, 57f),
    fontWeight = FontWeight.W400,
    fontSize = 57.sp,
    lineHeight = 64.sp,
    letterSpacing = 0.sp,
  ),
  displayMedium = defaultTypography.displayMedium.copy(
    fontFamily = googleSansFlex(400, 45f),
    fontWeight = FontWeight.W400,
    fontSize = 45.sp,
    lineHeight = 52.sp,
    letterSpacing = 0.sp,
  ),
  displaySmall = defaultTypography.displaySmall.copy(
    fontFamily = googleSansFlex(400, 36f),
    fontWeight = FontWeight.W400,
    fontSize = 36.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.sp,
  ),
  headlineLarge = defaultTypography.headlineLarge.copy(
    fontFamily = googleSansFlex(400, 32f),
    fontWeight = FontWeight.W400,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp,
  ),
  headlineMedium = defaultTypography.headlineMedium.copy(
    fontFamily = googleSansFlex(400, 28f),
    fontWeight = FontWeight.W400,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp,
  ),
  headlineSmall = defaultTypography.headlineSmall.copy(
    fontFamily = googleSansFlex(400, 24f),
    fontWeight = FontWeight.W400,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
  ),
  titleLarge = defaultTypography.titleLarge.copy(
    fontFamily = googleSansFlex(400, 22f),
    fontWeight = FontWeight.W400,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
  ),
  titleMedium = defaultTypography.titleMedium.copy(
    fontFamily = googleSansFlex(500, 16f),
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
  ),
  titleSmall = defaultTypography.titleSmall.copy(
    fontFamily = googleSansFlex(500, 14f),
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
  ),
  bodyLarge = defaultTypography.bodyLarge.copy(
    fontFamily = googleSansFlex(400, 16f),
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
  ),
  bodyMedium = defaultTypography.bodyMedium.copy(
    fontFamily = googleSansFlex(400, 14f),
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
  ),
  bodySmall = defaultTypography.bodySmall.copy(
    fontFamily = googleSansFlex(400, 12f),
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
  ),
  labelLarge = defaultTypography.labelLarge.copy(
    fontFamily = googleSansFlex(500, 14f),
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
  ),
  labelMedium = defaultTypography.labelMedium.copy(
    fontFamily = googleSansFlex(500, 12f),
    fontWeight = FontWeight.W500,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
  ),
  labelSmall = defaultTypography.labelSmall.copy(
    fontFamily = googleSansFlex(500, 11f),
    fontWeight = FontWeight.W500,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
  ),
)

/** Google Sans Flex for every Material text style: emphasized for display/headline/title, baseline for body/label. */
internal val boxTypography = Typography(
  displayLarge = emphasized.displayLarge,
  displayMedium = emphasized.displayMedium,
  displaySmall = emphasized.displaySmall,

  headlineLarge = emphasized.headlineLarge,
  headlineMedium = emphasized.headlineMedium,
  headlineSmall = emphasized.headlineSmall,

  titleLarge = emphasized.titleLarge,
  titleMedium = emphasized.titleMedium,
  titleSmall = emphasized.titleSmall,

  bodyLarge = baseline.bodyLarge,
  bodyMedium = baseline.bodyMedium,
  bodySmall = baseline.bodySmall,

  labelLarge = baseline.labelLarge,
  labelMedium = baseline.labelMedium,
  labelSmall = baseline.labelSmall,
)
