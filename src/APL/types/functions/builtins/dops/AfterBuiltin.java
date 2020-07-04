package APL.types.functions.builtins.dops;

import APL.errors.DomainError;
import APL.types.*;
import APL.types.functions.*;

public class AfterBuiltin extends Dop {
  public String repr() {
    return "⟜";
  }
  
  public Value call(Value aa, Value ww, Value w, DerivedDop derv) {
    return call(aa, ww, w, w, derv);
  }
  
  public Value call(Value aa, Value ww, Value a, Value w, DerivedDop derv) {
    return aa.asFun().call(a, ww.asFun().call(w));
  }
  
  public Value callInv(Value aa, Value ww, Value w) {
    if (ww instanceof Fun || ww instanceof Mop || ww instanceof Dop) throw new DomainError("𝔽⟜𝕘⁼: 𝕘 cannot be a function", this, aa); // +TODO make a saner checking way
    return aa.asFun().callInvA(w, ww);
  }
  
  public Value under(Value aa, Value ww, Value o, Value w, DerivedDop derv) {
    if (ww instanceof Fun || ww instanceof Mop || ww instanceof Dop) throw new DomainError("⌾(𝔽⟜𝕘): 𝕘 cannot be a function", this, aa); // +TODO make a saner checking way
    return ((Fun) aa).underA(o, w, ww);
  }
}
