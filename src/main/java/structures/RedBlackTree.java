package structures;

import tools.IntegerPair;


public class RedBlackTree<V> {
    enum COLOR{ RED, BLACK }
    private ColorNode root = null;
    private final ColorNode sentinel = new ColorNode( COLOR.BLACK);// sentinel: denote leaf and parent of root
    public class ColorNode{
        private IntegerPair<V> content;
        private COLOR color;
        private ColorNode parent;
        private ColorNode left;
        private ColorNode right;

        private ColorNode(int key){
            content = new IntegerPair<V>();
            content.setKey(key);
            color = COLOR.RED;
        }
        private ColorNode(COLOR color){
            this.color = color;
        }
        private ColorNode(int key, V val){
            content = new IntegerPair<>();
            color = COLOR.RED;
            content.setKey(key);
            content.setValue(val);
        }

        public boolean isRed(){ return color == COLOR.RED; }
        public boolean isBlack(){ return color == COLOR.BLACK; }

        public COLOR getColor(){ return color; }
        public void setColor(COLOR color){ this.color = color; }

        private void setRed(){ color = COLOR.RED; }
        private void setBlack() { color = COLOR.BLACK; }

        public V getValue(){ return content.getValue(); }
        public int getKey() { return content.getKey(); }

        public ColorNode getParent(){ return parent; }
        private void setParent(ColorNode parent){ this.parent = parent; }

        public ColorNode getLeft(){ return left; }
        private void setLeft(ColorNode left){ this.left = left; }

        public ColorNode getRight(){ return right; }
        private void setRight(ColorNode right){ this.right = right; }
    }

    public ColorNode getMinimum(){
        if(getRoot() != null & getSentinel() != getRoot()) {
            return getMinimum(getRoot());
        }else{
            return null;
        }
    }
    public ColorNode getMaximum(){
        if(getRoot() != null & getRoot() != getSentinel()) {
            return getMaximum(getRoot());
        }else{
            return null;
        }
    }

    private ColorNode getSentinel() { return sentinel; }
    private void setRoot(ColorNode r) {
        root = r;
        root.setParent(getSentinel());
    }
    public ColorNode getRoot() { return root; }

    public void printTree(){ // inorder print
        if(getRoot() == null | getSentinel() == getRoot()){
            return;
        }
        inorderTreeWalk(getRoot().getLeft());
        System.out.print(getRoot().getKey());
        System.out.print(' ');
        inorderTreeWalk(getRoot().getRight());
        System.out.print('\n');
    }
    public int getNodesNumber(){
        if(getRoot() == getSentinel()){
            return 0;
        }
        return inorderTreeWalk(getRoot(), "sum");
    }
    public int getHeight(){
        if(getRoot() == null | getRoot() == getSentinel()){
            return 0;
        }
        int count = 1;
        int left_max = inorderTreeWalk(getRoot().getLeft(), count);
        int right_max = inorderTreeWalk(getRoot().getRight(), count);
        return Math.max(left_max, right_max) - 1;
    }

    private void insert(ColorNode n){
        if(n == null | n == sentinel) return;
        if(getRoot() == null | getRoot() == getSentinel()){
            setRoot(n);
            getRoot().setRight(getSentinel());
            getRoot().setLeft(getSentinel());
        }else{
            var store = getSentinel();
            var ptr = getRoot();
            while(ptr != getSentinel()){
                store = ptr;
                if(n.getKey() < ptr.getKey()){
                    ptr = ptr.getLeft();
                }else{
                    ptr = ptr.getRight();
                }
            }
            n.setParent(store);
            if(n.getKey() < store.getKey()){
                store.setLeft(n);
            }else{
                store.setRight(n);
            }
            n.setLeft(getSentinel());
            n.setRight(getSentinel());
        }
        insertFixUp(n);
    }
    public void insert(int key, V val){ insert(new ColorNode(key, val)); }
    private void insertFixUp(ColorNode ptr){
        while(ptr.getParent().isRed()){
            if(ptr.getParent() == ptr.getParent().getParent().getLeft()){
                var right = ptr.getParent().getParent().getRight();
                if(right.isRed()){ // case1: sibling is red
                    ptr.getParent().setBlack();
                    right.setBlack();
                    ptr.getParent().getParent().setRed();
                    ptr = ptr.getParent().getParent();
                    continue;
                }else if(ptr == ptr.getParent().getRight()){ //case 2 convert to case 3
                    ptr = ptr.getParent();
                    leftRotate(ptr);
                }
                ptr.getParent().setBlack(); // case3
                ptr.getParent().getParent().setRed();
                rightRotate(ptr.getParent().getParent()); // ptr.getParent will be black and then break
                ptr = ptr.getParent();
            }else{
                var left = ptr.getParent().getParent().getLeft();
                if(left.isRed()){
                    ptr.getParent().setBlack();
                    left.setBlack();
                    ptr.getParent().getParent().setRed();
                    ptr = ptr.getParent().getParent();
                    continue;
                }else if(ptr == ptr.getParent().getLeft()){
                    ptr = ptr.getParent();
                    rightRotate(ptr);
                }
                ptr.getParent().setBlack();
                ptr.getParent().getParent().setRed();
                leftRotate(ptr.getParent().getParent());
                ptr = ptr.getParent();
            }
        }
        getRoot().setBlack();
    }

