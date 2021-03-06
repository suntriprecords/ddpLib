package com.suntriprecords.ddp.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractStreamCollection<L> extends AbstractDataStream implements Iterable<L> {

  public AbstractStreamCollection(URL streamUrl) {
    super(streamUrl);
  }

  private List<L> delegate = new ArrayList<L>();

  public int size() {
    return delegate.size();
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public Iterator<L> iterator() {
    return delegate.iterator();
  }

  public boolean add(L e) {
    return delegate.add(e);
  }

  public void clear() {
    delegate.clear();
  }

  public L get(int index) {
    return delegate.get(index);
  }
}
