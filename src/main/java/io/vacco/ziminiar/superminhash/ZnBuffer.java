package io.vacco.ziminiar.superminhash;

import java.util.Arrays;

public class ZnBuffer {

  public double[] h;
  public int[] hi;

  public int[] b, p, q;
  public int a, m;

  public ZnBuffer clear() {
    Arrays.fill(hi, 0);
    Arrays.fill(h, m + 1);
    Arrays.fill(b, 0);
    Arrays.fill(q, -1);
    Arrays.fill(p, 0);
    b[m - 1] = m;
    return this;
  }

  public ZnBuffer init(int length) {
    this.m = length;
    this.h = new double[length];
    this.hi = new int[length];
    this.b = new int[length];
    this.q = new int[length];
    this.p = new int[length];
    this.a = m - 1;
    return this.clear();
  }

  public ZnBuffer fill() {
    for (int i = 0; i < h.length; i++) {
      hi[i] = (int) h[i];
    }
    return this;
  }

}
