package APL.types.functions.builtins.fns2;

import APL.errors.DomainError;
import APL.types.Value;
import APL.types.functions.Builtin;

public class LTackBuiltin extends Builtin {
  @Override public String repr() {
    return "⊣";
  }
  
  
  
  public Value call(Value x) { return x; }
  public Value call(Value w, Value x) { return w; }
  
  public Value callInv(Value x) {
    return x;
  }
  public Value callInvX(Value w, Value x) {
    if (w.eq(x)) return w;
    throw new DomainError("⊣⁼: expected 𝕨 and 𝕩 to be equal", this);
  }
  public Value callInvW(Value w, Value x) {
    return w;
  }
}