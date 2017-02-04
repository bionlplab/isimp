package annotator;

import annotation.ISimpAnnotations.AdjpCoordinationAnnotation;
import annotation.ISimpAnnotations.AdvpCoordinationAnnotation;
import annotation.ISimpAnnotations.CoordinationAnnotation;
import annotation.ISimpAnnotations.CoordinationConjunctAnnotation;
import annotation.ISimpAnnotations.CoordinationConjunctionAnnotation;
import annotation.ISimpAnnotations.NpCoordinationAnnotation;
import annotation.ISimpAnnotations.PpCoordinationAnnotation;
import annotation.ISimpAnnotations.SenCoordinationAnnotation;
import annotation.ISimpAnnotations.VpCoordinationAnnotation;
import annotator.coordination.Coordination;
import annotator.coordination.GeneralCoordination;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.Pair;

public class CoordinationAnnotator extends ISimpAnnotator {

  static final TregexPattern cooPattern = TregexPattern
                                            .compile("__ < CC|CONJP");

  private boolean detect(Coordination coor, TregexMatcher m, int index) {
    boolean detected = false;
    if (coor.isCoordination()) {
      // parent
      Tree matched = m.getMatch();
      CoreLabel label = (CoreLabel) matched.label();
      // subcategorize to np, vp, pp
      if (label.value().startsWith("N")) {
        label.set(NpCoordinationAnnotation.class, index);
      } else if (label.value().startsWith("V")) {
        label.set(VpCoordinationAnnotation.class, index);
      } else if (label.value().startsWith("P")
          || label.value().startsWith("IN")) {
        label.set(PpCoordinationAnnotation.class, index);
      } else if (label.value().startsWith("ADJP")
          || label.value().startsWith("JJ")) {
        label.set(AdjpCoordinationAnnotation.class, index);
      } else if (label.value().startsWith("ADVP")
          || label.value().startsWith("RB")) {
        label.set(AdvpCoordinationAnnotation.class, index);
      } else if (label.value().startsWith("S")) {
        label.set(SenCoordinationAnnotation.class, index);
      } else {
        label.set(CoordinationAnnotation.class, index);
      }
      // conjunts
      for (Pair<Integer, Tree> pair : coor.conjuncts) {
        Tree conjunct = pair.second;
        label = (CoreLabel) conjunct.label();
        label.set(CoordinationConjunctAnnotation.class, index);
      }
      // conjunction
      Tree conjunction = coor.conjunction;
      label = (CoreLabel) conjunction.label();
      label.set(CoordinationConjunctionAnnotation.class, index);
      detected = true;
    }
    return detected;
  }

  @Override
  public void annotate(Tree root) {
    int index = 0;
    TregexMatcher m = cooPattern.matcher(root);
    while (m.find()) {
      Tree matched = m.getMatch();
      CoreLabel label = (CoreLabel) matched.label();
      if (label.get(CoordinationAnnotation.class) == null
          || label.get(NpCoordinationAnnotation.class) == null
          || label.get(VpCoordinationAnnotation.class) == null
          || label.get(PpCoordinationAnnotation.class) == null
          || label.get(AdjpCoordinationAnnotation.class) == null
          || label.get(AdvpCoordinationAnnotation.class) == null
          || label.get(SenCoordinationAnnotation.class) == null) {
        Coordination coordination = new GeneralCoordination(root, matched);
        if (detect(coordination, m, index)) {
          index++;
        }
      }
    }
  }
}
