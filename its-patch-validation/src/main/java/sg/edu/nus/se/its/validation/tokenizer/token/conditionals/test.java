import com.microsoft.z3.*;

public class test {

    public static void main(String[] args) {
        Context ctx = new Context();

        // Define the integer variable x
        IntExpr x = ctx.mkIntConst("x");

        // Define the two functions: f1(x) = x + 1 and f2(x) = x + 2
        ArithExpr f1 = ctx.mkAdd(x, ctx.mkInt(1));
        ArithExpr f2 = ctx.mkAdd(x, ctx.mkInt(2));

        // Create a solver instance
        Solver s = ctx.mkSolver();

        // Assert that for all values of x, the outputs of f1 and f2 are equal
        BoolExpr forallExpr = ctx.mkForall(new Expr[] { x }, ctx.mkEq(f1, f2), 1, null, null, null, null);

        // Add the assertion to the solver
        s.add(ctx.mkNot(forallExpr)); // We assert the negation of the equivalence

        // Check if the functions are semantically equivalent
        Status result = s.check();

        if (result == Status.UNSATISFIABLE) {
            System.out.println("The functions are semantically equivalent for all values of x.");
        } else {
            System.out.println("The functions are not semantically equivalent for all values of x.");
        }

        // Dispose the context to free resources
        ctx.close();
    }
}
