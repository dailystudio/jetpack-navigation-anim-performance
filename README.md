# Jetpack Navigation Animation Performance Issue

This repo is dedicated for the investigation of a performance issue encountered when using Jetpack Navigation animations. The issue manifests as lag during screen transitions and is observed across different layouts and effects. The goal is to analyze the problem, document the application setup, and outline the testing and analysis performed. 

Hopefully, this project can help relevant personnel address this issue. 

## Problem Description

The core issue is that when using the Jetpack Navigation library, if animations are enabled during screen transitions, the interface experiences noticeable lag regardless of whether traditional Android Views or Jetpack Compose are used. This lag occurs as long as the UI elements include click feedback backgrounds. 

This interactive background refers to: 
- Android Views: `selectableItemBackground`
- Compose: `ripple` effect.

## Application Features

The application demonstrates a transition between two screens, each containing a list of items. The list items are built using both Android View + XML and Jetpack Compose to test the performance under different UI paradigms. The app allows users to enable or disable UI effects like the `ripple` effect or `selectableItemBackground`.

### List Item Layout Variations:
#### For Android View + XML:
1. A single `TextView`
2. An `ImageView` paired with a `TextView`
3. A `CardView` wrapping (`ImageView` + `TextView`)

#### For Jetpack Compose:
1. `Icon` paired with `Text`
2. A `Card` wrapping `Text`

By default, each list item includes:
- `ripple` effect (Compose)
- `selectableItemBackground` (Android View + XML)

Users can toggle these effects to observe their impact on navigation performance.

## Testing Process

1. Navigate between two screens containing lists of items.
2. Test with default settings (`ripple` or `selectableItemBackground` enabled).
3. Disable `ripple` or `selectableItemBackground` and repeat the tests.
4. Compare performance on 60Hz and 120Hz display devices.
5. Test in both debug and release builds with R8 enabled.

## Devices Tested:
1. OnePlus 9 (OxygenOS 14, 60Hz)
2. OnePlus Ace2 Pro (ColorOS 14, 60 / 120Hz)
3. Pixel 9 Pro XL (Android 15, 60 / 120Hz)

## Analysis

1. **Effect of UI Elements:**
    - The `ripple` effect and `selectableItemBackground` are primary contributors to the lag. Removing these effects eliminates the lag, confirming their role in the issue.

2. **Impact of Layout Complexity:**
    - Simplifying the layout of list items does not resolve the issue. Even the simplest layouts with a single `TextView` exhibit the lag when the effect is enabled.

3. **Device Variations:**
    - Devices with 120Hz displays mitigate the perception of lag due to the higher refresh rate. However, the issue is still present and measurable on these devices.

4. **Build Configuration:**
    - The problem persists across debug and release builds, ruling out debug-related overhead as a factor.

5. **Library Versions:**
    - No significant differences were observed between the tested versions of Jetpack Navigation and Navigation Compose.

## Conclusion

The performance issue is strongly linked to the use of the `ripple` effect in Jetpack Compose and the `selectableItemBackground` in Android View. These effects significantly impact the smoothness of navigation animations, especially on devices with 60Hz displays. 

Future work may involve:
- Exploring alternative implementations of touch feedback effects.
- Reporting the issue to the Jetpack development team. 
  - Jetpack Compose: [https://issuetracker.google.com/issues/301488789](https://issuetracker.google.com/issues/301488789)
- Experimenting with newer library versions as they become available.

This investigation highlights the need for careful consideration of UI effects and their impact on performance, particularly in applications targeting a wide range of devices.

