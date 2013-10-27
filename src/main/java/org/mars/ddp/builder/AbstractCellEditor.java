package org.mars.ddp.builder;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

public abstract class AbstractCellEditor extends DefaultCellEditor {

  private static final long serialVersionUID = 1L;
  private Object value;
  
  public AbstractCellEditor() {
    super(new JTextField());
  }

  @Override
  public Object getCellEditorValue() {
    return value;
  }

  @Override
  public boolean stopCellEditing() {
    String s = (String)super.getCellEditorValue();
    if (s.length() == 0) {
      value = null;
      return super.stopCellEditing();
    }
    else {
      if(validate(s)) {
        value = convert(s);
        return super.stopCellEditing();
      }
      else {
        return false;
      }
    }
  }
  
  public abstract boolean validate(String s);
  public abstract Object convert(String s);
}