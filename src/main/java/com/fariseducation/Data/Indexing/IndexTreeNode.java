package com.fariseducation.Data.Indexing;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

import com.fariseducation.Data.ManagedData;

public class IndexTreeNode extends ManagedData implements TreeNode<IndexTreeNode> {
    private Comparable key;
    private Object element;
    private IndexTreeNode parent;
    protected ArrayList<IndexTreeNode> children = new ArrayList<IndexTreeNode>();

    public IndexTreeNode(Comparable key, Object element, IndexTreeNode parent) {
        this.key = key;
        this.element = element;
        this.parent = parent;
    }

    public void addChild(IndexTreeNode child) {
        this.children.add(child);
    }
    public void removeChild(IndexTreeNode child) {
        this.children.remove(child);
    }
    public UUID searchSubtree(Function<ArrayList<IndexTreeNode>, UUID> searcher) {
        return searcher.apply(this.children);
    }
    public IndexTreeNode getParent() {
        return this.parent;
    }
    public Object getElement() {
        return this.element;
    }
    public Comparable getKey() {
        return this.key;
    }
    public int size() {
        int sum = 1;

        for(IndexTreeNode node : this.children) {
            sum += node.size();
        }

        return sum;
    }
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/IndexTrees/");
    }
}
