package annotator;

import java.util.List;

import annotation.ISimpAnnotations.FullRelativeClauseAnnotation;
import annotation.ISimpAnnotations.RedRelativeClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseRefAnnotation;
import detect.DetectionPattern;
import detect.DetectionTregexReader;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;

public class RelativeClauseAnnotator extends ISimpAnnotator {

  @Override
  public void annotate(Tree root) {
    int index = 0;
    List<DetectionPattern> list = DetectionTregexReader
        .getTregex(DetectionTregexReader.RelativeClause);
    for (DetectionPattern p : list) {
      TregexMatcher m = p.getTregexPattern().matcher(root);
      while (m.find()) {
        Tree matched = m.getMatch();
        CoreLabel label = (CoreLabel) matched.label();
        if (label.get(RelativeClauseAnnotation.class) == null
            || label.get(FullRelativeClauseAnnotation.class) == null
            || label.get(RedRelativeClauseAnnotation.class) == null) {
          // ref
          Tree ref = m.getNode("ref");
          label = (CoreLabel) ref.label();
          label.set(RelativeClauseRefAnnotation.class, index);
          // elements
          Tree clause = m.getNode("clause");
          CoreLabel clLabel = (CoreLabel) clause.label();
          clLabel.set(RelativeClauseClauseAnnotation.class, index);
          // subcategorize
          label = (CoreLabel) matched.label();
          if (clLabel.value().startsWith("S")) {
            label.set(FullRelativeClauseAnnotation.class, index);
          } else if (clLabel.value().startsWith("V")) {
            label.set(RedRelativeClauseAnnotation.class, index);
          } else {
            label.set(RelativeClauseAnnotation.class, index);
          }
          index++;
        }
      }
    }

  }

}
