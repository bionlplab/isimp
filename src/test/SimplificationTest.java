// package test;
//
// import java.util.List;
//
// import org.junit.Test;
//
// import simplifier.Simplification;
// import utils.PtbUtils;
// import adapter.OtherAdapter;
// import detect.Detection;
// import edu.stanford.nlp.trees.Tree;
// import edu.stanford.nlp.trees.Treebank;
// import edu.stanford.nlp.util.Pair;
// import edu.stanford.nlp.util.Triple;
// import extractor.Extraction;
//
// public class SimplificationTest {
//
// @Test
// public void test() {
// Treebank treebank = TreebankReader.readTreebank("testcases/foo1.txt");
//
// for (Tree tree : treebank) {
// OtherAdapter.adapt(tree);
// Detection.detect(tree);
// System.out.println(tree.pennString());
//
// List<Pair<String, List<Triple<String, Integer, Integer>>>> listlist =
// Extraction
// .extract(tree);
//
// StringBuilder sb = new StringBuilder();
// for (Pair<String, List<Triple<String, Integer, Integer>>> p : listlist) {
// sb.append("[name=" + p.first);
// for (Triple<String, Integer, Integer> t : p.second) {
// sb.append(String.format(
// ",name=%s,from=%d,to=%d",
// t.first,
// t.second,
// t.third));
// }
// sb.append(",\n   text=");
// // line
// List<Tree> leaves = tree.getLeaves();
// for (Triple<String, Integer, Integer> t : p.second) {
// // find leaf
// for(Tree leaf: leaves) {
// Triple<String, Integer, Integer> tri = PtbUtils.parseValue(leaf.value());
// if (t.second <= tri.second && tri.third <= t.third) {
// sb.append(tri.first + " ");
// }
// }
// }
// sb.append("]\n");
// }
// System.out.println(sb);
//
// // simplified
// Treebank simplified = Simplification.simplify(tree);
// for(Tree stree: simplified) {
// System.out.println(PtbUtils.lineString2(stree));
// }
// }
// }
//
//
// }
