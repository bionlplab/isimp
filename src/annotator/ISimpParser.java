//package annotator;
//
//import java.util.List;
//import java.util.Properties;
//
//import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
//import edu.stanford.nlp.pipeline.Annotation;
//import edu.stanford.nlp.pipeline.Annotator;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.trees.MemoryTreebank;
//import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
//import edu.stanford.nlp.trees.Treebank;
//import edu.stanford.nlp.util.CoreMap;
//
//public class ISimpParser extends StanfordCoreNLP {
//
//  public static final Properties DEFAULT_PROPS = new Properties();
//  // public static final Properties PROPS_WITHOUT_TOKENZIE = new Properties();
//
//  static {
//    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER,
//    // parsing, and coreference resolution
//    DEFAULT_PROPS.put("annotators", "tokenize, ssplit, pos, parse");
//    DEFAULT_PROPS.put("tokenize.options", "americanize=false,"
//        + "normalizeCurrency=false,"
//        + "normalizeFractions=false,"
//        // + "normalizeParentheses=false,"
//        // + "normalizeOtherBrackets=false,"
//        // + "asciiQuotes=false,"
//        // + "latexQuotes=false,"
//        + "untokenizable=allKeep,"
//        + "ptb3Dashes=false,"
//        + "escapeForwardSlashAsterisk=false,"
//        + "ssplit.eolonly=true");
//    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER,
//    // parsing, and coreference resolution
//    // PROPS_WITHOUT_TOKENZIE.put("annotators",
//    // "tokenize, ssplit, pos, parse");
//    // PROPS_WITHOUT_TOKENZIE.put("tokenized", "true");
//  }
//
//  public ISimpParser() {
//    super(DEFAULT_PROPS);
//  }
//
//  public ISimpParser(Properties props) {
//    super(props);
//  }
//
//  public static class ISimpParserBuilder {
//
//    private boolean isTokenized;
//    private boolean isSSplitted;
//
//    public ISimpParserBuilder() {
//      isTokenized = false;
//      isSSplitted = false;
//    }
//
//    /**
//     * if set , separates words only when whitespace is encountered.
//     * 
//     * @return
//     */
//    public ISimpParserBuilder setTokenized() {
//      isTokenized = true;
//      return this;
//    }
//
//    /**
//     * if set, only splits sentences on newlines.
//     * 
//     * @return
//     */
//    public ISimpParserBuilder setSSplitted() {
//      isSSplitted = true;
//      return this;
//    }
//
//    public Annotator create() {
//      Properties props = new Properties();
//      props.put("annotators", "tokenize, ssplit, pos, parse");
//      if (isTokenized && isSSplitted) {
//        props.put("tokenize.whitespace", "true");
//        props.put("ssplit.eolonly", "true");
//      } else if (isTokenized && !isSSplitted) {
//
//      } else if (!isTokenized && isSSplitted) {
//        props.put("tokenize.options", "americanize=false,"
//            + "normalizeCurrency=false,"
//            + "normalizeFractions=false,"
//            // + "normalizeParentheses=false,"
//            // + "normalizeOtherBrackets=false,"
//            // + "asciiQuotes=false,"
//            // + "latexQuotes=false,"
//            + "untokenizable=allKeep,"
//            + "ptb3Dashes=false,"
//            + "escapeForwardSlashAsterisk=false");
//        props.put("ssplit.eolonly", "true");
//      } else if (!isTokenized && !isSSplitted) {
//        props.put("tokenize.options", "americanize=false,"
//            + "normalizeCurrency=false,"
//            + "normalizeFractions=false,"
//            // + "normalizeParentheses=false,"
//            // + "normalizeOtherBrackets=false,"
//            // + "asciiQuotes=false,"
//            // + "latexQuotes=false,"
//            + "untokenizable=allKeep,"
//            + "ptb3Dashes=false,"
//            + "escapeForwardSlashAsterisk=false");
//      }
//
//      return new ISimpParser(props);
//    }
//  }
//
//  public Treebank parse(String text) {
//    Treebank treebank = new MemoryTreebank();
//    Annotation document = new Annotation(text);
//    annotate(document);
//    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//    for (CoreMap sentence : sentences) {
//      Tree tree = sentence.get(TreeAnnotation.class);
//      treebank.add(tree);
//    }
//    return treebank;
//  }
//}
