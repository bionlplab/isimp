package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import bioc.BioCAnnotation;
import bioc.BioCCollection;
import bioc.BioCLocation;
import bioc.BioCNode;
import bioc.BioCRelation;
import bioc.BioCSentence;
import bioc.io.BioCCollectionReader;
import bioc.io.BioCFactory;
import bioc.util.BioCSentenceIterator;

public class EvalSimp {

  /**
   * @param args
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void main(String[] args)
      throws XMLStreamException, IOException {
    BioCCollection goldCollection = null;
    try {
      BioCCollectionReader reader = BioCFactory
          .newFactory(BioCFactory.STANDARD).createBioCCollectionReader(
              new FileReader("testcases/bioc-isimp-simplification.xml"));
      goldCollection = reader.readCollection();
      reader.close();
    } catch (FileNotFoundException e) {
      System.err.println("No such file or directory");
    }

    BioCCollection predCollection = null;
    try {
      BioCCollectionReader reader = BioCFactory
          .newFactory(BioCFactory.STANDARD).createBioCCollectionReader(
              new FileReader("testcases/bioc-isimp-simplification.simp.xml"));
      predCollection = reader.readCollection();
      reader.close();
    } catch (FileNotFoundException e) {
      System.err.println("No such file or directory");
    }

    BioCSentenceIterator golditr = new BioCSentenceIterator(goldCollection);
    BioCSentenceIterator preditr = new BioCSentenceIterator(predCollection);

    int numGoldRelTP = 0;
    int numGoldRelFN = 0;
    int numGoldRelFP = 0;
    PrintStream reltpOutput = new PrintStream(new File("rel.tp"));
    PrintStream relfnOutput = new PrintStream(new File("rel.fn"));
    PrintStream relfpOutput = new PrintStream(new File("rel.fp"));

    while (golditr.hasNext() && preditr.hasNext()) {
      BioCSentence goldsen = golditr.next();
      BioCSentence predsen = preditr.next();
      assert goldsen.getOffset() == predsen.getOffset();

      List<BioCRelation> goldrellist = new ArrayList<BioCRelation>();
      for (BioCRelation goldrel : goldsen.getRelations()) {
        String goldtype = goldrel.getInfon("simp");
        if (goldtype.equals("relative clause")) {
          goldrellist.add(goldrel);
        }
      }
      // remove gold duplicate
//      for (int i = 0; i < goldrellist.size(); i++) {
//        BioCRelation goldrel = goldrellist.get(i);
//        BioCLocation goldLoc = getLoc("relative clause", goldrel, goldsen);
//
//        Iterator<BioCRelation> itr = goldrellist.listIterator(i + 1);
//        while (itr.hasNext()) {
//          BioCRelation temprel = itr.next();
//          BioCLocation tempLoc = getLoc("relative clause", temprel, goldsen);
//          if (tempLoc.getOffset() == goldLoc.getOffset()
//              && tempLoc.getOffset() + tempLoc.getLength() == goldLoc
//                  .getOffset() + goldLoc.getLength()) {
//            //itr.remove();
//            System.err.println(goldsen);
//          }
//        }
//      }

      List<BioCRelation> predrellist = new ArrayList<BioCRelation>();
      for (BioCRelation predrel : predsen.getRelations()) {
        String predtype = predrel.getInfon("simp");
        if (predtype.equals("relative clause")) {
          predrellist.add(predrel);
        }
      }

      for (BioCRelation goldrel : goldrellist) {
        // find clause
        BioCLocation goldLoc = getLoc("relative clause", goldrel, goldsen);
        boolean found = false;
        boolean partiallyFound = false;
        BioCRelation predrel = null;
        for (BioCRelation rel : predrellist) {
          BioCLocation predLoc = getLoc("relative clause", rel, predsen);

          if (predLoc.getOffset() == goldLoc.getOffset()
              && predLoc.getOffset() + predLoc.getLength() == goldLoc
                  .getOffset() + goldLoc.getLength()) {
            found = true;
            predrellist.remove(rel);
            break;
          } else if (predLoc.getOffset() == goldLoc.getOffset()) {
            partiallyFound = true;
            predrel = rel;
          }
        }
        if (found) {
          numGoldRelTP++;
          print(reltpOutput, goldrel, goldsen);
        } else if (partiallyFound) {
          numGoldRelFN++;
          print(relfnOutput, goldrel, goldsen, predrel, predsen);
        } else {
          numGoldRelFN++;
          print(relfnOutput, goldrel, goldsen);
        }
      }
      numGoldRelFP += predrellist.size();
      for (BioCRelation predrel : predrellist) {
        print(relfpOutput, predrel, predsen);
      }
    }
    System.out.println("relative TP: " + numGoldRelTP);
    System.out.println("relative FN: " + numGoldRelFN);
    System.out.println("relative FP: " + numGoldRelFP);

    reltpOutput.close();
    relfnOutput.close();
    relfpOutput.close();
  }

  private static void print(PrintStream out, BioCRelation rel, BioCSentence sen) {
    out.println(sen.getText());

    BioCLocation loc = getLoc("referred noun phrase", rel, sen);
    String ref = sen.getText().substring(
        loc.getOffset() - sen.getOffset(),
          loc.getOffset() + loc.getLength() - sen.getOffset());

    loc = getLoc("relative clause", rel, sen);
    String clause = sen.getText().substring(
        loc.getOffset() - sen.getOffset(),
          loc.getOffset() + loc.getLength() - sen.getOffset());

    out.println("REL: " + ref + " ~@~ " + clause);
    out.println();
  }
  
  private static void print(PrintStream out, BioCRelation rel1, BioCSentence sen1, BioCRelation rel2, BioCSentence sen2) {
    out.println(sen1.getText());

    BioCLocation loc = getLoc("referred noun phrase", rel1, sen1);
    String ref = sen1.getText().substring(
        loc.getOffset() - sen1.getOffset(),
          loc.getOffset() + loc.getLength() - sen1.getOffset());

    loc = getLoc("relative clause", rel1, sen1);
    String clause = sen1.getText().substring(
        loc.getOffset() - sen1.getOffset(),
          loc.getOffset() + loc.getLength() - sen1.getOffset());

    out.println("REL: " + ref + " ~@~ " + clause);
    
    loc = getLoc("referred noun phrase", rel2, sen2);
    ref = sen2.getText().substring(
        loc.getOffset() - sen2.getOffset(),
          loc.getOffset() + loc.getLength() - sen2.getOffset());

    loc = getLoc("relative clause", rel2, sen2);
    clause = sen2.getText().substring(
        loc.getOffset() - sen2.getOffset(),
          loc.getOffset() + loc.getLength() - sen2.getOffset());
    
    out.println("REL: " + ref + " ~@~ " + clause);
    
    out.println();
  }

  private static BioCLocation getLoc(String nodeRole,
      BioCRelation rel,
      BioCSentence sen) {
    BioCNode node = null;
    for (BioCNode n : rel.getNodes()) {
      if (n.getRole().equals(nodeRole)) {
        node = n;
        break;
      }
    }
    assert node != null;
    BioCAnnotation ann = null;
    for (BioCAnnotation a : sen.getAnnotations()) {
      if (a.getID().equals(node.getRefid())) {
        ann = a;
        break;
      }
    }
    assert ann != null;
    return ann.getLocations().get(0);
  }

}