    public void delete(ColorNode target) {
        if(target == null | target == getSentinel()){
            return;
        }
        var ptr = target;
        var ptr_color = ptr.getColor();
        ColorNode fix_up;
        if(ptr.getLeft() == getSentinel()){
            fix_up = target.getRight();
            transplant(target, fix_up);
        }else if(ptr.getRight() == getSentinel()){
            fix_up = target.getLeft();
            transplant(target, fix_up);
        }else{
            ptr = getSuccessor(target);
            ptr_color = ptr.getColor();
            fix_up = ptr.getRight();
            if(ptr.getParent() == target){
                fix_up.setParent(ptr); // in case of sentinel refer to target
            }else{
                transplant(ptr, ptr.getRight());
                ptr.setRight(target.getRight());
                target.getRight().setParent(ptr);
            }
            transplant(target, ptr);
            ptr.setLeft(target.getLeft());
            target.getLeft().setParent(ptr);
            ptr.setColor(target.getColor());
        }
        if(ptr_color == COLOR.BLACK){ // delete black node may violate property of red-black tree
            deleteFixUp(fix_up);
        }
    }
    private void deleteFixUp(ColorNode fix_up){
        while(fix_up != getRoot() & fix_up.isBlack()){
            ColorNode sibling;
            if(fix_up == fix_up.getParent().getLeft()){
                sibling = fix_up.getParent().getRight();
                if(sibling.isRed()) { // case1:sibling is black, convert to case 2, 3 or 4
                    sibling.setBlack(); // , which denote that sibling is black
                    fix_up.getParent().setRed();
                    leftRotate(fix_up.getParent());
                    sibling = fix_up.getParent().getRight();
                }
                if(sibling.getLeft().isBlack() & sibling.getRight().isBlack()){ // case2: sibling children is black
                    sibling.setRed();
                    fix_up = fix_up.getParent();
                    continue; // may break while condition
                }else if(sibling.getRight().isBlack()){ // case3: sibling left red, right black. convert case4
                    sibling.getLeft().setBlack();
                    sibling.setRed();
                    rightRotate(sibling);
                    sibling = fix_up.getParent().getRight();
                }
                sibling.setColor(fix_up.getParent().getColor()); // case4: sibling right red
                fix_up.getParent().setBlack();
                sibling.getRight().setBlack();
                leftRotate(fix_up.getParent());
            }else{
                sibling = fix_up.getParent().getLeft();
                if(sibling.isRed()) {
                    sibling.setBlack();
                    fix_up.getParent().setRed();
                    rightRotate(fix_up.getParent());
                    sibling = fix_up.getParent().getLeft();
                }
                if(sibling.getLeft().isBlack() & sibling.getRight().isBlack()){
                    sibling.setRed();
                    fix_up = fix_up.getParent();
                    continue;
                }else if(sibling.getLeft().isBlack()){
                    sibling.getRight().setBlack();
                    sibling.setRed();
                    leftRotate(sibling);
                    sibling = fix_up.getParent().getLeft();
                }
                sibling.setColor(fix_up.getParent().getColor());
                fix_up.getParent().setBlack();
                sibling.getLeft().setBlack();
                rightRotate(fix_up.getParent());
            }
            fix_up = getRoot();
        }
        fix_up.setBlack();
    }

    private void transplant(ColorNode a, ColorNode b){
        if(a.getParent() == getSentinel()){
            setRoot(b);
        }else if(a.getParent().getRight() == a){
            a.getParent().setRight(b);
            b.setParent(a.getParent()); // permissible if b is sentinel
        }else{
            a.getParent().setLeft(b);
            b.setParent(a.getParent());
        }
    }

