# ***AutoDropdata***

---

A simple **Android library** to provide **custom dropdowns** for common lists like **GST %, countries, units, currencies, payment modes, delivery types, tax slabs, etc.**  
This library allows users to select from predefined datasets without manually entering values and provides a **modern, sleek UI with arrow animation** and custom styling.

---

## ***✨ Features***

---

- **Predefined Lists**: Supports multiple dropdown types:
  - GST %
  - Countries
  - Units
  - Currencies
  - Payment Modes
  - Delivery Types
  - Tax Slabs

- **Custom UI**:
  - Rounded corners with configurable radius
  - Dropdown background color customization
  - Text color and size customization
  - Animated arrow icon for dropdown open/close
  - Ripple touch effect on selection

- **Easy to Use**:
  - Just add `AutoDropdown` in XML and set the `type` attribute
  - No need to manually populate lists



---
## ***✨ Preview***

<p align="center">
  <img src="https://github.com/user-attachments/assets/b0de5e1d-deaa-44b8-9c72-c7da8278dada"
       alt="Demo GIF"
       width="200">

</p>

## ***📦 Installation***



Step 1: Add JitPack repository to your root build.gradle:

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2: Add dependency to your app module's build.gradle:

```
	        implementation 'com.github.Excelsior-Technologies-Community:AutoDropdata:1.0.0'


```

## ***Custom attribute file***

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="AutoDropdown">
        <attr name="type" format="string" />
        <attr name="dropdownBackground" format="color" />
        <attr name="dropdownTextColor" format="color" />
        <attr name="dropdownRadius" format="dimension" />
        <attr name="hintText" format="string" />
        <attr name="dropdownTextSize" format="dimension" />
        <attr name="arrowColor" format="color" /> <!-- Color of the arrow -->
        <attr name="arrowSize" format="dimension" /> <!-- Size of the arrow -->
        <attr name="fontFamily" format="string" /> <!-- Font family for dropdown text -->
    </declare-styleable>
</resources>

```


## ***Add AutoDropdown to your layout XML:***

```
<com.ext.auto_dropdown.AutoDropdown
    android:id="@+id/spGST"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:layout_marginTop="12dp"
    android:paddingStart="16dp"
    android:paddingEnd="48dp"
    android:paddingVertical="16dp"
    android:background="@drawable/bg_dropdown"
    android:textColor="#212121"
    android:textSize="16sp"
    android:textColorHint="#757575"
    android:fontFamily="sans-serif-medium"
    app:type="gst"
    app:hintText="Select GST %"
    app:dropdownBackground="#FFFFFF"
    app:dropdownTextColor="#212121"
    app:dropdownRadius="12dp"
    app:dropdownTextSize="16sp"/>

```

## ***✨ autodropdowns types***
- app:type="taxslabs"
- app:type="deliverytypes"
- app:type="paymentmodes"
- app:type="currencies"
- app:type="units"
- app:type="countries"
- app:type="gst"







## ***🛠 Usage Example***

```
val gstDropdown: AutoDropdown = findViewById(R.id.spGST)

// Listen for selection
gstDropdown.setOnItemClickListener { parent, view, position, id ->
    val selectedGst = parent.getItemAtPosition(position).toString()
    Toast.makeText(this, "Selected GST: $selectedGst", Toast.LENGTH_SHORT).show()
}
```

##   ***📄 License***

MIT License


```
Copyright (c) 2025 Excelsior Technologies 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
```
