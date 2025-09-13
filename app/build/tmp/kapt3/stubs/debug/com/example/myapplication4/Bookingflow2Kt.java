package com.example.myapplication4;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a&\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a&\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a&\u0010\u0011\u001a\u00020\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0015H\u0002\u00a8\u0006\u0016"}, d2 = {"BookingFlow2Screen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/example/myapplication4/ui/viewmodels/BookingViewModel;", "DatePickerButton", "dayInfo", "Lcom/example/myapplication4/DayInfo;", "isSelected", "", "onDateSelected", "Lkotlin/Function0;", "TimeSlotButton", "time", "", "onTimeSelected", "TimeSlotGrid", "selectedTime", "Lkotlin/Function1;", "generateNext5Days", "", "app_debug"})
public final class Bookingflow2Kt {
    
    @androidx.compose.runtime.Composable()
    public static final void BookingFlow2Screen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.example.myapplication4.ui.viewmodels.BookingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DatePickerButton(@org.jetbrains.annotations.NotNull()
    com.example.myapplication4.DayInfo dayInfo, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDateSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TimeSlotGrid(@org.jetbrains.annotations.Nullable()
    java.lang.String selectedTime, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTimeSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TimeSlotButton(@org.jetbrains.annotations.NotNull()
    java.lang.String time, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTimeSelected) {
    }
    
    private static final java.util.List<com.example.myapplication4.DayInfo> generateNext5Days() {
        return null;
    }
}