package adapter;

import annotator.ISimpAnnotator;
import edu.stanford.nlp.trees.Tree;

public class OtherAdapter extends ISimpAnnotator{

  @Override
  protected void annotate(Tree tree) {
    boolean found = true;
    while (found) {
      found = false;
      for (AdapterPattern p : AdapterTregexReader.getTregex()) {
        found |= p.evaluate(tree);
      }
    }
  }

}
