package dev.qhc99.centralibj.utils.misc;


import dev.qhc99.centralibj.utils.LambdaUtils;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class BTreePrinter<Node> {

  private final  Callable<Node> getRoot;
  private final  Function<Node, String> formatNode;
  private final  Function<Node, Node> getLeft;
  private final  Function<Node, Node> getRight;

  public BTreePrinter( Callable<Node> getRoot,
                       Function<Node, String> formatNode,
                       Function<Node, Node> getLeft,
                       Function<Node, Node> getRight) {
    this.getRoot = getRoot;
    this.getLeft = getLeft;
    this.getRight = getRight;
    this.formatNode = formatNode;
  }

  private String traversePreOrder(Node root) {
    if (root == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    sb.append(formatNode.apply(root));

    String pointerRight = "└──";
    String pointerLeft = (getRight.apply(root) != null) ? "├──" : "└──";

    traverseNodes(sb, "", pointerLeft, getLeft.apply(root), getRight.apply(root) != null);
    traverseNodes(sb, "", pointerRight, getRight.apply(root), false);

    return sb.toString();
  }

  private void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                             boolean hasRightSibling) {

    if (node != null) {

      sb.append("\n");
      sb.append(padding);
      sb.append(pointer);
      sb.append(formatNode.apply(node));

      StringBuilder paddingBuilder = new StringBuilder(padding);
      if (hasRightSibling) {
        paddingBuilder.append("│  ");
      }
      else {
        paddingBuilder.append("   ");
      }

      String paddingForBoth = paddingBuilder.toString();
      String pointerRight = "└──";
      String pointerLeft = (getRight.apply(node) != null) ? "├──" : "└──";

      traverseNodes(sb, paddingForBoth, pointerLeft, getLeft.apply(node), getRight.apply(node) != null);
      traverseNodes(sb, paddingForBoth, pointerRight, getRight.apply(node), false);
    }
  }

  public void println() {
    print(System.out);
  }

  public void print(PrintStream os) {
    os.print(traversePreOrder(LambdaUtils.stripCE(getRoot)));
    os.print("\n");
  }

}