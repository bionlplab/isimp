package adapter;

import annotator.ISimpAnnotator;
import edu.stanford.nlp.trees.LabeledScoredTreeFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class ParenthesisAdapter extends ISimpAnnotator {

  // if LRB and RRB contains several elements
  static TregexPattern pattern1 = TregexPattern
                                    .compile("-LRB-=lrb $++ -RRB-=rrb !$+ (__ $+ =rrb)");

  // if ref contains several elements
  static TregexPattern pattern2 = TregexPattern
                                    .compile("PRN=prn !>2 __ $- NN|NNS|CD=nn");

  @Override
  protected void annotate(Tree tree) {
    boolean found = true;
    while (found) {
      found = false;
      TregexMatcher m = pattern1.matcher(tree);
      if (m.find()) {
        Tree np = new LabeledScoredTreeFactory().newLeaf("NP");
        // np.setValue("NP");
        Tree lrb = m.getNode("lrb");
        Tree rrb = m.getNode("rrb");
        Tree parent = lrb.parent(tree);
        replace(parent, lrb, rrb, np);
        found = true;
      }
      m = pattern2.matcher(tree);
      if (m.find()) {
        Tree np = new LabeledScoredTreeFactory().newLeaf("NP");
        // np.setValue("NP");
        Tree prn = m.getNode("prn");
        Tree parent = prn.parent(tree);
        Tree first = parent.firstChild();
        replace2(parent, first, prn, np);
        found = true;
      }
    }
  }

  // put [start, end) to newtree
  private void replace2(Tree parent, Tree start, Tree end, Tree newTree) {
    int indexOfStart = -1;
    int indexOfEnd = -1;
    for (int i = 0; i < parent.children().length; i++) {
      if (parent.children()[i] == start) {
        indexOfStart = i;
      }
      if (parent.children()[i] == end) {
        indexOfEnd = i;
        break;
      }
    }
    if (indexOfStart == -1) {
      throw new RuntimeException("cannot find start: " + parent);
    }
    if (indexOfEnd == -1) {
      throw new RuntimeException("cannot find end: " + parent);
    }
    // replace
    while (indexOfStart != indexOfEnd) {
      newTree.addChild(parent.children()[indexOfStart]);
      parent.removeChild(indexOfStart);
      indexOfEnd--;
    }
    parent.addChild(indexOfStart, newTree);
    return;
  }

  // put (start, end) to newtree
  private void replace(Tree parent, Tree start, Tree end, Tree newTree) {
    int indexOfStart = -1;
    int indexOfEnd = -1;
    for (int i = 0; i < parent.children().length; i++) {
      if (parent.children()[i] == start) {
        indexOfStart = i;
      }
      if (parent.children()[i] == end) {
        indexOfEnd = i;
        break;
      }
    }
    if (indexOfStart == -1) {
      throw new RuntimeException("cannot find start: " + parent);
    }
    if (indexOfEnd == -1) {
      throw new RuntimeException("cannot find end: " + parent);
    }
    // replace
    while (indexOfStart + 1 != indexOfEnd) {
      newTree.addChild(parent.children()[indexOfStart + 1]);
      parent.removeChild(indexOfStart + 1);
      indexOfEnd--;
    }
    parent.addChild(indexOfStart + 1, newTree);
    return;
  }

}
