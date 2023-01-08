package com.fariseducation.Data.ObservedData;

public class ObservedUnlockedList<ContentType> extends ObservedLockedList<ContentType> {
    public void add(ContentType element) {
        this.contents.add(element);
        this.update();
    }
    public void add(int index, ContentType element) {
        this.contents.add(index, element);
        this.update();
    }
    public void remove(ContentType element) {
        this.contents.remove(element);
        this.update();
    }
    public void remove(int index) {
        this.contents.remove(index);
        this.update();
    }
    public void clear() {
        this.contents.clear();
        this.update();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if(o instanceof ObservedUnlockedList) return this.contents.equals(((ObservedUnlockedList)o).contents);
        return false;
    }
}
