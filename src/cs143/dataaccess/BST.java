package cs143.dataaccess;

import cs143.domain.Retiree;
import java.io.Serializable;
import java.util.ArrayList;

public class BST implements Serializable {
    //declare necessary variables for BST class
    protected TreeNode root;
    protected int size = 0;
    
    /**
     * Default constructor of BST create an empty object of BST
     */
    public BST() {
    }
    
    /**
     * Constructor of BST
     * Create a new BST with the specified list of retiree
     * @param objects the list of retiree which is added to the BST
     */
    public BST(Retiree[] objects) {
        //add each retiree into the BST
        for (Retiree object : objects) {
            //add into the BST
            insert(object);
        }
    }
    
    /**
     * get the retiree existed in the BST with the specified SSN
     * @param ssn the social security number needs to be gotten from the BST
     * @return the retiree object which is matched with the SSN
     *         null if retiree object cannot be found in the BST
     */
    public Retiree get(long ssn) {
        TreeNode current = root; // Start from the root
        while (current != null) {
            if (current.element.getSsn() > ssn) {
                //element needs to be found is on the left
                current = current.left;
            } else if (current.element.getSsn() < ssn) {
                //element needs to be found is on the right
                current = current.right;
            } else {
                // element matches current.element
                return current.element; // Element is found
            }
        }
        return null;
    }
    
    /**
     * insert the specified retiree to the BST
     * @param r the retiree object needs to be added to BST
     * @return true if the retiree is added successfully
     *         false if the retiree is already existed
     */
    public final boolean insert(Retiree r) {
        if (root == null) { //empty tree, create new root
            root = createNewNode(r);
        } else {
            TreeNode current = root;
            while (current != null) {
                if (r.getSsn() < current.element.getSsn()) {
                    //poistion of the element needs to be inserted is on the left
                    current = current.left;
                } else if (r.getSsn() > current.element.getSsn()) {
                    //poistion of the element needs to be inserted is on the right
                    current = current.right;
                } else {
                    return false; //retiree already exists, fail
                }
            }
            current = createNewNode(r); //insert new retiree
        }
        //increase number of element if adding successfully
        size++;
        return true;
    }

    /**
     * delete the specified retiree from the BST
     * @param e the retiree needs to be deleted
     * @return true if deleting successfully
     *         false if cannot find the retiree to be deleted in the BST 
     */
    public boolean delete(Retiree e) {
        TreeNode parent = null;
        TreeNode current = root;
        //Finding the retiree which needs to be deleted
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }
        //return false if the target cannot be found
        if (current == null) {
            return false;
        }
        
        //deleting process when the left of current is null
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else if (e.compareTo(parent.element) < 0) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else {
            TreeNode parentOfRightMost = current;
            TreeNode rightMost = current.left;
            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            current.element = rightMost.element;
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else {
                parentOfRightMost.left = rightMost.left;
            }
        }
        //decrease the size of BST if deleting successfully
        size--;
        return true;
    }
    
    /**
     * create a new node in the tree
     * @param r the retiree object needs to be created in a new node
     * @return the treenode contain the retiree object
     */
    protected TreeNode createNewNode(Retiree r) {
        return new TreeNode(r);
    }
    
    /**
     * get the number of element in the BST
     * @return integer number of element in BST
     */
    public int getSize() {
        return size;
    }
    
    /**
     * get the root of the BST
     * @return the root of the BST
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * clear the whole BST
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * check if BST have any element
     * @return true if the BST has no elements
     *         false if the BST has at least 1 elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the list of the path to the specified retiree position starting 
     * from the root.
     * @param r the retiree needs to be found the path
     * @return the list of the step to come to the position of current retiree
     */
    public ArrayList<TreeNode> path(Retiree r) {
        ArrayList<TreeNode> list = new ArrayList<>();
        TreeNode current = root;
        
        //locate the path based on the searching algorithm
        while (current != null) {
            list.add(current);
            if (r.compareTo(current.element) < 0) {
                current = current.left;
            } else if (r.compareTo(current.element) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return list;
    }

    /**
     * the inner class tree node represent the node object which is used inside
     * this class
     */
    public static class TreeNode implements Serializable {

        public Retiree element;
        public TreeNode left;
        public TreeNode right;
        
        /**
         * create a new node with specified retiree object
         * @param r the retiree object which needs to be put in the node.
         */
        public TreeNode(Retiree r) {
            element = r;
        }
    }

}
