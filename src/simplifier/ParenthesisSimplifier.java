// package simplifier;
//
// import utils.PtbUtils;
// import annotation.ISimpAnnotations.ParenthesisAnnotation;
// import annotation.ISimpAnnotations.ParenthesisElemAnnotation;
// import annotation.ISimpAnnotations.ParenthesisRefAnnotation;
// import edu.stanford.nlp.ling.CoreLabel;
// import edu.stanford.nlp.trees.Tree;
// import edu.stanford.nlp.trees.tregex.TregexMatcher;
//
// class ParenthesisSimplifier extends ISimpSimplifier {
//
// protected ParenthesisSimplifier() {
// super(ParenthesisAnnotation.class);
// }
//
// @Override
// protected Tree annotate(Tree tree, int index) {
//
// Tree copiedTree = tree.deepCopy();
// Tree copiedPar = null;
// Tree copiedNp = null;
// Tree copiedCl = null;
// // find
// for (Tree child : copiedTree) {
// CoreLabel label = (CoreLabel) child.label();
// Integer subindex = label.get(ParenthesisAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(ParenthesisAnnotation.class, null);
// copiedPar = child;
// }
// // ref
// subindex = label.get(ParenthesisRefAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(ParenthesisRefAnnotation.class, null);
// copiedNp = child;
// }
// // appositive
// subindex = label.get(ParenthesisElemAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(ParenthesisElemAnnotation.class, null);
// copiedCl = child;
// break;
// }
// }
// if (copiedPar == null) {
// throw new RuntimeException(String.format(
// "can not find PAR: %s",
// tree.toString()));
// }
// if (copiedNp == null) {
// throw new RuntimeException(String.format(
// "can not find PARREF: %s",
// tree.toString()));
// }
// if (copiedCl == null) {
// throw new RuntimeException(String.format(
// "can not find ELEMENTS: %s",
// tree.toString()));
// }
// // only ref
// c
//
// while (itr.hasNext()) {
// Tree child = itr.next();
// int subindex = PtbUtils.getSimpIndex(child.value(), "-PARREF");
// if (subindex == -1 || subindex != index) {
// subindex = PtbUtils.getSimpIndex(child.value(), "-ELEMENTS");
// }
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
// copiedChild
// .setValue(copiedChild.value().replace("-PARREF" + index, ""));
// copiedChild.setValue(copiedChild.value().replace(
// "-ELEMENTS" + index,
// ""));
// copiedPar.setValue(copiedPar.value()
// .replace("-PARENTHESIS" + index, ""));
// // tsurgeon.evaluate(copied, m);
//
// simplifiedTrees.add(copied);
// // restore
// child.setValue(child.value().substring(0, child.value().length() - 4));
// }
// }
// // restore
// par.setValue(par.value().substring(0, par.value().length() - 4));
// }
// }
