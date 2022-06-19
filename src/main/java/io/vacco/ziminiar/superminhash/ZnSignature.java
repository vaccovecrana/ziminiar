package io.vacco.ziminiar.superminhash;

public class ZnSignature {

  public double[] values;
  public int[] b, p, q;
  public int i, a;

  public ZnSignature init(int length) {
    this.values = new double[length];
    this.b = new int[length];
    this.q = new int[length];
    this.p = new int[length];
    this.a = length - 1;

    this.b[length - 1] = length;
    for (int k = 0; k < length; k++) {
      this.p[k] = k;
      this.q[k] = -1;
      this.values[k] = Integer.MAX_VALUE;
    }

    return this;
  }

}
