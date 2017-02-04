package extractor;

import org.apache.commons.lang3.Range;

import utils.PtbUtils;
import annotation.ISimpAnnotations.FullRelativeClauseAnnotation;
import annotation.ISimpAnnotations.RedRelativeClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseClauseAnnotation;
import annotation.ISimpAnnotations.RelativeClauseRefAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.TypesafeMap.Key;

public class RelativeClauseExtractor extends ISimpExtractor {

  public RelativeClauseExtractor() {
    super(RelativeClauseAnnotation.class);
  }

  protected RelativeClauseExtractor(Class<? extends Key<Integer>> annotationKey) {
    super(annotationKey);
  }

  public static class FullRelativeClauseExtractor extends
      RelativeClauseExtractor {

    public FullRelativeClauseExtractor() {
      super(FullRelativeClauseAnnotation.class);
    }
  }

  public static class RedRelativeClauseExtractor extends
      RelativeClauseExtractor {

    public RedRelativeClauseExtractor() {
      super(RedRelativeClauseAnnotation.class);
    }
  }

  @Override
  protected SimplificationConstruct annotate(Tree tree, int index) {

    Range<Integer> npRange = null;
    Range<Integer> clRange = null;
    SimplificationConstruct construct;
    if (annotationKey == FullRelativeClauseAnnotation.class) {
      construct = new SimplificationConstruct(
          new FullRelativeClauseAnnotation());
    } else if (annotationKey == RedRelativeClauseAnnotation.class) {
      construct = new SimplificationConstruct(new RedRelativeClauseAnnotation());
    } else {
      construct = new SimplificationConstruct(new RelativeClauseAnnotation());
    }

    for (Tree child : tree) {
      CoreLabel label = (CoreLabel) child.label();
      // ref
      Integer subindex = label.get(RelativeClauseRefAnnotation.class);
      if (subindex != null && subindex == index) {
        npRange = PtbUtils.getRange(child);
        construct.addComponent(new RelativeClauseRefAnnotation(), npRange);
      }
      // clause
      subindex = label.get(RelativeClauseClauseAnnotation.class);
      if (subindex != null && subindex == index) {
        clRange = PtbUtils.getRange(child);
        construct.addComponent(new RelativeClauseClauseAnnotation(), clRange);
        break;
      }
    }
    if (npRange == null) {
      throw new RuntimeException(String.format(
          "can not find RELREF: %s",
          tree.toString()));
    }
    if (clRange == null) {
      throw new RuntimeException(String.format(
          "can not find CLAUSE: %s",
          tree.toString()));
    }
    return construct;
  }

}
