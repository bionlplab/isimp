package annotator;

import java.util.List;

import annotation.ISimpAnnotations.AppositionAnnotation;
import annotation.ISimpAnnotations.AppositionAppositiveAnnotation;
import annotation.ISimpAnnotations.AppositionRefAnnotation;
import detect.DetectionPattern;
import detect.DetectionTregexReader;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;

public class AppositionAnnotator extends ISimpAnnotator {

  @Override
  public void annotate(Tree root) {
    int index = 0;
    
    List<DetectionPattern> list = DetectionTregexReader
        .getTregex(DetectionTregexReader.Apposition);
    
    for (DetectionPattern p : list) {
      TregexMatcher m = p.getTregexPattern().matcher(root);
      while (m.find()) {
        Tree matched = m.getMatch();

        CoreLabel label = (CoreLabel) matched.label();
        if (label.get(AppositionAnnotation.class) == null) {
          label.set(AppositionAnnotation.class, index);
          // ref
          Tree ref = m.getNode("np1");
          label = (CoreLabel) ref.label();
          label.set(AppositionRefAnnotation.class, index);
          // elements
          Tree clause = m.getNode("np2");
          label = (CoreLabel) clause.label();
          label.set(AppositionAppositiveAnnotation.class, index);
          index++;
        }
      }
    }
  }
}
