# Preserve the line number information for debugging stack traces
-keepattributes SourceFile,LineNumberTable

# Hide the original source file name if line number information is kept
-renamesourcefileattribute SourceFile

# Keep all classes in the application package
-keep class io.github.mexikoedi.smdf.** { *; }

# Keep all Kotlin data classes
-keepclassmembers class * extends kotlin.jvm.internal.Lambda {
    *;
}

# Keep all Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep all View Binding classes
-keep class **.databinding.* { *; }

# Keep all classes annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }

# Keep all methods annotated with @Keep
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep all classes used in serialization/deserialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}