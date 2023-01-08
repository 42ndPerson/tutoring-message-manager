package com.fariseducation.Data.ObservedData;

import java.util.ArrayList;
import java.util.Iterator;

public class ObservedLockedList<ContentType> extends ObservedDatum implements Iterable<ContentType> {
    protected ArrayList<ContentType> contents;

    public ObservedLockedList() {
        this.contents = new ArrayList<>();
    }
    public ObservedLockedList(ArrayList<ContentType> contents) {
        this.contents = contents;
    }

    public boolean contains(ContentType element) {
        return this.contents.contains(element);
    }
    public ContentType get(int index) {
        return this.contents.get(index);
    }
    public int indexOf(ContentType element) {
        return this.contents.indexOf(element);
    }
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }
    public Iterator<ContentType> iterator() {
        return this.contents.iterator();
    }
    public int size() {
        return this.contents.size();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedLockedList) return this.contents.equals(((ObservedLockedList)o).contents);
        return false;
    }
}
