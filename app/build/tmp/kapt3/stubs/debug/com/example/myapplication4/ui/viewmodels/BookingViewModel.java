package com.example.myapplication4.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\tJ\u0006\u0010\"\u001a\u00020 J6\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010\'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020*J\u000e\u0010\u001e\u001a\u00020 2\u0006\u0010$\u001a\u00020\tJ\u000e\u0010+\u001a\u00020 2\u0006\u0010\'\u001a\u00020\tJ\u000e\u0010,\u001a\u00020 2\u0006\u0010&\u001a\u00020\u000bJ\u000e\u0010-\u001a\u00020 2\u0006\u0010(\u001a\u00020\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000e0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0019\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012\u00a8\u0006."}, d2 = {"Lcom/example/myapplication4/ui/viewmodels/BookingViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_bookingState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/myapplication4/ui/viewmodels/BookingState;", "_currentBooking", "Lcom/example/myapplication4/data/Booking;", "_selectedDate", "", "_selectedService", "Lcom/example/myapplication4/data/Service;", "_selectedTime", "_userBookings", "", "bookingState", "Lkotlinx/coroutines/flow/StateFlow;", "getBookingState", "()Lkotlinx/coroutines/flow/StateFlow;", "currentBooking", "getCurrentBooking", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "selectedDate", "getSelectedDate", "selectedService", "getSelectedService", "selectedTime", "getSelectedTime", "userBookings", "getUserBookings", "cancelBooking", "", "bookingId", "clearCurrentBooking", "createBooking", "userId", "petName", "service", "date", "time", "price", "", "setSelectedDate", "setSelectedService", "setSelectedTime", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.myapplication4.data.Booking> _currentBooking = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.data.Booking> currentBooking = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.myapplication4.data.Booking>> _userBookings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.myapplication4.data.Booking>> userBookings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.myapplication4.data.Service> _selectedService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.data.Service> selectedService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedTime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedTime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.myapplication4.ui.viewmodels.BookingState> _bookingState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.ui.viewmodels.BookingState> bookingState = null;
    
    @javax.inject.Inject()
    public BookingViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.data.Booking> getCurrentBooking() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.myapplication4.data.Booking>> getUserBookings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.data.Service> getSelectedService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.myapplication4.ui.viewmodels.BookingState> getBookingState() {
        return null;
    }
    
    public final void setSelectedService(@org.jetbrains.annotations.NotNull()
    com.example.myapplication4.data.Service service) {
    }
    
    public final void setSelectedDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    public final void setSelectedTime(@org.jetbrains.annotations.NotNull()
    java.lang.String time) {
    }
    
    public final void createBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String petName, @org.jetbrains.annotations.NotNull()
    com.example.myapplication4.data.Service service, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String time, double price) {
    }
    
    public final void getUserBookings(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void cancelBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId) {
    }
    
    public final void clearCurrentBooking() {
    }
}