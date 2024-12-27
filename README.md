# jetpack-navigation-anim-performance
Investigation of Jetpack Navigation Animation Performance Issue.

The app demonstrates the transition between two screens, each containing a list of items. For the layout of each list item, I've implemented different variations:

**For Android View + XML:**
1. Single `TextView`
2. `ImageView` + `TextView`
3. `CardView` wrapping (`ImageView` + `TextView`)

**For Compose:**
1. `Icon` + `Text`
2. `Card` wrapping `Text`

By default, each list item includes a `ripple` effect (in Compose) or `selectableItemBackground` (in Android View + XML). The app provides a setting to disable this effect, allowing you to observe the difference. The lag is noticeably worse when the effect is enabled, making navigation less smooth.

**Key Observations:**
1. The issue is not exclusive to Compose. It also occurs with Jetpack Navigation in traditional Android View + XML. However, the lag is more pronounced in Jetpack Compose.
2. The problem is related to the `ripple` effect or `selectableItemBackground` in Android View + XML. Simplifying the layout of the list items does not resolve the lag. The issue persists as long as the page contains views with these effects. If you remove the `ripple` or `selectableItemBackground`, the laggy will disappear.
3. Users with devices that support a 120Hz display rate may not notice the lag. However, it is evident on 60Hz displays.
4. The lag occurs in both debug and release modes, even when R8 is enabled.
5. The Compose version used in the app corresponds to the latest BOM release as of 2024.12.01.
6. The versions of Jetpack Navigation and Navigation Compose libraries tested include 2.8.5 and 2.7.5, with no observable differences between them.
7. Testing was conducted on the following devices:
    - OnePlus 9 (OxygenOS 14)
    - OnePlus Ace2 Pro (ColorOS 14)
    - Pixel 9 Pro XL (Android 15)
