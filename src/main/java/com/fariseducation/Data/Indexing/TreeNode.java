package com.fariseducation.Data.Indexing;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

public interface TreeNode<TreeNodeType> {
    public UUID searchSubtree(Function<ArrayList<TreeNodeType>, UUID> searcher);
    public int size();
    public boolean isEmpty();
    public TreeNodeType getParent();
    public Object getElement();
    public Comparable getKey();
}
