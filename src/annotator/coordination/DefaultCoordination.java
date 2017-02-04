package annotator.coordination;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.util.Pair;

public class DefaultCoordination extends Coordination {

  public DefaultCoordination(Tree root, Tree coordination) {
    super(root, coordination);
  }

  @Override
  public boolean isCoordination() {
    TregexMatcher m = conjPattern.matcher(coordination);
    if (!m.find()) {
      return false;
    }
    Tree parent = m.getMatch();

    boolean isCCOnlyFirst = true;
    for (int i = 0; i < parent.numChildren(); i++) {
      Tree child = parent.getChild(i);
      if (isCC(child) && i != 0) {
        isCCOnlyFirst = false;
      }
    }
    if (isCCOnlyFirst) {
      return false;
    }

    conjuncts.clear();
    for (int i = 0; i < parent.numChildren(); i++) {
      Tree child = parent.getChild(i);
      if (isComma(child) || isCC(child)) {
        conjunction = child;
      } else {
        conjuncts.add(new Pair<Integer, Tree>(i, child));
      }
    }
    return true;
  }
}