package annotator.coordination;

import java.util.LinkedList;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.Pair;

public abstract class Coordination {

  protected static final TregexPattern   conjPattern = TregexPattern
                                                         .compile("__ < CC|CONJP");
  public LinkedList<Pair<Integer, Tree>> conjuncts;
  public Tree                            conjunction;
  public Tree                            root;
  public Tree                            coordination;

  public Coordination(Tree root, Tree coordination) {
    this.root = root;
    this.coordination = coordination;
    conjuncts = new LinkedList<Pair<Integer, Tree>>();
  }

  public abstract boolean isCoordination();

  protected boolean isCC(Tree t) {
    return t.value().equals("CC") || t.value().equals("CONJP");
  }

  protected boolean isComma(Tree t) {
    return t.value().equals(",")
        || t.value().equals(".")
        || t.value().equals(";")
        || t.value().equals(":");
  }

}
