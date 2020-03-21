package APL.types.functions.builtins.fns;

import APL.errors.DomainError;
import APL.types.*;
import APL.types.functions.Builtin;

public class StileBuiltin extends Builtin {
  @Override public String repr() {
    return "|";
  }
  
  
  
  private static final NumMV NF = new NumMV() {
    public Value call(Num w) {
      return w.abs();
    }
    public void call(double[] res, double[] a) {
      for (int i = 0; i < a.length; i++) res[i] = Math.abs(a[i]);
    }
    public Value call(BigValue w) {
      return new BigValue(w.i.abs());
    }
  };
  
  public Value call(Value w) {
    return numChrMapM(NF, c->{ throw new DomainError("|char", this, w); }, c -> Num.of(c.size()), w);
  }
  
  private static final D_NNeN DNF = new D_NNeN() {
    public double on(double a, double w) {
      double c = w % a;
      if (c < 0) return c + a;
      return c;
    }
    public void on(double[] res, double a, double[] w) {
      int ia = (int) a;
      if (a == ia) {
        if (ia > 0 && (ia & ia-1) == 0) {
          // power of 2 ⍺
          int mask = ia-1;
          for (int i = 0; i < w.length; i++) {
            int intv = (int) w[i];
            if (intv == w[i]) res[i] = (intv & mask) + w[i]-intv;
            else {
              double c = w[i]%ia;
              if (c < 0) res[i] = c + a;
              else res[i] = c;
            }
          }
        } else {
          // integer ⍺
          for (int i = 0; i < w.length; i++) {
            double c = w[i]%ia;
            if (c < 0) res[i] = c + a;
            else res[i] = c;
          }
        }
      } else {
        // arbitrary double ⍺
        for (int i = 0; i < w.length; i++) {
          double c = w[i]%a;
          if (c < 0) res[i] = c + a;
          else res[i] = c;
        }
      }
    }
    public void on(double[] res, double[] a, double w) {
      if (w > 0) for (int i = 0; i < a.length; i++) res[i] = w % a[i];
      else       for (int i = 0; i < a.length; i++) {
        double c = w % a[i];
        if (c < 0) res[i] = c + a[i];
        else res[i] = c;
      }
    }
    public void on(double[] res, double[] a, double[] w) {
      for (int i = 0; i < a.length; i++) {
        double c = w[i] % a[i];
        if (c < 0) res[i] = c + a[i];
        else res[i] = c;
      }
    }
    public Value call(BigValue a, BigValue w) {
      return new BigValue(w.i.remainder(a.i));
    }
  };
  public Value call(Value a0, Value w0) {
    return numD(DNF, a0, w0);
  }
}