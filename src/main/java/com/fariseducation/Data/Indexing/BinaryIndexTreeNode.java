package com.fariseducation.Data.Indexing;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

import com.fariseducation.Data.ManagedData;

@SuppressWarnings("rawtypes")
public class BinaryIndexTreeNode extends ManagedData implements TreeNode<BinaryIndexTreeNode> {
    private Comparable key;
    private Object element;
    private BinaryIndexTreeNode parent;
    private BinaryIndexTreeNode leftChild = null;
    private BinaryIndexTreeNode rightChild = null; 

    public BinaryIndexTreeNode(Comparable key, Object element, BinaryIndexTreeNode parent) {
        this.key = key;
        this.element = element;
        this.parent = parent;
    }

    public BinaryIndexTreeNode getLeftChild() {
        return this.leftChild;
    }
    public void setLeftChild(BinaryIndexTreeNode child) {
        this.leftChild = child;
    }
    public BinaryIndexTreeNode getRightChild() {
        return this.rightChild;
    }
    public void setRightChild(BinaryIndexTreeNode child) {
        this.rightChild = child;
    }
    @Override
    public UUID searchSubtree(Function<ArrayList<BinaryIndexTreeNode>, UUID> searcher) {
        ArrayList<BinaryIndexTreeNode> children = new ArrayList<BinaryIndexTreeNode>();
        children.add(this.leftChild);
        children.add(this.rightChild);

        return searcher.apply(
            new ArrayList<BinaryIndexTreeNode>(children));
    }
    @Override
    public BinaryIndexTreeNode getParent() {
        return this.parent;
    }
    @Override
    public Object getElement() {
        return this.element;
    }
    @Override
    public Comparable getKey() {
        return this.key;
    }
    @Override
    public int size() {
        int sum = 1;

        sum += this.leftChild.size();
        sum += this.rightChild.size();

        return sum;
    }
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/BinaryIndexTrees/");
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BinaryIndexTreeNode) {
            BinaryIndexTreeNode node = (BinaryIndexTreeNode)o;

            return 
                this.key==node.key && 
                this.element==node.key && 
                this.parent==node.parent && 
                this.leftChild==node.leftChild &&
                this.rightChild==node.rightChild;
        }
        return false;
    }
}
