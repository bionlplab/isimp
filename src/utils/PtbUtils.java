package utils;

import java.util.List;

import org.apache.commons.lang3.Range;

import edu.stanford.nlp.ling.HasOffset;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.Trees;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class PtbUtils {

  private static TregexPattern   inVivo1   = TregexPattern
                                               .compile("ADJP=p <1 FW <2 FW");
  private static TregexPattern   inVivo2   = TregexPattern
                                               .compile("PP=p <1 (IN <<: /in/) <2 (NP <<: /vitro/)");
  private static TsurgeonPattern inVivoOpt = Tsurgeon.parseOperation("prune p");

  public static void prune(Tree tree) {
    TregexMatcher m = inVivo1.matcher(tree);
    while (m.find()) {
      inVivoOpt.evaluate(tree, m);
    }
    m = inVivo2.matcher(tree);
    while (m.find()) {
      inVivoOpt.evaluate(tree, m);
    }
  }

  // public static Triple<String, Integer, Integer> parseValue(String value) {
  // int lastUnderline = value.lastIndexOf('_');
  // assert lastUnderline != -1 : value;
  // int to = Integer.parseInt(value.substring(lastUnderline + 1));
  //
  // int secondLastUnderline = value.lastIndexOf('_', lastUnderline - 1);
  // assert secondLastUnderline != -1 : value;
  // int from = Integer.parseInt(value.substring(
  // secondLastUnderline + 1,
  // lastUnderline));
  //
  // return new Triple<String, Integer, Integer>(value.substring(
  // 0,
  // secondLastUnderline), from, to);
  // }
  //
  // public static int maxDepth(Tree root) {
  // List<Tree> leaves = root.getLeaves();
  // List<Integer> depths = new ArrayList<Integer>();
  // for (Tree leaf : leaves) {
  // depths.add(root.depth(leaf));
  // }
  // return Collections.max(depths);
  // }

  public static Tree lastLeaf(Tree t) {
    List<Tree> leaves = t.getLeaves();
    return leaves.get(leaves.size() - 1);
  }

  public static Tree firstLeaf(Tree t) {
    return Trees.getLeaf(t, 0);
  }

  // public static String lineString(Tree t) {
  // StringBuilder sb = new StringBuilder();
  // List<Tree> leaves = t.getLeaves();
  // for (Tree leaf : leaves) {
  // sb.append(leaf.value() + " ");
  // }
  // return sb.substring(0, sb.length() - 1);
  // }
  //
  // /**
  // * <word, from, to>
  // * @param t
  // * @return
  // */
  // public static String lineString2(Tree t) {
  // StringBuilder sb = new StringBuilder();
  // List<Tree> leaves = t.getLeaves();
  // for (Tree leaf : leaves) {
  // sb.append(PtbUtils.parseValue(leaf.value()).first + " ");
  // }
  // return sb.substring(0, sb.length() - 1);
  // }
  //
  // public static int getSimpIndex(String value, String key) {
  // int from = value.indexOf(key);
  // if (from == -1) {
  // return -1;
  // }
  // from += key.length();
  // int to = value.indexOf('-', from);
  // if (to == -1) {
  // to = value.length();
  // }
  // int i = 0;
  // try {
  // i = Integer.parseInt(value.substring(from, to));
  // } catch(NumberFormatException e) {
  // System.err.println(value);
  // e.printStackTrace();
  // System.exit(1);
  // }
  // return i;
  // }

  public static Range<Integer> getRange(Tree tree) {
    Tree firstLeaf = firstLeaf(tree);
    Tree lastLeaf = lastLeaf(tree);
    return Range.between(
        ((HasOffset) firstLeaf.label()).beginPosition(),
          ((HasOffset) lastLeaf.label()).endPosition());
  }
}
