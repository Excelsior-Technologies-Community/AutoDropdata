package com.ext.auto_dropdown

object DataSets {

    val gstList = listOf(
        "0%", "0.25%", "3%", "5%", "12%", "18%", "28%"
    )

    val countries = listOf(
        "Afghanistan", "Albania", "Algeria", "Argentina", "Australia",
        "Austria", "Bangladesh", "Belgium", "Brazil", "Canada",
        "China", "Colombia", "Denmark", "Egypt", "Finland",
        "France", "Germany", "Greece", "India", "Indonesia",
        "Iran", "Iraq", "Ireland", "Israel", "Italy",
        "Japan", "Kenya", "Malaysia", "Mexico", "Netherlands",
        "New Zealand", "Nigeria", "Norway", "Pakistan", "Philippines",
        "Poland", "Portugal", "Russia", "Saudi Arabia", "Singapore",
        "South Africa", "South Korea", "Spain", "Sri Lanka", "Sweden",
        "Switzerland", "Thailand", "Turkey", "UAE", "UK",
        "Ukraine", "USA", "Vietnam"
    )

    val units = listOf(
        // Weight
        "Kilogram (Kg)", "Gram (g)", "Milligram (mg)", "Ton (t)", "Quintal (q)",
        "Pound (lb)", "Ounce (oz)",

        // Volume
        "Liter (L)", "Milliliter (mL)", "Gallon (gal)", "Cubic Meter (m³)",

        // Length
        "Meter (m)", "Centimeter (cm)", "Millimeter (mm)", "Kilometer (km)",
        "Inch (in)", "Foot (ft)", "Yard (yd)",

        // Count
        "Piece (pc)", "Dozen (dz)", "Gross", "Pair", "Set",
        "Pack", "Box", "Carton", "Bundle",

        // Area
        "Square Meter (m²)", "Square Foot (ft²)", "Acre", "Hectare (ha)"
    )

    val currencies = listOf(
        "INR - Indian Rupee (₹)",
        "USD - US Dollar ($)",
        "EUR - Euro (€)",
        "GBP - British Pound (£)",
        "JPY - Japanese Yen (¥)",
        "AUD - Australian Dollar (A$)",
        "CAD - Canadian Dollar (C$)",
        "CHF - Swiss Franc (Fr)",
        "CNY - Chinese Yuan (¥)",
        "AED - UAE Dirham (د.إ)",
        "SAR - Saudi Riyal (﷼)"
    )

    val paymentModes = listOf(
        "Cash",
        "UPI",
        "Credit Card",
        "Debit Card",
        "Net Banking",
        "Paytm",
        "PhonePe",
        "Google Pay",
        "Cheque",
        "Bank Transfer",
        "Cash on Delivery (COD)"
    )

    val deliveryTypes = listOf(
        "Home Delivery",
        "Pickup",
        "Express Delivery",
        "Same Day Delivery",
        "Standard Delivery",
        "Store Pickup"
    )

    val taxSlabs = listOf(
        "0%",
        "5%",
        "12%",
        "18%",
        "28%"
    )
}