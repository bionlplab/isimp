// package simplifier;
//
// import java.util.Iterator;
//
// import utils.PtbUtils;
// import edu.stanford.nlp.trees.LabeledScoredTreeFactory;
// import edu.stanford.nlp.trees.Tree;
// import edu.stanford.nlp.trees.tregex.TregexMatcher;
//
// class RelativeClauseSimplifier extends ISimpSimplifier {
//
// protected RelativeClauseSimplifier(Tree root) {
// super(root);
// }
//
// @Override
// public boolean find() {
// for (Tree child : root.preOrderNodeList()) {
// int index = PtbUtils.getSimpIndex(child.value(), "-RELATIVE");
// if (index != -1) {
// simplify(child, index);
// return true;
// }
// }
// return false;
// }
//
// protected void simplify(Tree par, int index) {
//
// // change value
// par.setValue(par.value() + "-###");
// // simplify
// Iterator<Tree> itr = par.iterator();
// while (itr.hasNext()) {
// Tree child = itr.next();
// int subindex = PtbUtils.getSimpIndex(child.value(), "-RELREF");
// if (subindex != -1 && subindex == index) {
// // change value
// child.setValue(child.value() + "-@@@");
// // copy
// Tree copied = root.deepCopy();
// TregexMatcher m = tregex.matcher(copied);
// boolean found = m.find();
// if (!found) {
// throw new IllegalArgumentException("no matching found:" + copied);
// }
// // restore child in copied
// Tree copiedChild = m.getNode("child");
// copiedChild.setValue(copiedChild.value().substring(
// 0,
// copiedChild.value().length() - 4));
// Tree copiedPar = m.getNode("par");
// while (copiedPar.numChildren() != 0) {
// copiedPar.removeChild(0);
// }
// copiedPar.addChild(copiedChild);
// copiedChild.setValue(copiedChild.value().replace("-RELREF"+index, ""));
// copiedPar.setValue(copiedPar.value().replace("-RELATIVE"+index, ""));
//
// // tsurgeon.evaluate(copied, m);
//
// simplifiedTrees.add(copied);
// // restore
// child.setValue(child.value().substring(0, child.value().length() - 4));
// }
// }
// // ref clause
// Tree ref = null;
// Tree clause = null;
// itr = par.iterator();
// while (itr.hasNext()) {
// Tree child = itr.next();
// int subindex = PtbUtils.getSimpIndex(child.value(), "-RELREF");
// if (subindex != -1 && subindex == index) {
// ref = child;
// }
// subindex = PtbUtils.getSimpIndex(child.value(), "-CLAUSE");
// if (subindex != -1 && subindex == index) {
// clause = child;
// }
// }
// if (ref == null || clause == null) {
// throw new IllegalArgumentException("no matching found:\n"
// + root
// + "\nindex:"
// + index);
// }
// Tree newRoot = new LabeledScoredTreeFactory().newLeaf("ROOT");
// Tree newS = new LabeledScoredTreeFactory().newLeaf("S");
// newRoot.addChild(newS);
// newS.addChild(ref.deepCopy());
// newS.addChild(clause.deepCopy());
// simplifiedTrees.add(newRoot);
// // restore
// par.setValue(par.value().substring(0, par.value().length() - 4));
// }
// }
