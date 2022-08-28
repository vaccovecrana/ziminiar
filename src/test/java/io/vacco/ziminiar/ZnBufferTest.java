package io.vacco.ziminiar;

import io.vacco.ziminiar.document.*;
import io.vacco.ziminiar.superminhash.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.*;
import java.util.function.Function;

import static j8spec.J8Spec.*;
import static org.junit.Assert.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class ZnBufferTest {

  public static void logBuffer(ZnBuffer b) {
    System.out.println(Arrays.toString(b.fill().hi));
  }

  static {
    it("Converts tokens into weighted shingles", () -> {
      var sigLength = 128;

      Function<ZShingle, Long> hashFn = sh -> (long) sh.token.hashCode();
      String d0 = "How are you? I Am fine. ablar ablar xyz blar blar blar blar blar blar blar Thanks.";
      String d1 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar than";
      String d2 = "How are you i am fine.ablar ablar xyz blar blar blar blar blar blar blar thank";

      var docPairs = new ArrayList<ZnTestPair>();

      for (int i = 1; i < 8; i++) {
        System.out.println(d0);
        var sig0 = ZnShingles.fromDocument(d0, i, sigLength, hashFn, ZnBufferTest::logBuffer);
        System.out.println(d1);
        var sig1 = ZnShingles.fromDocument(d1, i, sigLength, hashFn, ZnBufferTest::logBuffer);
        System.out.println(d2);
        var sig2 = ZnShingles.fromDocument(d2, i, sigLength, hashFn, ZnBufferTest::logBuffer);

        var sim02 = ZnBuffers.similarity(sig0, sig2);
        var sim12 = ZnBuffers.similarity(sig1, sig2);

        System.out.println("-----------------------------");
        System.out.printf("[sl: %d, d0-d2: %.4f]%n", i, sim02);
        System.out.printf("[sl: %d, d1-d2: %.4f]%n", i, sim12);

        docPairs.add(ZnTestPair.from(d0, d2).withSimilarity(sim02));
        docPairs.add(ZnTestPair.from(d1, d2).withSimilarity(sim12));
      }

      assertTrue(docPairs.get(0).similarity > 0.77);
      assertTrue(docPairs.get(1).similarity > 0.95);
      assertTrue(docPairs.get(12).similarity > 0.42);
      assertTrue(docPairs.get(13).similarity > 0.98);

      var shingleLength = 1;
      var textPairs = new ArrayList<ZnTestPair>();

      for (ZnTestPair p : new ZnTestPair[] {
          ZnTestPair.from("abc", ""),
          ZnTestPair.from("", "abc"),
          ZnTestPair.from("", ""),
          ZnTestPair.from("abc", "abc"),
          ZnTestPair.from("abc", "xyz"),
          ZnTestPair.from("night", "nacht"),
          ZnTestPair.from("context", "contact"),
          ZnTestPair.from("ht", "nacht"),
      }) {
        System.out.println(p.d0);
        var b0 = ZnShingles.fromDocument(p.d0, shingleLength, sigLength, hashFn, ZnBufferTest::logBuffer);
        System.out.println(p.d1);
        var b1 = ZnShingles.fromDocument(p.d1, shingleLength, sigLength, hashFn, ZnBufferTest::logBuffer);
        var sim = ZnBuffers.similarity(b0, b1);
        System.out.println("-----------------------------");
        System.out.printf("[sl: %d, d0: %s, d1: %s, sim: %.4f]%n", shingleLength, p.d0, p.d1, sim);
        textPairs.add(p.withSimilarity(sim));
      }
      System.out.println("-----------------------------");

      assertEquals(0.0, textPairs.get(0).similarity, 0.0);
      assertEquals(0.0, textPairs.get(1).similarity, 0.0);
      assertEquals(1.0, textPairs.get(2).similarity, 0.0);
      assertEquals(1.0, textPairs.get(3).similarity, 0.0);

      assertTrue(textPairs.get(4).similarity < 0.025);
      assertTrue(textPairs.get(5).similarity > 0.4);
      assertTrue(textPairs.get(6).similarity > 0.57);
      assertTrue(textPairs.get(7).similarity > 0.35);
    });
  }
}
