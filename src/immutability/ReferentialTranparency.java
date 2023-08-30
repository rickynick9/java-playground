package immutability;

public class ReferentialTranparency {

    /*
    Referential transparency : An expression or a function may be replaced with its value.

     */

    static final int a = 1;
    static final int b = 2;
    public static void main(String[] args) {
        System.out.println(7 + 8); // 7 is a constant, 8 is a constant, plus is a beautiful function that does not sneak
        // around and change state. We can say 7 + 8 is a pure function

        System.out.println(a + b); // Is this pure ?
        // Lets apply rule no 1 : a + b is pure if a + b does not mutate anything.
        // rule no 2 : both a and b are mutable and function plus dependents on mutable variables so the function is not pure.
        // You know these rules but guess what, compiler knows these rules as well

        /*
        As you can see in the bytecode
        The first opcode, "getstatic", fetches a static field and pushes its value onto the stack
        iadd opcode pops them from the stack and puts them back after adding them.
         */

        /*
        After changing a and b to final
        rule 1 : a + b does not change anything
        rule 2 : a and b are final and they are not going to change, so this function is pure

        In bytecode we could see, we no longer have iadd instead we have iconst_3
        This is referential transparency. If compiler knows a function is pure it can lead to optimized bytecode.
        A piece of code is referentially transparent if we can safely replace that piece of code with the value it computes

        */
    }
}

/*
Byte code, when you have non final int a and int b

javap -c ReferentialTranparency.class
Compiled from "ReferentialTranparency.java"
public class immutability.ReferentialTranparency {
  static int a;

  static int b;

  public immutability.ReferentialTranparency();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: bipush        15
       5: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
       8: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      11: getstatic     #4                  // Field a:I
      14: getstatic     #5                  // Field b:I
      17: iadd
      18: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
      21: return

  static {};
    Code:
       0: iconst_1
       1: putstatic     #4                  // Field a:I
       4: iconst_2
       5: putstatic     #5                  // Field b:I
       8: return
}


Byte Code after changing variables to final

javap -c ReferentialTranparency.class
Compiled from "ReferentialTranparency.java"
public class immutability.ReferentialTranparency {
  static final int a;

  static final int b;

  public immutability.ReferentialTranparency();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: bipush        15
       5: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
       8: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      11: iconst_3
      12: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
      15: return


 */