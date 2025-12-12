package com.ext.auto_dropdown

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

@RequiresApi(Build.VERSION_CODES.Q)
class AutoDropdown(context: Context, attrs: AttributeSet?) :
    AppCompatAutoCompleteTextView(context, attrs) {

    private var listItems: List<String> = emptyList()
    private var arrowDrawable: ArrowDrawable
    private var dropdownBgColor: Int
    private var dropdownTextColorValue: Int
    private var dropdownTextSizeValue: Float
    private var arrowColorValue: Int
    private var arrowSizeValue: Float
    private var fontFamilyValue: String?

    init {
        val styledAttr = context.obtainStyledAttributes(attrs, R.styleable.AutoDropdown)

        val type = styledAttr.getString(R.styleable.AutoDropdown_type) ?: ""
        dropdownBgColor = styledAttr.getColor(R.styleable.AutoDropdown_dropdownBackground, 0xFFFFFFFF.toInt())
        dropdownTextColorValue = styledAttr.getColor(R.styleable.AutoDropdown_dropdownTextColor, 0xFF212121.toInt())
        val radius = styledAttr.getDimension(R.styleable.AutoDropdown_dropdownRadius, 12f)
        val hintText = styledAttr.getString(R.styleable.AutoDropdown_hintText) ?: "Select option"
        dropdownTextSizeValue = styledAttr.getDimension(R.styleable.AutoDropdown_dropdownTextSize, 16f)
        arrowColorValue = styledAttr.getColor(R.styleable.AutoDropdown_arrowColor, 0xFF757575.toInt())
        arrowSizeValue = styledAttr.getDimension(R.styleable.AutoDropdown_arrowSize, 24f) // default 24dp
        fontFamilyValue = styledAttr.getString(R.styleable.AutoDropdown_fontFamily)

        styledAttr.recycle()

        listItems = getListBasedOnType(type)

        // Custom adapter with styling
        val arrayAdapter = CustomDropdownAdapter(
            context,
            listItems,
            dropdownTextColorValue,
            dropdownTextSizeValue,
            fontFamilyValue
        )
        setAdapter(arrayAdapter)
        threshold = 1

        setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, dropdownTextSizeValue)
        hint = hintText
        setTextColor(dropdownTextColorValue)
        setHintTextColor(0xFF757575.toInt())

        // Set background
        background = GradientDrawable().apply {
            setColor(dropdownBgColor)
            cornerRadius = radius
            setStroke(2, 0xFFE0E0E0.toInt())
        }

        // Create arrow drawable with custom color & size
        arrowDrawable = ArrowDrawable(context, arrowColorValue, arrowSizeValue)
        setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null)
        compoundDrawablePadding = (8 * resources.displayMetrics.density).toInt()

        // Style the dropdown popup
        setDropDownBackgroundDrawable(createDropdownBackground())
        dropDownVerticalOffset = (4 * resources.displayMetrics.density).toInt()
        dropDownHorizontalOffset = 0

        // Disable overscroll in dropdown list
        setOnTouchListener { _, _ ->
            try {
                val popupField = AppCompatAutoCompleteTextView::class.java.getDeclaredField("mPopup")
                popupField.isAccessible = true
                val popup = popupField.get(this)
                val listViewField = popup.javaClass.getDeclaredField("mDropDownList")
                listViewField.isAccessible = true
                val listView = listViewField.get(popup) as? android.widget.ListView
                listView?.overScrollMode = View.OVER_SCROLL_NEVER
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }

        // Toggle dropdown and animate arrow
        setOnClickListener {
            post {
                try {
                    val popupField = AppCompatAutoCompleteTextView::class.java.getDeclaredField("mPopup")
                    popupField.isAccessible = true
                    val popup = popupField.get(this)
                    val listViewField = popup.javaClass.getDeclaredField("mDropDownList")
                    listViewField.isAccessible = true
                    val listView = listViewField.get(popup) as? android.widget.ListView
                    listView?.overScrollMode = View.OVER_SCROLL_NEVER
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (isPopupShowing) {
                dismissDropDown()
                arrowDrawable.animateRotation(false)
            } else {
                showDropDown()
                arrowDrawable.animateRotation(true)
            }
        }

        setOnDismissListener {
            arrowDrawable.animateRotation(false)
        }

        setOnItemClickListener { _, _, _, _ ->
            arrowDrawable.animateRotation(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createDropdownBackground(): GradientDrawable {
        return GradientDrawable().apply {
            setColor(0xFFFFFFFF.toInt())
            cornerRadius = 12 * resources.displayMetrics.density
            setStroke((1 * resources.displayMetrics.density).toInt(), 0xFFE0E0E0.toInt())
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: android.graphics.Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) arrowDrawable.animateRotation(false)
    }

    private fun getListBasedOnType(type: String): List<String> {
        return when (type.lowercase()) {
            "gst" -> DataSets.gstList
            "countries" -> DataSets.countries
            "units" -> DataSets.units
            "currencies" -> DataSets.currencies
            "paymentmodes" -> DataSets.paymentModes
            "deliverytypes" -> DataSets.deliveryTypes
            "taxslabs" -> DataSets.taxSlabs
            else -> emptyList()
        }
    }

    // Arrow drawable with color & size
    private class ArrowDrawable(context: Context, color: Int, private val arrowSize: Float) : Drawable() {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
            style = Paint.Style.FILL
        }

        private val path = Path()
        private var rotation = 0f

        override fun draw(canvas: Canvas) {
            val bounds = bounds
            val centerX = bounds.exactCenterX()
            val centerY = bounds.exactCenterY()

            canvas.save()
            canvas.rotate(rotation, centerX, centerY)

            path.reset()
            val sizePx = arrowSize
            path.moveTo(centerX - sizePx / 2, centerY - sizePx / 3)
            path.lineTo(centerX, centerY + sizePx / 3)
            path.lineTo(centerX + sizePx / 2, centerY - sizePx / 3)
            path.close()

            canvas.drawPath(path, paint)
            canvas.restore()
        }

        fun animateRotation(isOpen: Boolean) {
            val targetRotation = if (isOpen) 180f else 0f
            val animator = ValueAnimator.ofFloat(rotation, targetRotation)
            animator.duration = 250
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addUpdateListener { animation ->
                rotation = animation.animatedValue as Float
                invalidateSelf()
            }
            animator.start()
        }

        override fun setAlpha(alpha: Int) { paint.alpha = alpha }
        override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) { paint.colorFilter = colorFilter }
        override fun getOpacity(): Int = android.graphics.PixelFormat.TRANSLUCENT
        override fun getIntrinsicWidth(): Int = arrowSize.toInt()
        override fun getIntrinsicHeight(): Int = arrowSize.toInt()
    }

    // Adapter with fontFamily support
    private class CustomDropdownAdapter(
        context: Context,
        items: List<String>,
        private val textColor: Int,
        private val textSize: Float,
        private val fontFamily: String?
    ) : ArrayAdapter<String>(context, 0, items) {

        private val inflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val textView: TextView

            if (convertView == null) {
                view = createCustomView(parent)
                textView = view.findViewById(android.R.id.text1)
            } else {
                view = convertView
                textView = view.findViewById(android.R.id.text1)
            }

            textView.text = getItem(position)
            textView.setTextColor(textColor)
            textView.textSize = pxToSp(textSize)

            fontFamily?.let {
                try {
                    textView.typeface = Typeface.create(it, Typeface.NORMAL)
                } catch (e: Exception) { /* ignore invalid font */ }
            }

            val divider = view.findViewById<View?>(android.R.id.background)
            divider?.visibility = if (position == count - 1) View.GONE else View.VISIBLE

            view.setOnTouchListener { v, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> v.setBackgroundColor(0xFFF5F5F5.toInt())
                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> v.setBackgroundColor(0xFFFFFFFF.toInt())
                }
                false
            }

            return view
        }

        private fun createCustomView(parent: ViewGroup): View {
            val container = android.widget.LinearLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                orientation = android.widget.LinearLayout.VERTICAL
                setBackgroundColor(0xFFFFFFFF.toInt())
            }

            val textView = TextView(context).apply {
                id = android.R.id.text1
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setPadding(
                    (20 * context.resources.displayMetrics.density).toInt(),
                    (16 * context.resources.displayMetrics.density).toInt(),
                    (20 * context.resources.displayMetrics.density).toInt(),
                    (16 * context.resources.displayMetrics.density).toInt()
                )
                minHeight = (48 * context.resources.displayMetrics.density).toInt()
                gravity = android.view.Gravity.CENTER_VERTICAL
                maxLines = 1
                ellipsize = android.text.TextUtils.TruncateAt.END
            }

            val divider = View(context).apply {
                id = android.R.id.background
                val params = android.widget.LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (1 * context.resources.displayMetrics.density).toInt()
                )
                params.marginStart = (20 * context.resources.displayMetrics.density).toInt()
                params.marginEnd = (20 * context.resources.displayMetrics.density).toInt()
                layoutParams = params
                setBackgroundColor(0xFFF0F0F0.toInt())
            }

            container.addView(textView)
            container.addView(divider)

            val outValue = android.util.TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            container.foreground = context.getDrawable(outValue.resourceId)

            return container
        }

        private fun pxToSp(px: Float): Float = px / context.resources.displayMetrics.scaledDensity
    }
}
