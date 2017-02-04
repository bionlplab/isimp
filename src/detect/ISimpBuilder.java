package detect;

public class ISimpBuilder {

  boolean isTokenized;
  boolean onlyParser;

  public ISimpBuilder() {
    isTokenized = false;
    onlyParser = false;
  }

  /**
   * if set , separates words only when whitespace is encountered. if set, only
   * splits sentences on newlines.
   * 
   * @return
   */
  public ISimpBuilder setTokenized() {
    isTokenized = true;
    return this;
  }

  public ISimpBuilder onlyParser() {
    onlyParser = true;
    return this;
  }

  public ISimp create() {
    return new ISimp(this);
  }
}
