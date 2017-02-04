package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

import detect.ISimp;
import detect.ISimpBuilder;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Timing;

public class Console {

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("Console [OPTIONS] [INPUT] [OUTPUT]\n"
        + "Tag the POS, parse the sentences, and detect simplification "
        + "constructs in the sentences.\n\n"
        + "By default, assume the document "
        + "is not tokenized and sentence-splited. Therefore, these "
        + "two tasks will be done first.", options);
  }

  public static void main(String[] args)
      throws IOException {

    // create Options object
    Options options = new Options();
    options.addOption("parse_only", false, "If set, parse the document only");
    options
        .addOption(
            "tokenized",
            false,
            "set input tokenized. If not set, assume the document is not tokenized and ssplited.");
    options.addOption("help", false, "display this help and exit");
    options
        .addOption(
            "json",
            false,
            "print file in JSON format. If not set, print file in plain text format");

    CommandLineParser parser = new GnuParser();
    CommandLine cmd = null;
    try {
      // parse the command line arguments
      cmd = parser.parse(options, args);
    } catch (ParseException exp) {
      // oops, something went wrong
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      printHelp(options);
      System.exit(1);
    }

    if (cmd.hasOption("help")) {
      printHelp(options);
      System.exit(1);
    }

    Timing tim = new Timing();

    SentencePrinterBuilder printerBuilder = new SentencePrinterBuilder();
    SentencePrinter printer;
    if (cmd.hasOption("json")) {
      printer = printerBuilder.setPrettyPrinting().createJson();
    } else {
      printer = printerBuilder.createPlain();
    }

    ISimpBuilder isimpBuilder = new ISimpBuilder();
    if (cmd.hasOption("tokenzied")) {
      isimpBuilder = isimpBuilder.setTokenized();
    }

    File inputFile = new File(cmd.getArgs()[0]);
    PrintStream ps = new PrintStream(new FileOutputStream(cmd.getArgs()[1]));

    ISimp isimp = isimpBuilder.create();

    String text = FileUtils.readFileToString(inputFile, "utf8");

    Annotation document = new Annotation(text);
    isimp.annotate(document);

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      String str = printer.get(sentence);
      ps.println(str);
    }
    ps.close();

    System.out.println(isimp.timingInformation());
    System.err.println("Total time for iSimp pipeline: "
        + tim.toSecondsString()
        + " sec.");
  }
}
