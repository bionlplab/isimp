package extractor;

import org.apache.commons.lang3.Range;

import utils.PtbUtils;
import annotation.ISimpAnnotations.AppositionAnnotation;
import annotation.ISimpAnnotations.AppositionAppositiveAnnotation;
import annotation.ISimpAnnotations.AppositionRefAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

public class AppositionExtractor extends ISimpExtractor {

  public AppositionExtractor() {
    super(AppositionAnnotation.class);
  }

  @Override
  protected SimplificationConstruct annotate(Tree tree, int index) {

    Range<Integer> npRange = null;
    Range<Integer> clRange = null;
    SimplificationConstruct construct = new SimplificationConstruct(
        new AppositionAnnotation());

   for(Tree child: tree) {
      CoreLabel label = (CoreLabel) child.label();
      // ref
      Integer subindex = label.get(AppositionRefAnnotation.class);
      if (subindex != null && subindex == index) {
        npRange = PtbUtils.getRange(child);
        construct.addComponent(new AppositionRefAnnotation(), npRange);
      }
      // appositive
      subindex = label.get(AppositionAppositiveAnnotation.class);
      if (subindex != null && subindex == index) {
        clRange = PtbUtils.getRange(child);
        construct.addComponent(new AppositionAppositiveAnnotation(), clRange);
        break;
      }
    }
    if (npRange == null) {
      throw new RuntimeException(String.format(
          "can not find APPREF: %s",
          tree.toString()));
    }
    if (clRange == null) {
      throw new RuntimeException(String.format(
          "can not find APPOSITIVE: %s",
          tree.toString()));
    }
    return construct;
  }
}
