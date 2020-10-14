package APL.types;

import APL.errors.*;
import APL.types.callable.*;

public abstract class Md2 extends Callable {
  
  protected Md2() { }
  
  public Value call(         Value x) { throw new SyntaxError("Cannot interpret a 2-modifier as a function", this, x); }
  public Value call(Value w, Value x) { throw new SyntaxError("Cannot interpret a 2-modifier as a function", this, x); }
  
  
  public Value derive(Value f, Value g) {
    return new Md2Derv(f, g, this);
  }
  public Md1 derive(Value g) {
    return new Md2HalfDerv(g, this);
  }
  
  public Value call(Value f, Value g, Value x, Md2Derv derv) {
    throw new IncorrectArgsError(repr()+" can't be called monadically", derv, x);
  }
  public Value call(Value f, Value g, Value w, Value x, Md2Derv derv) {
    throw new IncorrectArgsError(repr()+" can't be called dyadically", derv, w);
  }
  
  public Value callInv(Value f, Value g, Value x) {
    throw new DomainError(this+" doesn't support monadic inverting", this, x);
  }
  public Value callInvX(Value f, Value g, Value w, Value x) {
    throw new DomainError(this+" doesn't support dyadic inverting of 𝕩", this, x);
  }
  public Value callInvW(Value f, Value g, Value w, Value x) {
    throw new DomainError(this+" doesn't support dyadic inverting of 𝕨", this, x);
  }
  public Value under(Value f, Value g, Value o, Value x, Md2Derv derv) {
    Value v = o instanceof Fun? o.call(call(f, g, x, derv)) : o;
    return callInv(f, g, v);
  }
  public Value underW(Value f, Value g, Value o, Value w, Value x, Md2Derv derv) {
    Value v = o instanceof Fun? o.call(call(f, g, w, x, derv)) : o;
    return callInvX(f, g, w, v);
  }
  public Value underA(Value f, Value g, Value o, Value w, Value x, Md2Derv derv) {
    Value v = o instanceof Fun? o.call(call(f, g, w, x, derv)) : o;
    return callInvW(f, g, v, x);
  }
  
  public String toString() {
    return repr();
  }
  public abstract String repr();
  
  
  
  // functions are equal per-object basis
  public int hashCode() {
    return actualHashCode();
  }
  public boolean eq(Value o) {
    return this == o;
  }
}