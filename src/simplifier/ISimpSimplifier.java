package simplifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import annotation.ISimpAnnotations.ISimpSimplifiedAnnotation;
import annotator.ISimpAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Filter;
import edu.stanford.nlp.util.TypesafeMap.Key;

public abstract class ISimpSimplifier implements Annotator {

  public static final Requirement         ISMP_SIMPLIFIED = new Requirement(
                                                              "isimp simplified tree");

  protected Class<? extends Key<Integer>> annotationKey;

  protected ISimpSimplifier(Class<? extends Key<Integer>> annotationKey) {
    this.annotationKey = annotationKey;
  }

  @Override
  public Set<Requirement> requirementsSatisfied() {
    return Collections.singleton(ISMP_SIMPLIFIED);
  }

  @Override
  public Set<Requirement> requires() {
    Set<Requirement> set = new TreeSet<Requirement>();
    set.addAll(TOKENIZE_SSPLIT_PARSE);
    set.add(ISimpAnnotator.ISMP_TREE);
    return set;
  }

  protected static final TregexPattern   tregex   = TregexPattern
                                                      .compile("/###/=par << /@@@/=child");
  protected static final TsurgeonPattern tsurgeon = Tsurgeon
                                                      .parseOperation("replace par child");

  @Override
  public final void annotate(Annotation annotation) {
    if (annotation.containsKey(SentencesAnnotation.class)) {
      for (CoreMap sentence : annotation.get(SentencesAnnotation.class)) {
        if (sentence.containsKey(TreeAnnotation.class)) {
          Tree root = sentence.get(TreeAnnotation.class);
          List<Tree> list = annotate(root);
          if (!sentence.containsKey(ISimpSimplifiedAnnotation.class)) {
            sentence.set(ISimpSimplifiedAnnotation.class, list);
          } else {
            sentence.get(ISimpSimplifiedAnnotation.class).addAll(list);
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

  public final List<Tree> annotate(Tree tree) {
    List<Tree> listlist = new ArrayList<Tree>();

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

  /**
   * Attempts to find the first node that matches the pattern.
   * 
   * @param tree
   * @param index
   * @return
   */
  protected abstract Tree annotate(Tree tree, int index);

  protected static class TreeFilter implements Filter<Tree> {

    List<Tree> trees;

    TreeFilter(Tree... ts) {
      trees = new ArrayList<Tree>();
      for (Tree t : ts) {
        trees.add(t);
      }
    }

    @Override
    public boolean accept(Tree tree) {
      for (Tree t : trees) {
        // only check reference
        if (t == tree) {
          return true;
        }
      }
      return false;
    }

  }
}
