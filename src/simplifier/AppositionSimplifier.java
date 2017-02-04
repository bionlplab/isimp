// package simplifier;
//
// import java.util.Collections;
//
// import annotation.ISimpAnnotations.AppositionAnnotation;
// import annotation.ISimpAnnotations.AppositionAppositiveAnnotation;
// import annotation.ISimpAnnotations.AppositionRefAnnotation;
// import edu.stanford.nlp.ling.CoreLabel;
// import edu.stanford.nlp.trees.Tree;
//
// class AppositionSimplifier extends ISimpSimplifier {
//
// protected AppositionSimplifier(Tree root) {
// super(AppositionAnnotation.class);
// }
//
// @Override
// protected Tree annotate(Tree tree, int index) {
//
// Tree copiedTree = tree.deepCopy();
// Tree t1 = retainNp(copiedTree, index);
//
// copiedTree = tree.deepCopy();
// Tree t2 = retainCl(copiedTree, index);
//
// // clean this
//
// Tree copiedPar = null;
// Tree copiedNp = null;
// Tree copiedCl = null;
// // remove ref
// Tree copiedTree = tree.deepCopy();
// for (Tree child : copiedTree) {
// CoreLabel label = (CoreLabel) child.label();
// Integer subindex = label.get(AppositionAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAnnotation.class, null);
// copiedPar = child;
// }
// // ref
// subindex = label.get(AppositionRefAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionRefAnnotation.class, null);
// copiedNp = child;
// }
// // appositive
// subindex = label.get(AppositionAppositiveAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAppositiveAnnotation.class, null);
// copiedCl = child;
// break;
// }
// }
// if (copiedPar == null) {
// throw new RuntimeException(String.format(
// "can not find APP: %s",
// tree.toString()));
// }
// if (copiedNp == null) {
// throw new RuntimeException(String.format(
// "can not find APPREF: %s",
// tree.toString()));
// }
// if (copiedCl == null) {
// throw new RuntimeException(String.format(
// "can not find APPOSITIVE: %s",
// tree.toString()));
// }
// copiedTree.prune(new TreeFilter(copiedNp));
//
// // remove appositive
// copiedTree = tree.deepCopy();
// for (Tree child : copiedTree) {
// CoreLabel label = (CoreLabel) child.label();
// // ref
// Integer subindex = label.get(AppositionAppositiveAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAppositiveAnnotation.class, null);
// copiedCl = child;
// }
// }
// if (copiedCl == null) {
// throw new RuntimeException(String.format(
// "can not find APPREF: %s",
// tree.toString()));
// }
// copiedTree.prune(new TreeFilter(copiedCl));
//
// // find
// for (Tree child : copiedTree) {
// CoreLabel label = (CoreLabel) child.label();
// Integer subindex = label.get(AppositionAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAnnotation.class, null);
// copiedPar = child;
// }
// // ref
// subindex = label.get(AppositionRefAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionRefAnnotation.class, null);
// copiedNp = child;
// }
// // appositive
// subindex = label.get(AppositionAppositiveAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAppositiveAnnotation.class, null);
// copiedCl = child;
// break;
// }
// }
// if (copiedPar == null) {
// throw new RuntimeException(String.format(
// "can not find APP: %s",
// tree.toString()));
// }
// if (copiedNp == null) {
// throw new RuntimeException(String.format(
// "can not find APPREF: %s",
// tree.toString()));
// }
// if (copiedCl == null) {
// throw new RuntimeException(String.format(
// "can not find APPOSITIVE: %s",
// tree.toString()));
// }
//
// // simplify
// while (copiedPar.numChildren() != 0) {
// copiedPar.removeChild(0);
// }
// copiedPar.addChild(copiedNp);
// // add "is"
// Tree is = copiedPar.treeFactory().newLeaf("is");
// Tree vbz = copiedPar.treeFactory().newTreeNode(
// "VBZ",
// Collections.singletonList(is));
// copiedPar.addChild(vbz);
// copiedPar.addChild(copiedCl);
//
// return copiedPar;
// }
//
// private Tree retainCl(Tree tree, int index) {
// Tree par = null;
// Tree np = null;
// Tree cl = null;
// // remove ref
// for (Tree child : tree) {
// CoreLabel label = (CoreLabel) child.label();
// Integer subindex = label.get(AppositionAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAnnotation.class, null);
// par = child;
// }
// // ref
// subindex = label.get(AppositionRefAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionRefAnnotation.class, null);
// np = child;
// }
// // appositive
// subindex = label.get(AppositionAppositiveAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAppositiveAnnotation.class, null);
// cl = child;
// break;
// }
// }
// if (par == null) {
// throw new RuntimeException(String.format(
// "can not find APP: %s",
// tree.toString()));
// }
// if (np == null) {
// throw new RuntimeException(String.format(
// "can not find APPREF: %s",
// tree.toString()));
// }
// if (cl == null) {
// throw new RuntimeException(String.format(
// "can not find APPOSITIVE: %s",
// tree.toString()));
// }
// while (par.numChildren() != 0) {
// par.removeChild(0);
// }
// par.addChild(cl);
// return tree;
// }
//
// private Tree retainNp(Tree tree, int index) {
// Tree par = null;
// Tree np = null;
// Tree cl = null;
// // remove ref
// for (Tree child : tree) {
// CoreLabel label = (CoreLabel) child.label();
// Integer subindex = label.get(AppositionAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAnnotation.class, null);
// par = child;
// }
// // ref
// subindex = label.get(AppositionRefAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionRefAnnotation.class, null);
// np = child;
// }
// // appositive
// subindex = label.get(AppositionAppositiveAnnotation.class);
// if (subindex != null && subindex == index) {
// label.set(AppositionAppositiveAnnotation.class, null);
// cl = child;
// break;
// }
// }
// if (par == null) {
// throw new RuntimeException(String.format(
// "can not find APP: %s",
// tree.toString()));
// }
// if (np == null) {
// throw new RuntimeException(String.format(
// "can not find APPREF: %s",
// tree.toString()));
// }
// if (cl == null) {
// throw new RuntimeException(String.format(
// "can not find APPOSITIVE: %s",
// tree.toString()));
// }
// while (par.numChildren() != 0) {
// par.removeChild(0);
// }
// par.addChild(np);
// return tree;
// }
// }
