package utils;


public class Utils {

  public static String handleRegStr(String s) {
    s = s.replaceAll("\\(", "\\\\(");
    s = s.replaceAll("\\)", "\\\\)");
    s = s.replaceAll("\\[", "\\\\[");
    s = s.replaceAll("\\]", "\\\\]");
    s = s.replaceAll("[.]", "[.]");
    s = s.replaceAll("[-]", "[-]");
    return s;
  }

  public static String adaptValue(String value) {
    value = value.replace("-LRB-", "(");
    value = value.replace("-RRB-", ")");
    value = value.replace("-LSB-", "[");
    value = value.replace("-RSB-", "]");
    value = value.replace("-lrb-", "(");
    value = value.replace("-rrb-", ")");
    value = value.replace("-lsb-", "[");
    value = value.replace("-rsb-", "]");
    value = value.replace("``", "\"");
    value = value.replace("`", "'");
    value = value.replace("''", "\"");
    value = value.replace("\\/", "/");
    return value;
  }

  // public static boolean isIntersected(int from1, int to1, int from2, int
  // to2) {
  // Range<Integer> r1 = Range.between(from1, to1);
  // Range<Integer> r2 = Range.between(from2, to2);
  // return r1.isOverlappedBy(r2);
  // }
  //
  // /**
  // * [from2, to2] in [from1, to1]
  // *
  // * @param from1
  // * @param to1
  // * @param from2
  // * @param to2
  // * @return
  // */
  // public static boolean includes(int from1, int to1, int from2, int to2) {
  // Range<Integer> r1 = Range.between(from1, to1);
  // Range<Integer> r2 = Range.between(from2, to2);
  // return r1.containsRange(r2);
  // }
}
