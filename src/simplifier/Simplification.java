// package simplifier;
//
// import java.util.HashSet;
// import java.util.LinkedList;
// import java.util.Queue;
// import java.util.Set;
//
// import utils.PtbUtils;
// import edu.stanford.nlp.trees.MemoryTreebank;
// import edu.stanford.nlp.trees.Tree;
// import edu.stanford.nlp.trees.Treebank;
//
// public class Simplification {
//
// public static Treebank simplify(Tree tree) {
// Treebank treebank = new MemoryTreebank();
//
// Set<String> simplifiedSentences = new HashSet<String>();
//
// Queue<Tree> queue = new LinkedList<Tree>();
// queue.offer(tree);
//
// int types[] = new int[] { //
// ISimpSimplifier.Parenthesis, //
// ISimpSimplifier.Coordination, //
// ISimpSimplifier.Relative, //
// ISimpSimplifier.Apposition,//
// };
//
// while (!queue.isEmpty()) {
// Tree t = queue.poll();
// boolean found = false;
// for (int i = 0; i < types.length; i++) {
// ISimpSimplifier simplifier = ISimpSimplifier.matcher(t, types[i]);
// if (simplifier.find()) {
// found = true;
// queue.addAll(simplifier.getSimplifiedTrees());
// break;
// }
// }
// if (!found) {
// String sentence = PtbUtils.lineString2(t);
// if (!simplifiedSentences.contains(sentence)) {
// treebank.add(t);
// simplifiedSentences.add(sentence);
// }
// }
// }
//
// return treebank;
// }
// }
