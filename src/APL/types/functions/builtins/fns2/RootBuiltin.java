package APL.types.functions.builtins.fns2;

import APL.types.*;
import APL.types.functions.Builtin;

public class RootBuiltin extends Builtin {
  @Override public String repr() {
    return "√";
  }
  
  
  
  private static final NumMV NF = new NumMV() {
    public Value call(Num w) {
      return w.root(Num.NUMS[2]);
    }
    public void call(double[] res, double[] a) {
      for (int i = 0; i < a.length; i++) res[i] = Math.sqrt(a[i]);
    }
  };
  private static final NumMV NFi = new NumMV() {
    public Value call(Num w) {
      return Num.of(w.num*w.num);
    }
    public void call(double[] res, double[] a) {
      for (int i = 0; i < a.length; i++) res[i] = a[i]*a[i];
    }
  };
  public Value call(Value x) {
    return numM(NF, x);
  }
  public Value callInv(Value x) {
    return numM(NFi, x);
  }
  
  public static final D_NNeN DNF = new D_NNeN() {
    public double on(double a, double w) {
      return Math.pow(w, 1/a);
    }
    public void on(double[] res, double a, double[] w) {
      double pow = 1/a;
      for (int i = 0; i < w.length; i++) res[i] = Math.pow(w[i], pow);
    }
    public void on(double[] res, double[] a, double w) {
      for (int i = 0; i < a.length; i++) res[i] = Math.pow(w, 1/a[i]);
    }
    public void on(double[] res, double[] a, double[] w) {
      for (int i = 0; i < a.length; i++) res[i] = Math.pow(w[i], 1/a[i]);
    }
  };
  public Value call(Value w, Value x) {
    return numD(DNF, w, x);
  }
  
  public Value callInvW(Value a, Value w) {
    return numD(StarBuiltin.DNF, w, a);
  }
  public Value callInvA(Value a, Value w) {
    return numD(LogBuiltin.DNF, a, w);
  }
}