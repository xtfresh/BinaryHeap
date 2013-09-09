import java.util.Comparator;

/**
 * This is an implementation of a heap that is backed by an array.
 * 
 * This implementation will accept a comparator object that can be used to
 * define an ordering of the items contained in this heap, other than the
 * objects' default compareTo method (if they are comparable). This is useful if
 * you wanted to sort strings by their length rather than their lexicographic
 * ordering. That's just one example.
 * 
 * Null should be treated as positive infinity if no comparator is provided. If
 * a comparator is provided, you should let it handle nulls, which means it
 * could possibly throw a NullPointerException, which in this case would be
 * fine.
 * 
 * If a comparator is provided that should always be what you use to compare
 * objects. If no comparator is provided you may assume the objects are
 * Comparable and cast them to type Comparable<T> for comparisons. If they
 * happen to not be Comparable you do not need to handle anything, and you can
 * just let your cast throw a ClassCastException.
 * 
 * This is a minimum heap, so the smallest item should always be at the root.
 * 
 * @param <T>
 *            The type of objects in this heap
 */
public class BinaryHeap<T> implements Heap<T> {

	/**
	 * The comparator that should be used to order the elements in this heap
	 */
	private Comparator<T> comp;

	/**
	 * The backing array of this heap
	 */
	private T[] data;

    private static double MAX_LOAD_FACTOR = .64;

	/**
	 * The number of elements that have been added to this heap, this is NOT the
	 * same as data.length
	 */
	private int size = 0;

	/**
	 * Default constructor, this should initialize data to a default size (11 is
	 * normally a good choice)
	 * 
	 * This assumes that the generic objects are Comparable, you will need to
	 * cast them when comparing since there are no bounds on the generic
	 * parameter
	 */
	public BinaryHeap() {
		data = (T[]) new Object[11];
        this.comp = heapComp;

	}

	/**
	 * Constructor that accepts a comparator to use with this heap. Also
	 * initializes data to a default size.
	 * 
	 * When a comparator is provided it should be preferred over the objects'
	 * compareTo method
	 * 
	 * If the comparator given is null you should attempt to cast the objects to
	 * Comparable as if a comparator were not given
	 * 
	 * @param comp
	 */
	public BinaryHeap(Comparator<T> comp) {
        data = (T[]) new Object[11];
        this.comp = comp;

	}
    private   Comparator<T> heapComp = new Comparator<T>(){
        @Override
        public int compare(T o1, T o2){
            if(o1 == null){
                return 1;
            }
            if(o2 == null){
                return -1;
            }
            return ((Comparable<T>) o1).compareTo(o2);
        }
    };

	@Override
	public void add(T item) {
        checkLoadFactor();
        if(size == 0){
            data[1] = item;
            size++;
        }else{
            data[size+1] = item;
            int temp = size+1;
            if(item!=null){
                while(temp > 1 && comp.compare(data[temp],data[temp/2]) < 0){
                    swap(temp,temp/2);
                    temp = temp/2;
                    
                }
                
            }
            size++;
        }

	}
    private void checkLoadFactor(){
        if ((double)size/data.length > MAX_LOAD_FACTOR) {
            int new_size = 2 * data.length + 1;
            T[] tempData = (T[]) new Object[new_size];
            for(int i = 0; i<data.length;i++){
                tempData[i] = data[i];
            }
            data = tempData;
        }
    }
    private void swap(int item1,int item2){
        T temp = data[item1];
        data[item1] = data[item2];
        data[item2] = temp;
    }
    public String toString(){
        String str = "[";
        for (int i = 1; i < size+1; i++){
        	str += " " + data[i];
        }
        return str += " ]";
    }

	@Override
	public boolean isEmpty() {
		if(size == 0)
			return true;
		return false;
	}

	@Override
	public T peek() {
		return data[1];
	}

	@Override
	public T remove() {
		swap(1,size);
        T dat = data[size];
        data[size]=null;
        size--;
        int temp = 1;
        int temp2 = 2*temp;
        int temp3 = temp2+1;
        while(2*temp <= size){
            if(comp.compare(data[temp2],data[temp3]) < 0){
                swap(temp,temp2);
                temp = temp2;
            }else{
                swap(temp,temp3);
                temp = temp3;

            }
            
            	temp2 = 2*temp;
            	temp3 = temp2+1;

        }

		return dat;
	}

	@Override
	public int size() {
		return size;
	}
}
