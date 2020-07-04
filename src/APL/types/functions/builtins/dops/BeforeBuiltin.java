package APL.types.functions.builtins.dops;

import APL.errors.DomainError;
import APL.types.*;
import APL.types.functions.*;

public class BeforeBuiltin extends Dop {
  public String repr() {
    return "⊸";
  }
  
  public Value call(Value aa, Value ww, Value w, DerivedDop derv) {
    return call(aa, ww, w, w, derv);
  }
  
  public Value call(Value aa, Value ww, Value a, Value w, DerivedDop derv) {
    return ww.asFun().call(aa.asFun().call(a), w);
  }
  
  public Value callInv(Value aa, Value ww, Value w) {
    if (aa instanceof Fun || aa instanceof Mop || aa instanceof Dop) throw new DomainError("𝕗⊸𝔾⁼: 𝕗 cannot be a function", this, aa); // +TODO make a saner checking way
    return ww.asFun().callInvW(aa, w);
  }
  
  public Value under(Value aa, Value ww, Value o, Value w, DerivedDop derv) {
    if (aa instanceof Fun || aa instanceof Mop || aa instanceof Dop) throw new DomainError("⌾(𝕗⊸𝔾): 𝕗 cannot be a function", this, aa); // +TODO make a saner checking way
    return ww.asFun().underW(o, aa, w);
  }
}
