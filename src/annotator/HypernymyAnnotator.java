package annotator;

import java.util.List;

import annotation.ISimpAnnotations.HypernymyAnnotation;
import annotation.ISimpAnnotations.HypernymyHypernymAnnotation;
import annotation.ISimpAnnotations.HypernymyHyponymAnnotation;
import detect.DetectionPattern;
import detect.DetectionTregexReader;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;

public class HypernymyAnnotator extends ISimpAnnotator {

  @Override
  public void annotate(Tree root) {
    int index = 0;

    List<DetectionPattern> list = DetectionTregexReader
        .getTregex(DetectionTregexReader.Hypernymy);

    for (DetectionPattern p : list) {
      TregexMatcher m = p.getTregexPattern().matcher(root);
      while (m.find()) {
        Tree matched = m.getMatch();

        CoreLabel label = (CoreLabel) matched.label();
        if (label.get(HypernymyAnnotation.class) == null) {
          label.set(HypernymyAnnotation.class, index);
          // ref
          Tree ref = m.getNode("hype");
          label = (CoreLabel) ref.label();
          label.set(HypernymyHypernymAnnotation.class, index);
          // elements
          Tree clause = m.getNode("hypo");
          label = (CoreLabel) clause.label();
          label.set(HypernymyHyponymAnnotation.class, index);
          index++;
        }
      }
    }
  }
}