    public ColorNode search(int key){
        if(getRoot() == null | getRoot() == getSentinel()){
            return null;
        }
        return search(getRoot(), key);
    }
    private ColorNode search(ColorNode n, int key){
        if(n.getKey() == key){
            return n;
        }else if(key < n.getKey() & n.getLeft() != getSentinel()){
            return search(n.getLeft(), key);
        }else if(key > n.getKey() & n.getRight() != getSentinel()){
            return search(n.getRight(), key);
        }
        return null;
    }

    private void leftRotate(ColorNode left_node){
        var right_node = left_node.getRight();
        //exchange
        left_node.setRight(right_node.getLeft());
        if(right_node.getLeft() != getSentinel()){ // remember to double link
            right_node.getLeft().setParent(left_node);
        }
        //exchange
        right_node.setParent(left_node.getParent()); // double link right_node to left_node parent
        if(left_node.getParent() == getSentinel()){
            setRoot(right_node);
        }else if(left_node.getParent().getLeft() == left_node){
            left_node.getParent().setLeft(right_node);
        }else{
            left_node.getParent().setRight(right_node);
        }
        //exchange
        right_node.setLeft(left_node);
        left_node.setParent(right_node);
    }
    private void rightRotate(ColorNode right_node){ // mirror of leftRotate
        var left_node = right_node.getLeft();
        //exchange
        right_node.setLeft(left_node.getRight());
        if(left_node.getRight() != getSentinel()){ // remember to double link
            left_node.getRight().setParent(right_node);
        }
        //exchange
        left_node.setParent(right_node.getParent()); // double link right_node to left_node parent
        if(right_node.getParent() == getSentinel()){
            setRoot(left_node);
        }else if(right_node.getParent().getRight() == right_node){
            right_node.getParent().setRight(left_node);
        }else{
            right_node.getParent().setLeft(left_node);
        }
        //exchange
        left_node.setRight(right_node);
        right_node.setParent(left_node);
    }

    private ColorNode getMinimum(ColorNode current){
        ColorNode target = null;
        var ptr = current;
        while(ptr != getSentinel()){
            target = ptr;
            ptr = ptr.getLeft();
        }
        return target;
    }
    private ColorNode getMaximum(ColorNode current){
        ColorNode target = null;
        var ptr = current;
        while(ptr != getSentinel()){
            target = ptr;
            ptr = ptr.getRight();
        }
        return target;
    }

    private ColorNode getSuccessor(ColorNode current){
        if(current.getRight() != getSentinel()){
            return getMinimum(current.getRight());
        }else{
            var target = current.getParent();
            var target_right = current;
            while(target != getSentinel() & target.getRight() == target_right){
                target_right = target;
                target = target.getParent();
            }
            return target;
        }
    }
    private ColorNode getPredecessor(ColorNode current){
        if(current.getLeft() != null){
            return getMaximum(current.getLeft());
        }else{
            var target = current.getParent();
            var target_left = current;
            while(target != null){
                if(target.getLeft() == target_left) {
                    target_left = target;
                    target = target.getParent();
                }else break;
            }
            return target;
        }
    }

    private void inorderTreeWalk(ColorNode n){
        if(n != getSentinel()){
            inorderTreeWalk(n.getLeft());
            System.out.print(n.getKey());
            System.out.print(' ');
            inorderTreeWalk(n.getRight());
        }
    }
    private int inorderTreeWalk(ColorNode n, int count){
        if(n != getSentinel()){
            int left_max = inorderTreeWalk(n.getLeft(), count + 1);
            int right_max = inorderTreeWalk(n.getRight(), count + 1);
            return Math.max(left_max, right_max);
        }
        return count;
    }
    private int inorderTreeWalk(ColorNode n, String sum){ //overload trick
        if(!sum.equals("sum")) throw new IllegalArgumentException();
        if(n.getRight() != getSentinel() & n.getLeft() == getSentinel()){
            return inorderTreeWalk(n.getRight(), "sum") + 1;
        }else if(n.getRight() == getSentinel() & n.getLeft() != getSentinel()){
            return inorderTreeWalk(n.getLeft(), "sum") + 1;
        }else if(n.getRight() != getSentinel() & n.getLeft() != getSentinel()){
            return inorderTreeWalk(n.getLeft(), "sum") + inorderTreeWalk(n.getRight(), "sum") + 1;
        }else{
            return 1;
        }
    }

}