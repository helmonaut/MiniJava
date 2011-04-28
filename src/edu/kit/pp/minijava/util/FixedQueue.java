package edu.kit.pp.minijava.util;

import java.util.ArrayList;
import java.util.List;

public class FixedQueue<T> {

	private int _size;

	private List<T> _items;

	public FixedQueue(int size) {
		_size = size;
		_items = new ArrayList<T>(size);
	}

	public void add(T item) {
		if (_items.size() == _size) {
			_items.add(0, item);
			_items.remove(_size);
		} else {
			_items.add(0, item);
		}
	}

	public T get(int index) {
		return _items.get(index);
	}
}
