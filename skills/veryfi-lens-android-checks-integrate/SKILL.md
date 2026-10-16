---
name: veryfi-lens-android-checks-integrate
description: "Integrates the Veryfi Lens Android Checks SDK (com.veryfi.lens:veryfi-lens-cheques-sdk:2.7.0.6): Maven setup, permissions, VeryfiLens.configure, check VeryfiLensSettings, front and back capture, delegate callbacks, showCamera, showGallery, showDocumentBrowser, and uploadImages. Use when adding bank-check capture on Android, including TIFF via Remote Models, fraud gating, or check upload troubleshooting."
---

# Veryfi Lens Android — Checks

Walk a host Android app from Gradle to a working first scan. Change the fewest flags. Do not invent SDK APIs. Facts were read from the Android SDK at version `2.7.0.6` (commit `7f33726c`). Anything not confirmed there is marked **Unconfirmed**.

**Coordinate:** `com.veryfi.lens:veryfi-lens-cheques-sdk:2.7.0.6`  
**Gradle flavor:** `lensCheques`  
**Allowed document types:** `DocumentType.CHECK` only.

Public entry point: `com.veryfi.lens.VeryfiLens` with `com.veryfi.lens.VeryfiLensDelegate`. Settings type: `com.veryfi.lens.helpers.VeryfiLensSettings`.

This folder is complete by itself. Duplicate guidance across products is intentional.

## Pick a workflow

- **Quick** — the host already asked for this product. Ask nothing else. Apply the settings in [settings.md](settings.md), wire [implementation.md](implementation.md), and state the assumptions below.
- **Guided** — something material is missing. Ask only the unknown questions (at most four, one message), then continue.

### Quick workflow

```
veryfi-lens-android-checks-integrate quick:
- [ ] Confirm this artifact (install.md)
- [ ] Add the Maven repository and this one dependency
- [ ] Permissions (install.md)
- [ ] Credentials + settings from settings.md
- [ ] setDelegate, then configure { ok -> }, then showCamera() only when ok is true
- [ ] Handle the callbacks in callbacks.md
- [ ] Run the conflicts in troubleshooting.md
```

Assumptions when the host was specific about the product and silent on the rest: Lens camera, `CHECK`, Cloud API, front-then-back optional, stitch off, gallery on, TIFF off until Remote Models is chosen.

### Guided workflow

1. **Sides** — front only, or front and optional/required back?
2. **Entry** — camera, gallery, files, or `uploadImages`?
3. **TIFF** — does the host need `img_check_front_tiff_path` / `img_check_back_tiff_path`?
4. **Fraud** — is backend `detect_fraud_on` already enabled?

Defaults: camera, Cloud API, back optional, TIFF off, fraud off.


## Rules

1. Artifact `veryfi-lens-cheques-sdk` and `documentTypes = arrayListOf(DocumentType.CHECK)`.
2. `stitchIsOn = false`. Check backs use `checksBackIsOn`, `checksBackIsRequired`, and `checkSequenceMode`.
3. `retrieveCheckTiffIsOn` stays false on this artifact unless the host switches to Remote Models. See [clarifications.md](clarifications.md).
4. `img_original_paths` is reversed in code relative to capture order. Which index is the front is **Unconfirmed** until a device test.
5. `uploadImages` is the UI file pipeline on this artifact. It is not Headless.


## Do not

- Copy a repository password, keystore, or credential from the SDK repo or the Demo app. Maven access comes from Veryfi. Keep it in `~/.gradle/gradle.properties` or CI secrets.
- Depend on a second Lens UI flavor, or on `com.veryfi.lens.core:veryfi-lens-core-sdk` (email and obsolete dictation; adds `WRITE_CONTACTS` and `RECORD_AUDIO`).
- Treat Demo types as the client contract: `SettingsHolder`, Demo analyzers, `ImageProxyUtils`, `VeryfiLensHelper`.
- Set `dictateIsOn = true` or call `VeryfiLensCore.showDictation()`.
- Set `anyDocumentType` (deprecated local regex OCR). A new template id is `anyDocumentTemplate`, and only after Veryfi support confirms it.
- Set `useLegacyModels` (default true; no partner contract).
- Quote AAR size as the installed app size. Compare the final app with the SDK linked and removed. [SDK binary sizes](https://docs.veryfi.com/lens/mobile/settings/sdk_binary_sizes/).
- Treat `allowSubmittingScreenshots` (rejects imported screenshots) and `enableScreenshots` (device screenshots of Lens screens, default true) as one switch.
- Set `retrieveCheckTiffIsOn = true` on `veryfi-lens-cheques-sdk` and promise TIFF files.
- Set `useLegacyModels = false` because the Demo does. Leave the default true.
- Turn on `stitchIsOn` for checks.
- Leave `allowSubmitUndetectedDocsIsOn` false while also setting the check allow-flags false, unless the host wants users blocked.


## What to hand back

1. `implementation "com.veryfi.lens:veryfi-lens-cheques-sdk:2.7.0.6"` or, when TIFF or install size requires it, `veryfi-lens-remote-models-sdk` with `documentTypes` still `CHECK`.
2. Delegate, configure callback, `showCamera()`.
3. `checksSettings()` including explicit back-side and validation flags.
4. A TIFF note marked **Unconfirmed** if the host needs TIFFs.
5. Fraud described as backend-gated.


## Files in this folder

- [install.md](install.md) — Maven coordinate, requirements, permissions, app size
- [implementation.md](implementation.md) — configure, delegate, and launch or camera lifecycle
- [settings.md](settings.md) — `VeryfiLensSettings` for this product, with Android defaults
- [callbacks.md](callbacks.md) — delegate payloads
- [troubleshooting.md](troubleshooting.md) — symptom, cause, fix
- [clarifications.md](clarifications.md) — Unconfirmed items, Any-Documents, fraud, field detection
- [checklist.md](checklist.md) — validation checklist before handing code to the host

