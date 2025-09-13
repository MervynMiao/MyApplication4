package com.example.myapplication4.ui.viewmodels;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class BookingViewModel_Factory implements Factory<BookingViewModel> {
  @Override
  public BookingViewModel get() {
    return newInstance();
  }

  public static BookingViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BookingViewModel newInstance() {
    return new BookingViewModel();
  }

  private static final class InstanceHolder {
    private static final BookingViewModel_Factory INSTANCE = new BookingViewModel_Factory();
  }
}
