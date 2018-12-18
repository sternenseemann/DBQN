package APL.types.arrs;

import APL.errors.DomainError;
import APL.types.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SingleItemArr extends Arr {
  private final Value item;
  
  public SingleItemArr(Value item, int[] shape) {
    super(shape);
    this.item = item;
  }
  
  @Override
  public int[] asIntVec() {
    int vi = item.asInt();
    int[] a = new int[ia];
    for (int i = 0; i < ia; i++) a[i] = vi;
    return a;
  }
  
  @Override
  public int asInt() {
    throw new DomainError("using array as integer", this);
  }
  
  @Override
  public Value get(int i) {
    return item;
  }
  
  @Override
  public String asString() {
    if (rank >= 2) throw new DomainError("using rank≥2 array as string");
    if (! (item instanceof Char)) throw new DomainError("using non-char array as string");
    char c = ((Char) item).chr;
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < ia; i++) s.append(c);
    return s.toString();
  }
  
  @Override
  public Value prototype() {
    return item.prototype();
  }
  
  @Override
  public Value ofShape(int[] sh) {
    assert ia == Arrays.stream(sh).reduce(1, (a, b) -> a*b);
    return new SingleItemArr(item, sh);
  }
  
  @Override
  public boolean quickDoubleArr() {
    return item instanceof Num;
  }
  public Value[] values() {
    Value[] vs = new Value[ia];
    for (int i = 0; i < ia; i++) vs[i] = item;
    return vs;
  }
  @Override
  public double[] asDoubleArr() {
    double[] res = new double[ia];
    double n = item.asDouble();
    for (int i = 0; i < ia; i++) res[i] = n;
    return res;
  }
  
  @Override
  public double[] asDoubleArrClone() {
    return asDoubleArr();
  }
  
  @Override
  public Value squeeze() {
    Value ov = item.squeeze();
    if (ov == item) return this;
    return new SingleItemArr(item, shape);
  }
  
  @Override
  public String oneliner(int[] where) {
    if (where.length == 0) {
      String r = Arrays.stream(shape).mapToObj(String::valueOf).collect(Collectors.joining(" "));
      return (r.length() == 0? "⍬" : r) + "⍴" + item.oneliner(new int[0]);
    }
    return super.oneliner(where);
  }
  @Override
  public String toString() {
    String r = Arrays.stream(shape).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    return (r.length() == 0? "⍬" : r) + "⍴" + item.oneliner(new int[0]);
  }
}