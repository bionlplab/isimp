package annotator.coordination;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.util.Pair;

public class GeneralCoordination extends Coordination {

  public GeneralCoordination(Tree root, Tree coordination) {
    super(root, coordination);
  }

  @Override
  public boolean isCoordination() {
    TregexMatcher m = conjPattern.matcher(coordination);
    if (!m.find()) {
      return false;
    }

    Tree parent = m.getMatch();
    boolean hasCC = false;
    conjuncts.clear();
    int state = 0;
    for (int i = 0; i < parent.numChildren(); i++) {
      Tree child = parent.getChild(i);
      switch (state) {
      case 0:
        if (isCC(child)) {
          conjunction = child;
          state = 1;
        } else if (isComma(child)) {
          return false;
        } else {
          conjuncts.add(new Pair<Integer, Tree>(i, child));
          state = 2;
        }
        break;
      case 1:
        conjuncts.add(new Pair<Integer, Tree>(i, child));
        state = 2;
        break;
      case 2:
        if (isCC(child)) {
          conjunction = child;
          state = 4;
        } else if (isComma(child)) {
          state = 3;
        } else {
          return false;
        }
        break;
      case 3:
        if (isCC(child) && i != parent.numChildren() - 1) {
          conjunction = child;
          state = 4;
        } else {
          conjuncts.add(new Pair<Integer, Tree>(i, child));
          state = 2;
        }
        break;
      case 4:
        if (child.value().equals("ADVP")
            && !conjuncts.get(0).second.value().equals("ADVP")) {
          ;
        } else if (child.value().equals("RB")
            && !conjuncts.get(0).second.value().equals("RB")) {
          ;
        } else {
          hasCC = true;
          conjuncts.add(new Pair<Integer, Tree>(i, child));
          state = 5;
        }
        break;
      default:
        if (isComma(child)) {

        } else {
          hasCC = false;
        }
      }
    }
    if (!hasCC) {
      return false;
    }
    // isomorphic
    // only compare the first and last conjuncts
    Tree first = conjuncts.getFirst().second;
    Tree last = conjuncts.getLast().second;
    if (first.numChildren() == 1 && last.numChildren() == 1) {
      if (first.firstChild().isLeaf() && last.firstChild().isLeaf()) {
        return true;
      }
    }
    return isIsomorphic(first, last);
  }

  private boolean isIsomorphic(Tree t1, Tree t2) {
    return true;
  }

}
