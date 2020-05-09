package structue;

class BinarySearchTree {
    public static class BinaryNode{
        private double value;
        private BinaryNode parent;
        private BinaryNode left;
        private BinaryNode right;

        BinaryNode(double value){ this.value = value; }

        public double getValue(){ return this.value; }

        public BinaryNode getParent(){ return this.parent; }

        public BinaryNode getLeft(){ return this.left; }

        public BinaryNode getRight(){ return this.right; }
    }
    private BinaryNode root;

    public BinaryNode getMinimum(){
        if(root != null) {
            return getMinimum(this.root);
        }else{
            return null;
        }
    }
    private BinaryNode getMinimum(BinaryNode current){
        BinaryNode target = null;
        BinaryNode ptr = current;
        while(ptr != null){
            target = ptr;
            ptr = ptr.left;
        }
        return target;
    }

    public BinaryNode getMaximum(){
        if(root != null) {
            return getMaximum(this.root);
        }else{
            return null;
        }
    }
    private BinaryNode getMaximum(BinaryNode current){
        BinaryNode target = null;
        BinaryNode ptr = current;
        while(ptr != null){
            target = ptr;
            ptr = ptr.right;
        }
        return target;
    }

    public BinaryNode getRoot() { return this.root; }

    public void insert(double key) {
        BinaryNode n = new BinaryNode(key);
        if(root == null){
            this.root = n;
        }else {
            BinaryNode ptr = root;
            BinaryNode target_parent = null;
            while (ptr != null) {
                target_parent = ptr;
                if (n.value <= ptr.value) {
                    ptr = ptr.left;
                } else {
                    ptr = ptr.right;
                }
            }
            n.parent = target_parent;
            if (n.value < target_parent.value) {
                target_parent.left = n;
            } else {
                target_parent.right = n;
            }
        }
    }

    public void delete(BinaryNode target){
        if(target.left == null){ //case1:only one child, or no child
            transplant(target, target.right);
        }else if(target.right == null){ //case1
            transplant(target, target.left);
        }else{
            BinaryNode successor =  getSuccessor(target);
            if (successor.parent != target) { // case3:two child, not right successor. change to case2 to deal
                transplant(successor, successor.right); //successor must not have left node
                successor.right = target.right; // double link two node
                target.right.parent = successor; // double link two node

            } //case2: two child with right successor
            transplant(target, successor); // transplant, link
            successor.left = target.left; // double link two node
            target.left.parent = successor; // double link two node
        }
    }

    private void transplant(BinaryNode p, BinaryNode q){ //replace p with q
        if(p.parent == null){
            this.root = q;
        }else if(p == p.parent.left){
            p.parent.left = q;
        }else{
            p.parent.right = q;
        }
        if(q != null){
            q.parent = p.parent;
        }
    }

    private BinaryNode getSuccessor(BinaryNode current){
        if(current.right != null){
            return getMinimum(current.right);
        }else{
            BinaryNode target = current.parent;
            BinaryNode target_right = current;
            while(target != null & target.right == target_right){
                target_right = target;
                target = target.parent;
            }
            return target;
        }
    }
    private BinaryNode getPredecessor(BinaryNode current){
        if(current.left != null){
            return getMaximum(current.left);
        }else{
            BinaryNode target = current.parent;
            BinaryNode target_left = current;
            while(target != null & target.left == target_left){
                target_left = target;
                target = target.parent;
            }
            return target;
        }
    }

    public BinaryNode search(double key){
        if(root == null){
            return null;
        }
        return search(root, key);
    }
    private BinaryNode search(BinaryNode n, double key){
        if(n.value == key){
            return n;
        }else if(key < n.value & n.left != null){
            return search(n.left, key);
        }else if(key > n.value & n.right != null){
            return search(n.right, key);
        }
        return null;
    }

    public void printTree(){
        if(root == null){
            return;
        }
        inorderTreeWalk(root.left);
        System.out.print(root.value);
        System.out.print(' ');
        inorderTreeWalk(root.right);
        System.out.print('\n');
    }
    private void inorderTreeWalk(BinaryNode n){
        if(n != null){
            inorderTreeWalk(n.left);
            System.out.print(n.value);
            System.out.print(' ');
            inorderTreeWalk(n.right);
        }
    }

    public int getHeight(){
        if(root == null){
            return 0;
        }
        int count = 1;
        int left_max = inorderTreeWalk(root.left, count);
        int right_max = inorderTreeWalk(root.right, count);
        return Math.max(left_max, right_max);
    }
    private int inorderTreeWalk(BinaryNode n, int count){
        if(n != null){
            int left_max = inorderTreeWalk(n.left, count + 1);
            int right_max = inorderTreeWalk(n.right, count + 1);
            return Math.max(left_max, right_max);
        }
        return count;
    }

    public int getNodesNumber(){
        if(root == null){
            return 0;
        }
        return inorderTreeWalk(root,  "sum");
    }
    private int inorderTreeWalk(BinaryNode n, String sum){
        if(!sum.equals("sum")) throw new IllegalArgumentException();
        if(n.right != null & n.left == null){
            return inorderTreeWalk(n.right, "sum") + 1;
        }else if(n.right == null & n.left != null){
            return inorderTreeWalk(n.left, "sum") + 1;
        }else if(n.right != null & n.left != null){
            return inorderTreeWalk(n.left, "sum") + inorderTreeWalk(n.right, "sum") + 1;
        }else{
            return 1;
        }
    }

}