package org.mars.ddp.ui;

public class IntegerCellEditor extends AbstractCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    try {
      Integer.parseInt(s);
      return true;
    }
    catch(NumberFormatException e) {
      return false;
    }
  }

  @Override
  public Object convert(String s) {
    return new Integer(s);
  }
}
