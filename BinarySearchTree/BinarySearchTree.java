public class
  BinarySearchTree<T extends Comparable<? super T>>
  extends BinaryTree<T> implements SearchTreeInterface<T> {

    public BinarySearchTree(){
      super();
    }

    public BinarySearchTree(T rootData){
      super();
      setRoot(new BinaryNode<T>(rootData));

    }

    public BinarySearchTree(BinaryNode<T> root){
      super(null);
      setRoot(root);//Have you seen About Time?
    }

    public void buildTree(T rootData) {
      throw new UnsupportedOperationException();
    }

    public void buildTree(T rootData,
                          BinaryTreeInterface<T> left,
                          BinaryTreeInterface<T> right) {
      throw new UnsupportedOperationException();
    }

    public boolean contains(T entry){
      return getEntry(entry) != null;
    }

    public T getEntry(T entry){
      return findEntry(getRoot(), entry);
    }

    private T findEntry(BinaryNode<T> root, T entry){
      T result = null;
      if(root != null){
        int compResult = root.getData().compareTo(entry);
        if(compResult == 0){
          result = root.getData();
        } else if(compResult < 0){ // the entry we're looking for is greater than the node we're on --> move right
          result = findEntry(root.getRightChild(), entry);
        } else { // the entry we're looking for is less than the node we're on --> move left
          result = findEntry(root.getLeftChild(), entry);
        }
      }
      return result;
    }

    public T add(T entry){
      T result = null;
      if(isEmpty()){
        setRoot(new BinaryNode<>(entry));
      } else {
        result = addEntry(getRoot(), entry);
      }
      return result;
    }

    private T addEntry(BinaryNode<T> root, T entry){
      T result = null;
      int comparison = root.getData().compareTo(entry);
      if(comparison == 0){
        result = root.getData(); // this is because in this case we're returning whatever we remove from the tree to check its data
        root.setData(entry);
      } else if(comparison < 0){
        if(root.hasRightChild()){
          result = addEntry(root.getRightChild(), entry);
        } else {
          root.setRightChild(new BinaryNode<>(entry));
        }
      } else {
        if(root.hasLeftChild()){
          result = addEntry(root.getLeftChild(), entry);
        } else {
          root.setLeftChild(new BinaryNode<>(entry));
        }
      }
      return result; // going to be null if we're putting it in a spot where there was nothing
                    // otherwise, we've replaced the data of whatever node was occupying that spot in the tree, so we'll return whatever the old data was
    }

    public T remove(T entry){
      // T result = findEntry(entry);
      ReturnObject envelope = new ReturnObject(null);
      setRoot(removeEntry(getRoot(), entry, envelope));

      return envelope.get();

    }

    private BinaryNode<T> removeEntry(BinaryNode<T> root,
                                      T entry, ReturnObject item){
      if(root != null){
        int comparison = root.getData().compareTo(entry);
        if(comparison == 0){
          item.set(root.getData());
          root = removeFromRoot(root);

        } else if (comparison < 0){
          root.setRightChild(removeEntry(root.getRightChild(), entry, item));
        } else {
          root.setLeftChild(removeEntry(root.getLeftChild(), entry, item));
        }

      }
      return root;
    }

    private BinaryNode<T> removeFromRoot(BinaryNode<T> root){
      //handles the three cases
      if(root.hasLeftChild() && root.hasRightChild()){
        BinaryNode<T> largest = findLargest(root.getLeftChild()); // find the largest item in the left subtree
        root.setData(largest.getData()); // set the node's data equal to that largest item in the left subtree
        root.setLeftChild(removeLargest(root.getLeftChild())); // remove the largest item in the left subtree (which is guarunteed to have at most one child)
      } else { 
        if(root.hasLeftChild()){ // if there's only one child, then just set this node's data equal to the data of that child
          root = root.getLeftChild();
        } else { // this cleverly handles 2 cases: 
                // (1) the nodes has just a right child; in which case, just set this node's data equal to that of the right child
                // (2) the node has no children, in which case getRightChild returns null, and thus we have set this node equal to null, which is what we would want if we are just deleting a leaf
          root = root.getRightChild();
        }
      }
      return root;
    }

    private BinaryNode<T> findLargest(BinaryNode<T> root){
      if(root.hasRightChild()){
        return findLargest(root.getRightChild());
      } else {
        return root;
      }
    }

    private BinaryNode<T> removeLargest(BinaryNode<T> root){
      if(root.hasRightChild()){
         root.setRightChild(removeLargest(root.getRightChild()));
      } else {
        root = root.getLeftChild();
      }
      return root;
    }

    private class ReturnObject {
      T item;
      private ReturnObject(T entry){
        item = entry;
      }
      private void set(T entry){
        item = entry;
      }
      private T get(){
        return item;
      }
    }
}
