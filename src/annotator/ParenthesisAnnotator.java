package annotator;

import annotation.ISimpAnnotations.ParenthesisAnnotation;
import annotation.ISimpAnnotations.ParenthesisElemAnnotation;
import annotation.ISimpAnnotations.ParenthesisRefAnnotation;
import detect.DetectionPattern;
import detect.DetectionTregexReader;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;

public class ParenthesisAnnotator extends ISimpAnnotator {

  @Override
  public void annotate(Tree root) {
    int index = 0;
    // parenthesis.txt
    for (DetectionPattern p : DetectionTregexReader
        .getTregex(DetectionTregexReader.ParentThesis)) {
      TregexMatcher m = p.getTregexPattern().matcher(root);
      while (m.find()) {
        Tree matched = m.getMatch();
        CoreLabel label = (CoreLabel) matched.label();
        if (label.get(ParenthesisAnnotation.class) == null) {
          label.set(ParenthesisAnnotation.class, index);
          // ref
          Tree ref = m.getNode("ref");
          label = (CoreLabel) ref.label();
          label.set(ParenthesisRefAnnotation.class, index);
          // elements
          Tree elemens = m.getNode("elements");
          label = (CoreLabel) elemens.label();
          label.set(ParenthesisElemAnnotation.class, index);
          index++;
        }
      }
    }
  }
}
