package org.nathan.algorithmsJ.structures;

import org.junit.jupiter.api.Test;
import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderStatisticTreeTest{

  private static boolean isSizeConsistent(OrderStatisticTree<Integer> tree){
    return isSizeConsistent(tree.root);
  }

  private static boolean isSizeConsistent(OrderStatisticTree.Node<Integer> x){
    if(x == null){
      return true;
    }
    if(x.size != x.left.size + x.right.size + 1){
      return false;
    }
    return isSizeConsistent(x.left) && isSizeConsistent(x.right);
  }

  private static boolean isRankConsistent(OrderStatisticTree<Integer> tree){
    for(int i = 0; i < tree.root.size; i++){
      if(i != tree.getRankOfNode(tree.getNodeOfRank(i))){
        return false;
      }
    }

    for(var key : tree){
      if(tree.comparator.compare(key, tree.getKeyOfRank(tree.getRankOfKey(key))) != 0){
        return false;
      }
    }
    return true;
  }
}