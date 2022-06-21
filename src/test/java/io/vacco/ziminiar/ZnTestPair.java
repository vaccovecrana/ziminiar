package io.vacco.ziminiar;

public class ZnTestPair {

  public String d0, d1;

  public static ZnTestPair from(String d0, String d1) {
    var p = new ZnTestPair();
    p.d0 = d0;
    p.d1 = d1;
    return p;
  }
}
