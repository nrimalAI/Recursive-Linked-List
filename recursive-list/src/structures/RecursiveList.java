package structures;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {

  class LLNode<T> {
    public T data;
    public LLNode<T> next;

    public LLNode(T data) {
      this.data = data;
    }

    public LLNode(T data, LLNode<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  private class ListIterator<T> implements Iterator<T> {

    private LLNode<T> x;

    public ListIterator(LLNode<T> head) {
      this.x = head;
    }

    @Override
    public boolean hasNext() {
      return x != null;
    }

    @Override
    public T next() {
      if (x == null)
        throw new IllegalArgumentException();
      if (!hasNext()) {
        throw new IllegalArgumentException();
      } else {
        T holder = x.data;
        x = x.next;
        return holder;
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private LLNode<T> main;
  private int numEl;

  @Override
  public Iterator<T> iterator() {
    return new ListIterator<T>(main);
  }

  @Override
  public int size() {
    return numEl;
  }

  @Override
  public ListInterface<T> insertFirst(T elem) {
    if (elem == null)
      throw new NullPointerException();
    else {
      return insertAt(0, elem);
    }
  }

  @Override
  public ListInterface<T> insertLast(T elem) {
    if (elem == null)
      throw new NullPointerException();
    if (isEmpty())
      insertFirst(elem);
    else
      insertAt(size(), elem);
    return this;
  }

  @Override
  public ListInterface<T> insertAt(int index, T elem) {
    if (index < 0 || index > size())
      throw new IndexOutOfBoundsException();
    if (elem == null)
      throw new NullPointerException();
    else {
      if (index == 0) {
        LLNode<T> zeroNode = new LLNode<T>(elem);
        zeroNode.next = main;
        main = zeroNode;
      } else {
        LLNode<T> previous = findNode(index - 1, 0, main);
        LLNode<T> node = new LLNode<T>(elem, previous.next);
        previous.next = node;
      }
    }
    numEl++;
    return this;
  }

  @Override
  public T removeFirst() {
    if (isEmpty())
      throw new IllegalStateException();
    return removeAt(0);
  }

  @Override
  public T removeLast() {
    if (isEmpty())
      throw new IllegalStateException();
    return removeAt(size() - 1);
  }

  @Override
  public T removeAt(int i) {
    if (i < 0 || i >= size())
      throw new IndexOutOfBoundsException();
    T temp;
    if (i == 0) {
      temp = main.data;
      main = main.next;
      numEl--;
    } else {
      LLNode<T> prev = findNode(i - 1, 0, main);
      temp = prev.next.data;
      prev.next = prev.next.next;
      numEl--;
    }
    return temp;
  }

  @Override
  public T getFirst() {
    if (isEmpty())
      throw new IllegalStateException();
    return get(0);
  }

  @Override
  public T getLast() {
    if (isEmpty())
      throw new IllegalStateException();
    return get(size() - 1);
  }

  @Override
  public T get(int i) {
    if (i < 0 || i >= size())
      throw new IndexOutOfBoundsException();
    return findNode(i, 0, main).data;
  }

  private LLNode<T> findNode(int index, int count, LLNode<T> curr) {
    if (index == count)
      return curr;
    return findNode(index, count + 1, curr.next);
  }

  @Override
  public boolean remove(T elem) {
    if (isEmpty())
      return false;
    if (elem == null)
      throw new NullPointerException();
    if (indexOf(elem) == -1)
      return false;
    else {
      removeAt(indexOf(elem));
      return true;
    }
  }

  @Override
  public int indexOf(T elem) {
    if (elem == null)
      throw new NullPointerException();
    else {
      return indexOfHelper(elem, 0, main);
    }
  }

  private int indexOfHelper(T elem, int index, LLNode<T> curr) {
    if (curr == null)
      return -1;
    if (curr.data.equals(elem))
      return index;
    return indexOfHelper(elem, index + 1, curr.next);
  }

  @Override
  public boolean isEmpty() {
    return main == null;
  }
}
