# Veryfi Lens Android — Checks — settings

Source of truth: Veryfi Lens Android SDK **2.7.0.6**, commit `7f33726c`. Names below were read from that tree. Items not fully confirmed are marked **Unconfirmed**. Do not copy repository passwords, keystores, or Demo-only types into the host app.


Always construct a fresh `VeryfiLensSettings()`. Android has no `VeryfiLens*Settings` subclasses. Pass that instance to `configure`. The SDK mutates it.

```kotlin
import com.veryfi.lens.helpers.DocumentType
import com.veryfi.lens.helpers.VeryfiLensSettings

fun checksSettings() = VeryfiLensSettings().apply {
    ignoreRemoteSettings = true            // default false; dashboard lens_settings overwrite this object during configure
    backupDocsToGallery = false            // default true; copies every scan into the device gallery
    moreMenuIsOn = false                   // default true; stored user prefs then replace host capture flags
    moreSettingsMenuIsOn = false           // default true
    autoCaptureOtherIsOn = false           // default true; OTHER auto-captures even when autoCaptureIsOn is false
    showNoDocumentDetectedWarning = false  // default true
    dictateIsOn = false                    // default true; obsolete voice capture, not a partner feature
    locationServicesIsOn = false           // default false; true raises a runtime location request
    glareDetectionIsOn = false             // default true; the glare gate is a weak algorithmic check

    dataExtractionEngine = VeryfiLensSettings.ExtractionEngine.VeryfiCloudAPI
    documentTypes = arrayListOf(DocumentType.CHECK)
    showDocumentTypes = false
    stitchIsOn = false                     // default true; checks are single frames
    autoCaptureIsOn = false                // default false
    checkSequenceMode = true               // default false; front-then-back UI
    checksBackIsOn = true                  // default false
    checksBackIsRequired = false           // default false; true forces both sides
    forceLandscapeImage = true             // default false
    autoSelectManualCropIsOn = true        // default false
    autoRotateIsOn = true                  // default false
    captureMarginRatio = 0.08              // default 0.0; sweet spot noted in KDoc
    checksCropMargin = 2                   // default 5
    checksMinAspectRatio = 1.9             // default 0.0 (internal detector defaults)
    checksMaxAspectRatio = 2.85            // default 0.0
    forceCropLayoutOnRetake = true         // default false
    cropLayoutShowDelay = 15.0             // default 0.0 seconds
    cropLayoutAspectRatio = 2.18           // default 0.0
    cropLayoutWidth = 60                   // default 80 (percent)
    cropLayoutCornerRadius = 5             // default 0
    cropLayoutOverlayAlpha = 0.3           // default 0.3
    validateCheckSize = true               // default false
    validateCheckSides = true              // default false; loads DetectCheckSide
    validateCheckCorners = false           // default false; can be aggressive
    allowSubmitCheckSideNotDetected = true // default true
    allowSubmitCheckCornersNotDetected = true // default true
    allowSubmitUndetectedDocsIsOn = false  // default true
    returnStitchedPDF = true               // default false
    galleryIsOn = true
}

```

## Defaults versus this recipe

Android initializers below are the property defaults in `VeryfiLensSettings`.

| Property | Android default | This recipe | Why |
|----------|-----------------|-------------|-----|
| `ignoreRemoteSettings` | false | true | Dashboard `lens_settings` would overwrite this object during configure |
| `backupDocsToGallery` | true | false | Default copies every scan into the device gallery |
| `moreMenuIsOn` | true | false | Stored user prefs would replace host capture flags on later configure calls |
| `moreSettingsMenuIsOn` | true | false | Settings sheet inside More |
| `autoCaptureOtherIsOn` | true | false | `OTHER` auto-captures even when `autoCaptureIsOn` is false |
| `showNoDocumentDetectedWarning` | true | false | Skip the no-document alert |
| `dictateIsOn` | true | false | Obsolete voice control; default is on |
| `glareDetectionIsOn` | true | false | Android default glare gate is a weak check |
| `locationServicesIsOn` | false | false | Leave off unless the host needs lat/lng on the package |

| `stitchIsOn` | true | false | Checks are not a stitch flow |
| `checksBackIsOn` | false | true | Prompt for the back |
| `checksBackIsRequired` | false | false | Set true only when both sides are mandatory |
| `checkSequenceMode` | false | true | Front-then-back sequence UI |
| `forceLandscapeImage` | false | true | Landscape check images |
| `captureMarginRatio` | 0.0 | 0.08 | Keep the check away from the frame edge |
| `checksCropMargin` | 5 | 2 | Margin around detected check edges |
| `checksMinAspectRatio` / `checksMaxAspectRatio` | 0.0 / 0.0 | 1.9 / 2.85 | 0 means the detector's internal defaults |
| `forceCropLayoutOnRetake` | false | true | Show the guide after a failed detection |
| `cropLayoutShowDelay` | 0.0 | 15.0 | Show the guide after 15 seconds |
| `validateCheckSize` / `validateCheckSides` | false | true | Reject size and side mismatches |
| `validateCheckCorners` | false | false | Leave false unless the customer wants the stricter corner gate |
| `allowSubmitUndetectedDocsIsOn` | true | false | Pair with the allow-submit check flags so users are not trapped |
| `returnStitchedPDF` | false | true | PDF of the captured side or sides |
| `retrieveCheckTiffIsOn` | false | false | Turn on only with the Remote Models artifact |


`checkSequenceMode` defaults to false. This recipe sets it true so the camera walks front, then back. `checksBackIsOn` is true so the back can be captured. Set `checksBackIsRequired = true` only when both sides are mandatory. Keep `packageMaxScans` at least 2 (the initializer is 5).

Remote Models artifact, TIFF:

```kotlin
settings.retrieveCheckTiffIsOn = true       // default false
settings.rejectInvalidCheckTIFF = true      // default false; retake when the TIFF file is missing
```

`rejectInvalidCheckTIFF` shows the invalid-TIFF alert from `ConfirmFragment` when the path is missing. Success keys are `img_check_front_tiff_path` and `img_check_back_tiff_path`.

`fraudDetectionIsOn` (default false) also needs `detect_fraud_on`. `useLegacyModels` stays at default true.


## Suggestions

- Confirm side order of `img_original_paths` on a device before mapping front and back.
- Prefer `allowSubmitCheckSideNotDetected = true` and `allowSubmitCheckCornersNotDetected = true` (the defaults) when validators are on.
- For install size or TIFF, measure the app with `veryfi-lens-remote-models-sdk` instead of quoting the cheques AAR.
- Gallery and `uploadImages(uris)` can submit an existing check photo. `uploadImages(paths)` will not take a PDF.

