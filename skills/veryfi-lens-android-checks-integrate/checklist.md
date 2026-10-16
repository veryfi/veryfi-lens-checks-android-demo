# Validation checklist

Run this before delivering host code. Fix conflicts by removing extra flags.

- [ ] Dependency is `com.veryfi.lens:veryfi-lens-cheques-sdk:2.7.0.6` unless TIFF or size moves the host to `veryfi-lens-remote-models-sdk`
- [ ] `documentTypes` is only CHECK
- [ ] `stitchIsOn` is false and back-side flags match the request
- [ ] `retrieveCheckTiffIsOn` is false on the cheques artifact
- [ ] TIFF, if required, is documented with Remote Models and marked **Unconfirmed** end-to-end
- [ ] `useLegacyModels` left at default true
- [ ] Fraud not promised without `detect_fraud_on`
- [ ] Gallery / browser / `uploadImages` distinguished from Headless
- [ ] `img_original_paths` order called out as **Unconfirmed**
- [ ] Installed app size, not AAR size

- [ ] Every relative link in the generated host notes stays inside this skill folder
- [ ] Unconfirmed behavior the host depends on is labeled **Unconfirmed** in the delivery note
- [ ] Host `<application>` sets `android:allowBackup="false"` and `tools:replace="android:allowBackup"` so a new-project `true` does not fail the manifest merger
- [ ] No repository password, Demo `SettingsHolder` preset, or `VeryfiLensHelper` mutation is in the host code
