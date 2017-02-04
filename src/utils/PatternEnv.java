package utils;

public class PatternEnv {

  public static String DIR;

  static {
    String path = PatternEnv.class.getProtectionDomain().getCodeSource()
        .getLocation().getPath();
    System.out.println(PatternEnv.class.getName() + " is loaded from " + path);
    DIR = path + "../rules/";
  }

  // simplification
  public static String COORDINATION_TREGEX        = "coordination-detect.txt";
  public static String RELATIVE_TREGEX            = "relative clause-detect.txt";
  public static String PARENTHESIS_TREGEX         = "parenthesis-detect.txt";
  public static String APPOSITION_TREGEX          = "apposition-detect.txt";
  public static String MEMEMBER_COLLECTION_TREGEX = "member-collection-detect.txt";
  public static String NPPP_TREGEX                = "nppp.txt";
  public static String SENBEG_TREGEX              = "sentenceBeginning.txt";
  public static String HYPERNYMY_TREGEX           = "hypernymy-detect.txt";
  public static String OTHERS_TREGEX              = "others.txt";
  // adapter
  public static String PAR_ADATER_TREGEX          = "parenthesis-adapter.txt";
  public static String REL_ADATER_TREGEX          = "relative clause-adapter.txt";
  public static String COO_ADATER_TREGEX          = "coordination-adapter.txt";
  public static String ADATER_TREGEX              = "adapter.txt";
}
