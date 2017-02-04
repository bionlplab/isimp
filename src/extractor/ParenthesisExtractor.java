package extractor;

import org.apache.commons.lang3.Range;

import utils.PtbUtils;
import annotation.ISimpAnnotations.ParenthesisAnnotation;
import annotation.ISimpAnnotations.ParenthesisElemAnnotation;
import annotation.ISimpAnnotations.ParenthesisRefAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

public class ParenthesisExtractor extends ISimpExtractor {

  public ParenthesisExtractor() {
    super(ParenthesisAnnotation.class);
  }

  @Override
  protected SimplificationConstruct annotate(Tree tree, int index) {
    Range<Integer> npRange = null;
    Range<Integer> clRange = null;
    SimplificationConstruct construct = new SimplificationConstruct(
        new ParenthesisAnnotation());

    for (Tree child : tree) {
      CoreLabel label = (CoreLabel) child.label();
      // ref
      Integer subindex = label.get(ParenthesisRefAnnotation.class);
      if (subindex != null && subindex == index) {
        npRange = PtbUtils.getRange(child);
        construct.addComponent(new ParenthesisRefAnnotation(), npRange);
      }
      // elements
      subindex = label.get(ParenthesisElemAnnotation.class);
      if (subindex != null && subindex == index) {
        clRange = PtbUtils.getRange(child);
        construct.addComponent(new ParenthesisElemAnnotation(), clRange);
        break;
      }
    }
    if (npRange == null) {
      throw new RuntimeException(String.format(
          "can not find PARREF: %s",
            tree.toString()));
    }
    if (clRange == null) {
      throw new RuntimeException(String.format(
          "can not find ELEMENTS: %s",
            tree.toString()));
    }
    return construct;
  }
}
