# AndroidExtensions

A collection of useful Kotlin extension functions for Android development that simplify common tasks and improve code readability.

## Features

- **Context Extensions**: Toast helpers, resource access, DP-PX conversion, SharedPreferences access, network checks
- **View Extensions**: Visibility helpers, margin updates, keyboard management, debounced click listeners
- **EditText Extensions**: Text access and validation helpers
- **String Extensions**: Email validation, date formatting
- **Fragment Extensions**: Simplified argument passing
- **Resource Extensions**: Easy dimension conversions (dp, sp)
- **Screen Dimension Extensions**: Access device screen properties

## Installation

### Step 1: Add JitPack repository

Add JitPack to your root build.gradle file:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the dependency

```gradle
dependencies {
    implementation 'com.github.yourusername:AndroidExtensions:1.0.0'
}
```

## Usage

### Context Extensions

```kotlin
// Show toast messages
context.showShortToast("Hello World")
context.showLongToast("This is a longer message")

// Get resources with compatibility
val color = context.getColorCompat(R.color.colorPrimary)
val drawable = context.getDrawableCompat(R.drawable.ic_launcher)

// Convert between DP and PX
val pixels = context.dpToPx(16f)
val dp = context.pxToDp(48)

// Check internet connection
if (context.isNetworkAvailable()) {
    // Perform network operation
}

// Start activity with type safety
context.startActivity<MainActivity> {
    putExtra("key", "value")
}
```

### View Extensions

```kotlin
// Manage visibility
view.show()
view.hide()
view.gone()
view.toggleVisibility()

// Update margins
view.updateMargins(left = 16.dp, top = 8.dp)

// Keyboard management
editText.showKeyboard()
editText.hideKeyboard()

// Debounced click listener
button.setOnSingleClickListener {
    // Handles rapid clicks, executing only once
}
```

### More Examples

```kotlin
// EditText
val text = editText.textString
if (!editText.isEmpty) {
    // Process text
}

// String validation
if (email.isValidEmail()) {
    // Valid email
}

// Date formatting
val date = "2023-03-15".toDate()
val formatted = Date().format("dd/MM/yyyy")

// Fragment arguments
val fragment = MyFragment().withArgs {
    putString("key", "value")
    putInt("id", 123)
}

// Resource dimensions
val padding = 16.dp
val textSize = 14.sp

// Screen dimensions
val width = context.screenWidth
val height = context.screenHeight
val density = context.screenDensity
```

## License

```
MIT License

Copyright (c) 2025 Ali Aktaş

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Contributing

Contributions are welcome! Feel free to submit a Pull Request.

