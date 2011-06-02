package org.mars.ddp.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractStreamCollection<P extends Packet> extends AbstractDataStream implements Iterable<P> {
  private List<P> delegate = new ArrayList<P>();

  public int size() {
    return delegate.size();
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public Iterator<P> iterator() {
    return delegate.iterator();
  }

  public boolean add(P e) {
    return delegate.add(e);
  }

  public void clear() {
    delegate.clear();
  }

  public P get(int index) {
    return delegate.get(index);
  }
}
