# Testing the Plugin Locally

This guide explains how to test the `share-to-whatsapp` Capacitor plugin locally before publishing.

## Prerequisites

- Node.js and npm installed
- For iOS testing: Xcode and CocoaPods installed
- For Android testing: Android Studio and Android SDK installed
- A test Ionic/Capacitor app (or create one)

## Method 1: Using npm link (Recommended)

This is the easiest way to test your plugin locally.

### Step 1: Build the Plugin

First, build the plugin to generate the distribution files:

```bash
npm run build
```

### Step 2: Link the Plugin

In the plugin directory, create a global link:

```bash
npm link
```

### Step 3: Create or Use a Test App

If you don't have a test app, create one:

```bash
# Create a new Ionic app (if needed)
ionic start test-app tabs --type=angular --capacitor
cd test-app

# Or use an existing Capacitor app
```

### Step 4: Link Plugin in Test App

In your test app directory, link the plugin:

```bash
npm link share-to-whatsapp
```

### Step 5: Install Plugin in Test App

Add the plugin to your test app:

```bash
npm install share-to-whatsapp
npx cap sync
```

### Step 6: Use the Plugin in Your Test App

In your test app, import and use the plugin:

```typescript
import { Shareto } from 'share-to-whatsapp';

// Example: Share an image
async shareImage() {
  try {
    await Shareto.shareImage({
      base64: 'your-base64-image-string',
      fileName: 'test-image.png',
      phoneNumber: '1234567890', // Optional
      message: 'Check out this image!', // Optional
      bundleId: 'net.whatsapp.WhatsApp' // WhatsApp bundle ID
    });
    console.log('Image shared successfully');
  } catch (error) {
    console.error('Error sharing image:', error);
  }
}

// Example: Share a PDF
async sharePdf() {
  try {
    await Shareto.sharePdf({
      base64: 'your-base64-pdf-string',
      fileName: 'document.pdf',
      phoneNumber: '1234567890', // Optional
      message: 'Check out this PDF!', // Optional
      bundleId: 'net.whatsapp.WhatsApp' // WhatsApp bundle ID
    });
    console.log('PDF shared successfully');
  } catch (error) {
    console.error('Error sharing PDF:', error);
  }
}
```

### Step 7: Test on Platforms

#### iOS Testing

```bash
# Sync native code
npx cap sync ios

# Open in Xcode
npx cap open ios

# Run from Xcode or use:
npx cap run ios
```

#### Android Testing

```bash
# Sync native code
npx cap sync android

# Open in Android Studio
npx cap open android

# Run from Android Studio or use:
npx cap run android
```

## Method 2: Using Local File Path

Instead of `npm link`, you can reference the plugin directly in your test app's `package.json`:

```json
{
  "dependencies": {
    "share-to-whatsapp": "file:../share-to-whatsapp"
  }
}
```

Then run:

```bash
npm install
npx cap sync
```

## Method 3: Verify Plugin Build

Before testing in an app, you can verify the plugin builds correctly:

### Verify All Platforms

```bash
npm run verify
```

This runs:
- iOS build verification
- Android build verification
- Web build verification

### Verify Individual Platforms

```bash
# iOS only
npm run verify:ios

# Android only
npm run verify:android

# Web only
npm run verify:web
```

## Method 4: Test with Example Code

Create a simple test page in your test app:

```typescript
// home.page.ts
import { Component } from '@angular/core';
import { Shareto } from 'share-to-whatsapp';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor() {}

  async testShareImage() {
    // Convert a test image to base64 or use a sample
    const testImageBase64 = 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==';
    
    try {
      await Shareto.shareImage({
        base64: testImageBase64,
        fileName: 'test.png',
        message: 'Test image from plugin',
        bundleId: 'net.whatsapp.WhatsApp'
      });
    } catch (error) {
      console.error('Error:', error);
      alert('Error: ' + error);
    }
  }

  async testShareMessage() {
    try {
      await Shareto.shareImage({
        message: 'Hello from the plugin!',
        bundleId: 'net.whatsapp.WhatsApp'
      });
    } catch (error) {
      console.error('Error:', error);
      alert('Error: ' + error);
    }
  }
}
```

```html
<!-- home.page.html -->
<ion-header [translucent]="true">
  <ion-toolbar>
    <ion-title>Plugin Test</ion-title>
  </ion-toolbar>
</ion-header>

<ion-content [fullscreen]="true">
  <ion-header collapse="condense">
    <ion-toolbar>
      <ion-title size="large">Plugin Test</ion-title>
    </ion-toolbar>
  </ion-header>

  <div id="container">
    <ion-button expand="block" (click)="testShareImage()">
      Share Test Image
    </ion-button>
    
    <ion-button expand="block" (click)="testShareMessage()">
      Share Message Only
    </ion-button>
  </div>
</ion-content>
```

## Important Notes

### Bundle IDs

- **iOS WhatsApp**: `net.whatsapp.WhatsApp`
- **Android WhatsApp**: `com.whatsapp` (used in Android implementation)

### Testing Requirements

1. **WhatsApp must be installed** on the test device/emulator
2. **For iOS**: Make sure you have proper permissions configured
3. **For Android**: Ensure file provider is configured in the app's `AndroidManifest.xml`

### Debugging

- Check browser console for web platform errors
- Check Xcode console for iOS errors
- Check Android Studio Logcat for Android errors
- Use `console.log` statements in your plugin code

### Rebuilding After Changes

After making changes to the plugin:

1. Rebuild the plugin:
   ```bash
   npm run build
   ```

2. In your test app, sync again:
   ```bash
   npx cap sync
   ```

3. Rebuild and run your test app

## Troubleshooting

### Plugin not found
- Make sure you ran `npm link` in the plugin directory
- Make sure you ran `npm link share-to-whatsapp` in the test app
- Try `npm install` in the test app

### Changes not reflecting
- Rebuild the plugin: `npm run build`
- Sync Capacitor: `npx cap sync`
- Clean and rebuild the native project

### iOS build errors
- Run `cd ios && pod install && cd ..`
- Clean Xcode build folder (Product → Clean Build Folder)

### Android build errors
- Clean Gradle: `cd android && ./gradlew clean && cd ..`
- Invalidate caches in Android Studio


