package io.vacco.ziminiar;

public class ZnTestPair {

  public String d0, d1;
  public double similarity;

  public static ZnTestPair from(String d0, String d1) {
    var p = new ZnTestPair();
    p.d0 = d0;
    p.d1 = d1;
    return p;
  }

  public ZnTestPair withSimilarity(double similarity) {
    this.similarity = similarity;
    return this;
  }

  @Override public String toString() {
    return String.format("[%s, %s => %.4f]", d0, d1, similarity);
  }
}
