package com.fariseducation.Data.ObservedData;

public class ObservedUnlockedList<ContentType> extends ObservedLockedList<ContentType> {
    public void add(ContentType element) {
        this.contents.add(element);
        update();
    }
    public void add(int index, ContentType element) {
        this.contents.add(index, element);
        update();
    }
    public void remove(ContentType element) {
        this.contents.remove(element);
        update();
    }
    public void remove(int index) {
        this.contents.remove(index);
        update();
    }
    public void clear() {
        this.contents.clear();
        update();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if(o instanceof ObservedUnlockedList) return this.contents.equals(((ObservedUnlockedList)o).contents);
        return false;
    }
}
