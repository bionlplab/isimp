package annotator;

import java.util.Collections;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public abstract class ISimpAnnotator implements Annotator {

  public static final Requirement ISMP_TREE = new Requirement(
                                                "isimp tree annotation");

  @Override
  public Set<Requirement> requirementsSatisfied() {
    return Collections.singleton(ISMP_TREE);
  }

  @Override
  public Set<Requirement> requires() {
    return TOKENIZE_SSPLIT_PARSE;
  }

  @Override
  public final void annotate(Annotation annotation) {

    if (annotation.containsKey(SentencesAnnotation.class)) {
      for (CoreMap sentence : annotation.get(SentencesAnnotation.class)) {
        if (sentence.containsKey(TreeAnnotation.class)) {
          Tree root = sentence.get(TreeAnnotation.class);
          annotate(root);
        } else {
          throw new RuntimeException("unable to find tree in: "
              + sentence.get(TextAnnotation.class));
        }
      }
    } else {
      throw new RuntimeException("unable to find sentences in: " + annotation);
    }
  }

  protected abstract void annotate(Tree t);
}
