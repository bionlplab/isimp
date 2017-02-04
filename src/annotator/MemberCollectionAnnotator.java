package annotator;

import java.util.List;

import annotation.ISimpAnnotations.MemberCollectionAnnotation;
import annotation.ISimpAnnotations.MemberCollectionCollectionAnnotation;
import annotation.ISimpAnnotations.MemberCollectionMemberAnnotation;
import detect.DetectionPattern;
import detect.DetectionTregexReader;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;

public class MemberCollectionAnnotator extends ISimpAnnotator {

  @Override
  public void annotate(Tree root) {
    int index = 0;
    List<DetectionPattern> list = DetectionTregexReader
        .getTregex(DetectionTregexReader.MemberCollection);
    for (DetectionPattern p : list) {
      TregexMatcher m = p.getTregexPattern().matcher(root);
      while (m.find()) {
        Tree matched = m.getMatch();
        CoreLabel label = (CoreLabel) matched.label();
        if (label.get(MemberCollectionAnnotation.class) == null) {
          label.set(MemberCollectionAnnotation.class, index);
          // collection
          Tree ref = m.getNode("tr");
          label = (CoreLabel) ref.label();
          label.set(MemberCollectionCollectionAnnotation.class, index);
          // member
          Tree clause = m.getNode("arg");
          label = (CoreLabel) clause.label();
          label.set(MemberCollectionMemberAnnotation.class, index);
          index++;
        }
      }
    }

  }

}
