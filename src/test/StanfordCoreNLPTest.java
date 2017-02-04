package test;

import java.util.List;
import java.util.Properties;

import main.SentencePrinter;
import main.SentencePrinterBuilder;

import org.junit.Before;
import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordCoreNLPTest {

  StanfordCoreNLP stanford;

  @Before
  public void init() {
    Properties DEFAULT_PROPS = new Properties();
    // public static final Properties PROPS_WITHOUT_TOKENZIE = new
    // Properties();

    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER,
    // parsing, and coreference resolution
    DEFAULT_PROPS.put("annotators", "tokenize, ssplit, pos, parse");
    DEFAULT_PROPS.put("tokenize.options", "americanize=false,"
        + "normalizeCurrency=false,"
        + "normalizeFractions=false,"
        // + "normalizeParentheses=false,"
        // + "normalizeOtherBrackets=false,"
        // + "asciiQuotes=false,"
        // + "latexQuotes=false,"
        + "untokenizable=allKeep,"
        + "ptb3Dashes=false,"
        + "escapeForwardSlashAsterisk=false,"
        + "tokenizeNLs=true");
    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER,
    // parsing, and coreference resolution
    Properties PROPS_WITHOUT_TOKENZIE = new Properties();
    PROPS_WITHOUT_TOKENZIE.put("annotators", "tokenize, ssplit, pos, parse");
    PROPS_WITHOUT_TOKENZIE.put("tokenize.whitespace", "true");
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
//        + "tokenizeNLs=true");
//    PROPS_WITHOUT_TOKENZIE.put("ssplit.eolonly", "true");
    stanford = new StanfordCoreNLP(PROPS_WITHOUT_TOKENZIE);
  }

  @Test
  public void test() {
    Annotation document = new Annotation(
        "UDEL is a universities, such as UPENN and CMU. UDEL is a universities, such as UPENN and CMU.");
    stanford.annotate(document);

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    SentencePrinter json = new SentencePrinterBuilder().setPrettyPrinting()
        .createPlain();

    for (CoreMap sentence : sentences) {
      System.out.println(json.get(sentence));
    }
  }

  @Test
  public void test2() {
    Annotation document = new Annotation(
        "UDEL is a universities, such as UPENN and CMU. \nUDEL is a universities, such as UPENN and CMU.");
    stanford.annotate(document);

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    SentencePrinter json = new SentencePrinterBuilder().setPrettyPrinting()
        .createPlain();

    for (CoreMap sentence : sentences) {
      System.out.println(json.get(sentence));
    }
  }
}
