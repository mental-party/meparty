package com.teammental.mebuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Mental sistem için Generic builder
 * Created by coskun on 12.06.2017.
 */
public class GenericBuilder<T> {

  private final Supplier<T> instantiator;

  private List<Consumer<T>> instanceModifiers = new ArrayList<>();

  /**
   * Constructor metodu.
   * @param instantiator yeni bir instance vereceğiniz parametre.
   */
  public GenericBuilder(Supplier<T> instantiator) {
    this.instantiator = instantiator;
  }

  /**
   * GenericBuilder classından statik olarak bir GenericBuilder instanceı oluşturmak için.
   * @param instantiator yeni bir instance vereceğiniz parametre
   * @param <T> build olmasını istediğiniz class
   * @return GenericBuilder classından bir instance
   */
  public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
    return new GenericBuilder<T>(instantiator);
  }

  /**
   * Bu metod ile setterları kullanarak değer atayabilirsiniz.
   * @param consumer Build etmek istediğiniz class propertysinin setter methodu
   * @param value Build etmek istediğiniz class propertysine atılacak değer
   * @param <U> Build etmek istediğiniz class propertysinin tipi
   * @return GenericBuilder classından bir instance
   */
  public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
    Consumer<T> c = instance -> consumer.accept(instance, value);
    instanceModifiers.add(c);
    return this;
  }

  /**
   * Build metodu.
   * @return Build edilmesi istenen class
   */
  public T build() {
    T value = instantiator.get();
    instanceModifiers.forEach(modifier -> modifier.accept(value));
    instanceModifiers.clear();
    return value;
  }
}
