package cs143.dataaccess;

import cs143.domain.Retiree;
import java.io.Serializable;
import java.util.ArrayList;

public final class SsnAvl extends BST implements Serializable {

    public SsnAvl() {
    }

    public SsnAvl(Retiree[] objects) {
        for (Retiree r: objects) {
            add(r);
        } 
    }

    public boolean add(Retiree r) {
        if (!super.insert(r)) {
            return false; //retiree not added
        } else {
           balancePath(r); //rebalance if needed
        }
        return true; 
    }

    public boolean remove(Retiree r) {
        if (root == null) {
            return false; // Element is not in the tree
        }
        // Locate the node to be deleted and also locate its parent node
        TreeNode parent = null;
        TreeNode current = root;
        while (current != null) {
            if (r.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (r.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                break; // Element is in the tree pointed by current
            }
        }

        if (current == null) {
            return false; // Element is not in the tree
        }
        // Case 1: current has no left children (See Figure 23.6)
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            } else {
                if (r.compareTo(parent.element) < 0) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }

                // Balance the tree if necessary
                balancePath(parent.element);
            }
        } else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode parentOfRightMost = current;
            TreeNode rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else // Special case: parentOfRightMost is current
            {
                parentOfRightMost.left = rightMost.left;
            }

            // Balance the tree if necessary
            balancePath(parentOfRightMost.element);
        }

        size--;
        return true; // Element inserted
    }


    @Override
    protected AVLTreeNode createNewNode(Retiree r) {
        return new AVLTreeNode(r);
    }

    private void updateHeight(AVLTreeNode node) {
        if (node.left == null && node.right == null) {
            node.height = 0;
        } else if (node.left == null) {
            node.height = 1 + ((AVLTreeNode) (node.right)).height;
        } else if (node.right == null) {
            node.height = 1 + ((AVLTreeNode) (node.left)).height;
        } else {
            node.height = 1
                    + Math.max(((AVLTreeNode) (node.right)).height,
                            ((AVLTreeNode) (node.left)).height);
        }
    }

    private void balancePath(Retiree r) {
        ArrayList<TreeNode> path = path(r);
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode a = (AVLTreeNode) (path.get(i));
            updateHeight(a);
            AVLTreeNode parentOfA = (a == root) ? null
                    : (AVLTreeNode) (path.get(i - 1));
            switch (balanceFactor(a)) {
                case -2:
                    if (balanceFactor((AVLTreeNode) a.left) <= 0) {
                        balanceLL(a, parentOfA);
                    } else {
                        balanceLR(a, parentOfA);
                    }
                    break;
                case +2:
                    if (balanceFactor((AVLTreeNode) a.right) >= 0) {
                        balanceRR(a, parentOfA);
                    } else {
                        balanceRL(a, parentOfA);
                    }
            }
        }
    }

    private int balanceFactor(AVLTreeNode node) {
        if (node.right == null) {
            return -node.height;
        } else if (node.left == null) {
            return +node.height;
        } else {
            return ((AVLTreeNode) node.right).height
                    - ((AVLTreeNode) node.left).height;
        }
    }

    private void balanceLL(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.left; 
        if (a == root) {
            root = b;
        } else if (parentOfA.left == a) {
            parentOfA.left = b;
        } else {
            parentOfA.right = b;
        }

        a.left = b.right; 
        b.right = a; 
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
    }

    private void balanceLR(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.left; 
        TreeNode c = b.right; 
        if (a == root) {
            root = c;
        } else if (parentOfA.left == a) {
            parentOfA.left = c;
        } else {
            parentOfA.right = c;
        }
        a.left = c.right; 
        b.right = c.left; 
        c.left = b;
        c.right = a;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
        updateHeight((AVLTreeNode) c);
    }

    private void balanceRR(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.right; 
        if (a == root) {
            root = b;
        } else if (parentOfA.left == a) {
            parentOfA.left = b;
        } else {
            parentOfA.right = a;
        }
        a.right = b.left; 
        b.left = a;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
    }

    private void balanceRL(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.right; 
        TreeNode c = b.left; 
        if (a == root) {
            root = c;
        } else if (parentOfA.left == a) {
            parentOfA.left = c;
        } else {
            parentOfA.right = c;
        }
        a.right = c.left; 
        b.left = c.right; 
        c.left = a;
        c.right = b;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
        updateHeight((AVLTreeNode) c);
    }

    protected static class AVLTreeNode extends BST.TreeNode implements Serializable {

        protected int height = 0;

        public AVLTreeNode(Retiree r) {
            super(r);
        }
    }

}
