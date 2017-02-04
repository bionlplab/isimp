package extractor;

import org.apache.commons.lang3.Range;

import utils.PtbUtils;
import annotation.ISimpAnnotations.AdjpCoordinationAnnotation;
import annotation.ISimpAnnotations.AdvpCoordinationAnnotation;
import annotation.ISimpAnnotations.CoordinationAnnotation;
import annotation.ISimpAnnotations.CoordinationConjunctAnnotation;
import annotation.ISimpAnnotations.CoordinationConjunctionAnnotation;
import annotation.ISimpAnnotations.NpCoordinationAnnotation;
import annotation.ISimpAnnotations.PpCoordinationAnnotation;
import annotation.ISimpAnnotations.SenCoordinationAnnotation;
import annotation.ISimpAnnotations.VpCoordinationAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.TypesafeMap.Key;

public class CoordinationExtractor extends ISimpExtractor {

  public CoordinationExtractor() {
    super(CoordinationAnnotation.class);
  }

  protected CoordinationExtractor(Class<? extends Key<Integer>> annotationKey) {
    super(annotationKey);
  }

  public static class NpCoordinationExtractor extends CoordinationExtractor {

    public NpCoordinationExtractor() {
      super(NpCoordinationAnnotation.class);
    }
  }

  public static class VpCoordinationExtractor extends CoordinationExtractor {

    public VpCoordinationExtractor() {
      super(VpCoordinationAnnotation.class);
    }
  }

  public static class PpCoordinationExtractor extends CoordinationExtractor {

    public PpCoordinationExtractor() {
      super(PpCoordinationAnnotation.class);
    }
  }

  public static class AdjpCoordinationExtractor extends CoordinationExtractor {

    public AdjpCoordinationExtractor() {
      super(AdjpCoordinationAnnotation.class);
    }
  }

  public static class AdvpCoordinationExtractor extends CoordinationExtractor {

    public AdvpCoordinationExtractor() {
      super(AdvpCoordinationAnnotation.class);
    }
  }

  public static class SenCoordinationExtractor extends CoordinationExtractor {

    public SenCoordinationExtractor() {
      super(SenCoordinationAnnotation.class);
    }
  }

  @Override
  protected SimplificationConstruct annotate(Tree tree, int index) {

    SimplificationConstruct construct;
    if (annotationKey == NpCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new NpCoordinationAnnotation());
    } else if (annotationKey == VpCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new VpCoordinationAnnotation());
    } else if (annotationKey == PpCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new PpCoordinationAnnotation());
    } else if (annotationKey == AdjpCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new AdjpCoordinationAnnotation());
    } else if (annotationKey == AdvpCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new AdvpCoordinationAnnotation());
    } else if (annotationKey == SenCoordinationAnnotation.class) {
      construct = new SimplificationConstruct(new SenCoordinationAnnotation());
    } else {
      construct = new SimplificationConstruct(new CoordinationAnnotation());
    }

    for (Tree child : tree) {

      CoreLabel label = (CoreLabel) child.label();
      // conjunct
      Integer subindex = label.get(CoordinationConjunctAnnotation.class);
      if (subindex != null && subindex == index) {
        Range<Integer> range = PtbUtils.getRange(child);
        construct.addComponent(new CoordinationConjunctAnnotation(), range);
      }
      // conjunction
      subindex = label.get(CoordinationConjunctionAnnotation.class);
      if (subindex != null && subindex == index) {
        Range<Integer> range = PtbUtils.getRange(child);
        construct.addComponent(new CoordinationConjunctionAnnotation(), range);
      }
    }
    return construct;
  }

}
