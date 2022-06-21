package io.vacco.ziminiar.superminhash;

import java.util.Random;

public class ZnBuffers {

  private static final Random rnd = new Random();

  private static void swap(int[] in, int i0, int i1) {
    int t = in[i0];
    in[i0] = in[i1];
    in[i1] = t;
  }

  public static void update(long seed, double[] h, int[] b, int[] q, int[] p, int a, int m, int i) {
    rnd.setSeed(seed);
    int j = 0, k;
    double r, rj;

    while (j <= a) {
      r = rnd.nextDouble();
      k = j + (int) Math.floor(r * (m - j));

      if (q[j] != i) {
        q[j] = i;
        p[j] = j;
      }
      if (q[k] != i) {
        q[k] = i;
        p[k] = k;
      }

      swap(p, j, k);
      rj = r + j;

      if (rj < h[p[j]]) {
        int j2 = (int) Math.min(h[p[j]], m - 1);
        h[p[j]] = rj;
        if (j < j2) {
          b[j2] -= 1;
          b[j] += 1;
          while (b[a] == 0) {
            a -= 1;
          }
        }
      }

      j = j + 1;
    }
  }

  public static void update(long seed, int i, ZnBuffer buf) {
    update(seed, buf.h, buf.b, buf.q, buf.p, buf.a, buf.m, i);
  }

  public static double similarity(ZnBuffer b0, ZnBuffer b1) {
    int[] a = b0.hi, b = b1.hi;
    double sim = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] != b[i]) {
        sim += 1;
      }
    }
    return 1 - sim / a.length;
  }

}
