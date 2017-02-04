package extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import annotation.ISimpAnnotations.ISimpAnnotation;
import annotator.ISimpAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap.Key;

public abstract class ISimpExtractor implements Annotator {

  public static final Requirement         ISMP_CONSTRUCT = new Requirement(
                                                             "isimp tree annotation");

  protected Class<? extends Key<Integer>> annotationKey;

  protected ISimpExtractor(Class<? extends Key<Integer>> annotationKey) {
    this.annotationKey = annotationKey;
  }

  @Override
  public Set<Requirement> requirementsSatisfied() {
    return Collections.singleton(ISMP_CONSTRUCT);
  }

  @Override
  public Set<Requirement> requires() {
    Set<Requirement> set = new TreeSet<Requirement>();
    set.addAll(TOKENIZE_SSPLIT_PARSE);
    set.add(ISimpAnnotator.ISMP_TREE);
    return set;
  }

  @Override
  public final void annotate(Annotation annotation) {
    if (annotation.containsKey(SentencesAnnotation.class)) {
      for (CoreMap sentence : annotation.get(SentencesAnnotation.class)) {
        if (sentence.containsKey(TreeAnnotation.class)) {
          Tree root = sentence.get(TreeAnnotation.class);
          List<SimplificationConstruct> list = annotate(root);
          if (!sentence.containsKey(ISimpAnnotation.class)) {
            sentence.set(ISimpAnnotation.class, list);
          } else {
            sentence.get(ISimpAnnotation.class).addAll(list);
          }
        } else {
          throw new RuntimeException("unable to find tree in: "
              + sentence.get(TextAnnotation.class));
        }
      }
    } else {
      throw new RuntimeException("unable to find sentences in: " + annotation);
    }
  }

  public final List<SimplificationConstruct> annotate(Tree tree) {
    List<SimplificationConstruct> listlist = new ArrayList<SimplificationConstruct>();

    for (Tree child : tree) {
      // parent
      CoreLabel label = (CoreLabel) child.label();
      Integer index = label.get(annotationKey);
      if (index != null) {
        listlist.add(annotate(child, index));
      }
    }
    return listlist;
  }

  protected abstract SimplificationConstruct annotate(Tree tree, int index);
}
