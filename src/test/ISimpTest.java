package test;

import java.util.List;

import main.SentencePrinter;
import main.SentencePrinterBuilder;

import org.junit.Ignore;
import org.junit.Test;

import detect.ISimp;
import detect.ISimpBuilder;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class ISimpTest {

  @Test
  @Ignore
  public void testDefault() {
    ISimpBuilder builder = new ISimpBuilder();
    ISimp isimp = builder.create();
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university. CMU is a university.");
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university. \nCMU is a university.");
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university . CMU is a university .");
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university . \nCMU is a university .");
  }

  @Test
  @Ignore
  public void testTokenized() {
    ISimpBuilder builder = new ISimpBuilder();
    ISimp isimp = builder.setTokenized().create();
    // UDEL is a university. CMU is a university.
    test(isimp, "UDEL is a university. CMU is a university.");
    // UDEL is a university. CMU is a university.
    test(isimp, "UDEL is a university. \nCMU is a university.");
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university . CMU is a university .");
    // UDEL is a university .
    // CMU is a university .
    test(isimp, "UDEL is a university . \nCMU is a university .");
  }

  private void test(ISimp isimp, String text) {
    Annotation document = new Annotation(text);
    isimp.annotate(document);

    System.out.println("INPUT:\n" + text);
    System.out.println("OUTPUT:");

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreMap token : sentence.get(TokensAnnotation.class)) {
        System.out.print(token.get(TextAnnotation.class) + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  @Test
  public void testBatch() {
    SentencePrinter plain = new SentencePrinterBuilder().setPrettyPrinting()
        .createPlain();
    ISimpBuilder builder = new ISimpBuilder();
    ISimp isimp = builder.create();
    
    String text = "UDEL is a university. CMU is a university.";
    
    Annotation document = new Annotation(text);
    isimp.annotate(document);

    System.out.println("INPUT:\n" + text);
    System.out.println("OUTPUT:");

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      System.out.println(plain.get(sentence));
    }
    System.out.println();
  }
}
