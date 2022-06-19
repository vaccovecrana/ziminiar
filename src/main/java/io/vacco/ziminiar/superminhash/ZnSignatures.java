package io.vacco.ziminiar.superminhash;

import io.vacco.ziminiar.transform.*;
import java.util.Random;
import java.util.function.Function;

public class ZnSignatures {

  private static final Random rnd = new Random();

  private static void swap(int[] in, int i0, int i1) {
    int t = in[i0];
    in[i0] = in[i1];
    in[i1] = t;
  }

  public static void update(double[] values, int[] q, int[] p,  int[] b, int a, int i, long seed) {
    rnd.setSeed(seed);
    double r, rj;
    int ar = a, k, offset;
    for (int j = 0; j < ar; j++) {
      r = rnd.nextDouble(); // 0.5;
      int o0 = rnd.nextInt() & Integer.MAX_VALUE; // 512;
      int o1 = values.length - j;
      offset = o0 % o1;
      k = j + offset;

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

      if (rj < values[p[j]]) {
        double jc = Math.min(values[p[j]], values.length - 1);
        values[p[j]] = rj;
        if (j < jc) {
          b[(int) jc] -= 1;
          b[j] += 1;
          while (b[a] == 0) {
            a -= 1;
          }
        }
      }
    }
  }

  // TODO what other types of hashable inputs should be supported?
  public static void update(ZnSignature sg, ZShingle sh, Function<ZShingle, Long> hashFn) {
    update(sg.values, sg.q, sg.p, sg.b, sg.a, sg.i, hashFn.apply(sh));
    sg.i += 1;
  }

  public static double similarity(ZnSignature s0, ZnSignature s1) {
    double sim = 0.0;
    for (int i = 0; i < s0.values.length; i++) {
      if (s0.values[i] == s1.values[i]) {
        sim += 1;
      }
    }
    return sim / s0.values.length;
  }

  public static double distance(ZnSignature s0, ZnSignature s1) {
    return 1 - similarity(s0, s1);
  }

  public static ZnSignature fromDocument(String document, int shingleLength, int signatureLength, Function<ZShingle, Long> hashFn) {
    var shl = ZnStringShingler.apply(document, shingleLength);
    var sig = new ZnSignature().init(signatureLength);
    for (ZShingle sh : shl) {
      ZnSignatures.update(sig, sh, hashFn);
    }
    return sig;
  }

}
