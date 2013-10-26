package org.mars.ddp.builder;

public abstract class AbstractCell<T> implements Cell {

  private T value;
  
  @Override
  public T getValue() {
    return value;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setValue(Object value) {
    this.value = (T)value;
  }

  @Override
  public String toString() {
    T value = getValue();
    return value != null ? value.toString() : null; 
  }
}
