package extractor;

import org.apache.commons.lang3.Range;

import utils.PtbUtils;
import annotation.ISimpAnnotations.HypernymyAnnotation;
import annotation.ISimpAnnotations.HypernymyHypernymAnnotation;
import annotation.ISimpAnnotations.HypernymyHyponymAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

public class HypernymyExtractor extends ISimpExtractor {

  public HypernymyExtractor() {
    super(HypernymyAnnotation.class);
  }

  @Override
  protected SimplificationConstruct annotate(Tree tree, int index) {

    Range<Integer> npRange = null;
    Range<Integer> clRange = null;
    SimplificationConstruct construct = new SimplificationConstruct(
        new HypernymyAnnotation());

    for (Tree child : tree) {
      CoreLabel label = (CoreLabel) child.label();
      // hypernym
      Integer subindex = label.get(HypernymyHypernymAnnotation.class);
      if (subindex != null && subindex == index) {
        npRange = PtbUtils.getRange(child);
        construct.addComponent(new HypernymyHypernymAnnotation(), npRange);
      }
      // hyponym
      subindex = label.get(HypernymyHyponymAnnotation.class);
      if (subindex != null && subindex == index) {
        clRange = PtbUtils.getRange(child);
        construct.addComponent(new HypernymyHyponymAnnotation(), clRange);
      }
    }
    if (npRange == null) {
      throw new RuntimeException(String.format(
          "can not find hypernym: %s",
            tree.toString()));
    }
    if (clRange == null) {
      throw new RuntimeException(String.format(
          "can not find hyponym: %s",
            tree.toString()));
    }
    return construct;
  }
}
