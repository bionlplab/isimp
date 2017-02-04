package utils;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

public class PtbWriter {

  public static final LabelWriter DEFAULT = new LabelWriter() {

                                            @Override
                                            public String labelString(Tree t) {
                                              return t.label().toString();
                                            }
                                          };

  public static final LabelWriter FULL    = new DetailedLabelWriter();

  public static String print(Tree t, LabelWriter labelPrinter) {
    return printHelper(new StringBuilder(), t, labelPrinter).toString();
  }

  private static StringBuilder printHelper(StringBuilder sb, Tree t,
      LabelWriter labelPrinter) {
    if (t.isLeaf()) {
      return sb.append(labelPrinter.labelString(t));
    } else {
      sb.append('(');
      if (t.label() != null) {
        sb.append(labelPrinter.labelString(t));
      }
      Tree[] kids = t.children();
      if (kids != null) {
        for (Tree kid : kids) {
          sb.append(' ');
          printHelper(sb, kid, labelPrinter);
        }
      }
      return sb.append(')');
    }
  }
}

interface LabelWriter {

  public String labelString(Tree t);
}

class DetailedLabelWriter implements LabelWriter {

  @Override
  public String labelString(Tree t) {

    CoreLabel label = (CoreLabel) t.label();
    StringBuilder sb = new StringBuilder();

    if (t.isLeaf()) {
      sb.append(label.value()).append('_').append(label.beginPosition())
          .append('_').append(label.endPosition());
    } else if (t.isPreTerminal()) {
      sb.append(label.tag());
    } else {
      sb.append(label.category());
    }
    return sb.toString();
  }
}
