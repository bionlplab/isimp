package detect;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utils.PatternEnv;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class DetectionTregexReader implements Closeable {

  public static final int                     Coordination      = 1;
  public static final int                     RelativeClause    = 2;
  public static final int                     ParentThesis      = 3;
  public static final int                     Apposition        = 4;
  public static final int                     SentenceBeginning = 6;
  public static final int                     MemberCollection  = 7;
  public static final int                     Hypernymy         = 8;
  public static final int                     Others            = 9;

  private static final List<DetectionPattern> list              = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> coolist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> rellist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> parlist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> applist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> senbeglist        = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> memlist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> hyplist           = new ArrayList<DetectionPattern>();
  private static final List<DetectionPattern> otherslist        = new ArrayList<DetectionPattern>();

  private static boolean                      isRead            = false;

  public void isRead() {
    isRead = true;
  }

  public static List<DetectionPattern> getTregex() {
    if (!isRead) {
      read();
    }
    return list;
  }

  public static List<DetectionPattern> getTregex(int type) {
    if (!isRead) {
      read();
    }
    switch (type) {
    case Coordination:
      return coolist;
    case RelativeClause:
      return rellist;
    case Apposition:
      return applist;
    case ParentThesis:
      return parlist;
    case SentenceBeginning:
      return senbeglist;
    case MemberCollection:
      return memlist;
    case Hypernymy:
      return hyplist;
    case Others:
      return otherslist;
    default:
      return list;
    }
  }

  private static void read() {
    list.clear();

    try {
      read(
          new FileInputStream(PatternEnv.DIR + PatternEnv.PARENTHESIS_TREGEX),
            new FileInputStream(PatternEnv.DIR + PatternEnv.COORDINATION_TREGEX),
            new FileInputStream(PatternEnv.DIR + PatternEnv.RELATIVE_TREGEX),
            new FileInputStream(PatternEnv.DIR + PatternEnv.APPOSITION_TREGEX),
            null,
            null,
            new FileInputStream(PatternEnv.DIR
                + PatternEnv.MEMEMBER_COLLECTION_TREGEX),
            new FileInputStream(PatternEnv.DIR + PatternEnv.HYPERNYMY_TREGEX));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    isRead = true;
  }

  public static void read(InputStream paris,
      InputStream coois,
      InputStream relis,
      InputStream appis,
      InputStream senbegis,
      InputStream otheris,
      InputStream memis,
      InputStream hypis) {
    DetectionTregexReader reader;
    try {
      reader = new DetectionTregexReader(paris);
      parlist.addAll(reader.readTregex());
      reader.close();

      reader = new DetectionTregexReader(coois);
      coolist.addAll(reader.readTregex());
      reader.close();

      reader = new DetectionTregexReader(relis);
      rellist.addAll(reader.readTregex());
      reader.close();

      reader = new DetectionTregexReader(appis);
      applist.addAll(reader.readTregex());
      reader.close();

      reader = new DetectionTregexReader(memis);
      memlist.addAll(reader.readTregex());
      reader.close();

      reader = new DetectionTregexReader(hypis);
      hyplist.addAll(reader.readTregex());
      reader.close();

      //
      // reader = new DetectionTregexReader(senbegis);
      // senbeglist.addAll(reader.readTregex());
      // reader.close();
      //
      // reader = new DetectionTregexReader(otheris);
      // otherslist.addAll(reader.readTregex());
      // reader.close();

      isRead = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  LineNumberReader reader;

  public DetectionTregexReader(InputStream is) {
    reader = new LineNumberReader(new InputStreamReader(is));
  }

  public List<DetectionPattern> readTregex()
      throws IOException {
    List<DetectionPattern> list = new LinkedList<DetectionPattern>();
    String line = null;
    while ((line = reader.readLine()) != null) {
      line = line.trim();
      if (line.startsWith("#") || line.startsWith("//")) {
        continue;
      } else if (line.isEmpty()) {
        continue;
      }
      int index = line.indexOf(':');
      if (index == -1) {
        throw new IllegalArgumentException("cannot parse line["
            + reader.getLineNumber()
              + "]: "
              + line);
      }
      String name = line.substring(0, index).trim();
      String value = line.substring(index + 1).trim();
      if (name.equals("tregex")) {
        list.add(new DetectionPattern(TregexPattern.compile(value)));
      } else {
        throw new IllegalArgumentException("cannot parse line["
            + reader.getLineNumber()
              + "]: "
              + line);
      }

    }
    return list;
  }

  @Override
  public void close()
      throws IOException {
    reader.close();
  }
}
