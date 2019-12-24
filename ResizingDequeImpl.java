import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingDequeImpl<E> implements ResizingDequeInterface<E> {

    Object[] arr;
    Integer head;
    Integer tail;

    public ResizingDequeImpl() {
        arr = new Object[2];
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        } else if (head == tail && arr[head] == null) {
            return 0;
        } else if (head > tail) {
            return (arr.length - head) + (tail + 1);

        } else {
            return (tail - head + 1);
        }
    }

    @Override
    public E[] getArray() {
        E[] ans = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = (E) arr[i];
        }
        return ans;
    }

    @Override
    public void addFirst(E e) {
        if (this.size() == 0) {
            arr[arr.length - 1] = e;
            head = arr.length - 1;
            tail = arr.length - 1;
        } else if (this.size() < arr.length) {
            if (head < tail) {
                arr[arr.length - 1] = e;
                head = arr.length - 1;
            } else {
                arr[head - 1] = e;
                head--;
            }
        } else {
            resizeArray(2);
            arr[arr.length - 1] = e;
            head = arr.length - 1;
        }
    }

    private void resizeArray(double x) {
        if (arr.length == 2 && x < 1) {
            return;
        }
        Object[] temp = arr;
        arr = new Object[(int) (temp.length * x)];
        int size = 0;
        if (head <= tail) {
            for (int i = 0; i <= tail - head; i++) {
                arr[i] = temp[i + head];
                size++;
            }
        } else {
            for (int i = 0; i < temp.length - head; i++) {
                arr[i] = temp[i + head];
                size++;
            }
            for (int i = temp.length - head - tail - 1; i < temp.length - head; i++) {
                arr[i + head] = temp[i];
                size++;
            }
        }
        head = 0;
        tail = size - 1;
    }

    @Override
    public void addLast(E e) {
        if (this.size() == 0) {
            arr[0] = e;
            head = 0;
            tail = 0;
        } else if (size() == 1 && head == arr.length - 1) {
            arr[head - 1] = e;
            tail--;
        } else if (this.size() < arr.length) {
            arr[tail + 1] = e;
            tail++;
        } else {
            resizeArray(2);
            arr[tail + 1] = e;
            tail++;
        }
    }

    @Override
    public E pollFirst() {
        if (this.size() == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        E ans = (E) arr[head];
        arr[head] = null;
        if (head == arr.length - 1) {
            head = 0;
        } else if (this.size() == 0) {
            head = 0;
            tail = 0;
        } else {
            head++;
        }
        if (this.size() <= arr.length / 4) {
            resizeArray(.5);
        }
        return ans;
    }

    @Override
    public E pollLast() {
        if (this.size() == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        E ans = (E) arr[tail];
        arr[tail] = null;
        tail--;
        if (this.size() - 1 <= arr.length / 4) {
            resizeArray(.5);
        }
        return ans;
    }

    @Override
    public E peekFirst() {
        if (this.size() == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        return (E) arr[head];
    }

    @Override
    public E peekLast() {
        if (this.size() == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        return (E) arr[tail];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int i = head;

            @Override
            public boolean hasNext() {
                return i != tail;
            }

            @Override
            public E next() {
                E ans = (E) arr[i];
                if (hasNext() && i == arr.length - 1) {
                    i = 0;
                } else if (hasNext()) {
                    i++;
                }
                return ans;
            }
        };
    }
}
