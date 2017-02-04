package adapter;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class AdapterPattern {

  private final TregexPattern   tregexPattern;
  private final TsurgeonPattern tsurgeonPattern;

  public AdapterPattern(
      TregexPattern tregexPattern,
      TsurgeonPattern tsurgeonPattern) {
    this.tregexPattern = tregexPattern;
    this.tsurgeonPattern = tsurgeonPattern;
  }

  public final boolean evaluate(Tree tree) {
    boolean matched = false;
    TregexMatcher m = tregexPattern.matcher(tree);
    if (m.find()) {
      matched = true;
      tsurgeonPattern.evaluate(tree, m);
    }
    return matched;
  }

  public final TregexPattern getTregexPattern() {
    return tregexPattern;
  }

  public final TsurgeonPattern getTsurgeonPattern() {
    return tsurgeonPattern;
  }

  @Override
  public String toString() {
    return "[pattern=" + tregexPattern + ",operation=" + tsurgeonPattern + "]";
  }
}
