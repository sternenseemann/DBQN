package APL.types.functions.builtins.dops;

import APL.errors.DomainError;
import APL.types.*;
import APL.types.functions.*;
import APL.types.functions.builtins.mops.InvBuiltin;

public class UnderBuiltin extends Dop {
  @Override public String repr() {
    return "⌾";
  }
  
  
  
  public Value call(Value aa, Value ww, Value w, DerivedDop derv) {
    Fun wwf = ww.asFun();
    return wwf.under(aa, w);
  }
  public Value callInv(Value aa, Value ww, Value w) {
    Fun aaf = aa.asFun(); Fun wwf = ww.asFun();
    return wwf.under(InvBuiltin.invertM(aaf), w);
  }
  
  public Value call(Value aa, Value ww, Value a, Value w, DerivedDop derv) {
    Fun aaf = aa.asFun(); Fun wwf = ww.asFun();
    return wwf.under(new BindA(wwf.call(a), aaf), w);
  }
  public Value callInvW(Value aa, Value ww, Value a, Value w) {
    Fun wwf = ww.asFun();
    return wwf.under(new BindA(wwf.call(a), InvBuiltin.invertW(aa.asFun())), w);
  }
  public Value callInvA(Value aa, Value ww, Value a, Value w) { // structural inverse is not possible; fall back to computational inverse
    Fun wwf = ww.asFun();
    Value a1 = wwf.call(a);
    Value w1 = wwf.call(w);
    try {
      return wwf.callInv(aa.asFun().callInvA(a1, w1));
    } catch (DomainError e) { // but add a nice warning about it if a plausible error was received (todo better error management to not require parsing the message?)
      String msg = e.getMessage();
      if (msg.contains("doesn't support") && msg.contains("inverting")) {
        throw new DomainError(msg + " (possibly caused by using f⌾g˜⁼, which only allows computational inverses)", e.cause);
      } throw e;
    }
  }
  
  public static class BindA extends Fun {
    final Value a;
    final Fun f;
    public BindA(Value a, Fun f) {
      this.a = a;
      this.f = f;
    }
    
    public Value call(Value x) {
      return f.call(a, x);
    }
    public Value callInv(Value w) {
      return f.callInvW(a, w);
    }
    
    public String repr() {
      return f.repr();
    }
  }
}