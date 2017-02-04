package annotator.coordination;

import java.util.List;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class RightMostVp {

  private static TregexPattern p1 = TregexPattern
                                      .compile("VP=p <1 @/VB.*/ <2 (S=s <: VP=vp) <- =s");
  private static TregexPattern p2 = TregexPattern
                                      .compile("ADJP=p <1 @/JJ/ <2 (S=s <: VP=vp) <- =s");

  public static Tree rightMostVp(Tree vp) {
    TregexMatcher m1 = p1.matcher(vp);
    if (find(m1, vp)) {
      return rightMostVp(m1.getNode("vp"));
    }

    List<Tree> children = vp.getChildrenAsList();
    for (int i = children.size() - 1; i >= 0; i--) {
      Tree child = children.get(i);
      if (child.value().startsWith("VP")) {
        return rightMostVp(child);
      }
      if (child.value().startsWith("ADJP")) {
        Tree foundVp = rightMostAdjp(child);
        if (foundVp != null) {
          return foundVp;
        }
      }
    }
    return vp;
  }

  private static Tree rightMostAdjp(Tree adjp) {
    TregexMatcher m1 = p2.matcher(adjp);
    if (find(m1, adjp)) {
      return rightMostVp(m1.getNode("vp"));
    }
    return null;
  }

  private static boolean find(TregexMatcher m, Tree vp) {
    return m.find() && m.getNode("p") == vp;
  }
}
