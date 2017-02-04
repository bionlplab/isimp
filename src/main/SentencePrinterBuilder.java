package main;

import main.SentencePrinter.JsonPrinter;
import main.SentencePrinter.PlainPrinter;

public class SentencePrinterBuilder {
  private boolean isPrettyPrinting;

  public SentencePrinterBuilder() {
    isPrettyPrinting = false;
  }

  public SentencePrinterBuilder setPrettyPrinting() {
    isPrettyPrinting = true;
    return this;
  }

  public SentencePrinter createJson() {
    return new JsonPrinter(isPrettyPrinting);
  }
  
  public SentencePrinter createPlain() {
    return new PlainPrinter(isPrettyPrinting);
  }
}
