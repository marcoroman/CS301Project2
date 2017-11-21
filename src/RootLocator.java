import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class RootLocator{

    static File file = new File("output.txt");
    static PrintWriter writer;

    public static void main(String[] args) throws FileNotFoundException {

        writer = new PrintWriter(file);

        /***************Finding the roots of the first function***************/

        writer.println("Roots of Function 1\n");

        writer.println("Bisection Method");
        Bisection(1, 0, 1);
        Bisection(1, 1, 3);
        Bisection(1, 3, 4);

        writer.println("Newton-Raphson Method");
        Newton_Raphson(1, 0);
        Newton_Raphson(1, 2);
        Newton_Raphson(1, 3);

        writer.println("False Position Method");
        FalsePosition(1, 0, 1);
        FalsePosition(1, 1, 3);
        FalsePosition(1, 3, 4);

        writer.println("Secant Method");
        Secant(1, 0, 1);
        Secant(1, 1, 3);
        Secant(1, 3, 4);

        writer.println("Modified Secant Method");
        ModifiedSecant(1, 1, .01);
        ModifiedSecant(1, 2, .01);
        ModifiedSecant(1, 3, .01);

        /***************Finding the roots of the second function***************/

        writer.println("\n\nRoots of Function 2\n");

        writer.println("Bisection Method");
        Bisection(2, 120, 130);

        writer.println("Newton-Raphson Method");
        Newton_Raphson(2, 120);

        writer.println("False Position Method");
        FalsePosition(2, 120, 130);

        writer.println("Secant Method");
        Secant(2, 120, 130);

        writer.println("Modified Secant Method");
        ModifiedSecant(2, 120, .01);

        writer.close();

        System.out.println("All output written to output.txt");
    }

    //Arguments: (function #, guess A, guess B)
    public static void Bisection(int fx, double doubleA, double doubleB){
        double doubleC = 0, evalA = 0, evalB = 0, evalC = 0, doubleApproximateError = 1, prev = doubleC;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Ea");

        while(doubleApproximateError >= .01){
            doubleC = (doubleA + doubleB) / 2.0;

            doubleApproximateError = Math.abs((doubleC - prev) / doubleC);

            evalA = evaluate(fx, false, doubleA);
            evalB = evaluate(fx, false, doubleB);
            evalC = evaluate(fx, false, doubleC);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n",
                    iteration++, doubleA, doubleB, doubleC, evalA, evalB, evalC, doubleApproximateError);

            if((evalA >= 0 && evalC >= 0) || (evalA < 0 && evalC < 0)){
                doubleA = doubleC;
            }else
                doubleB = doubleC;

            prev = doubleC;
        }

        writer.println();
    }

    //Arguments: (function #, initial guess for x)
    public static void Newton_Raphson(int fx, double start){
        double doubleVariableX = start, doubleVariableNext = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s\n",
                "n", "x", "x + 1", "f(x)", "d[f(x)]", "Ea");

        while(doubleApproximateError >= .01){
            double evalFx = evaluate(fx, false, doubleVariableX);
            double eval_dFx = evaluate(fx, true, doubleVariableX);

            doubleVariableNext = doubleVariableX - (evalFx / eval_dFx);

            doubleApproximateError = Math.abs((doubleVariableNext - doubleVariableX) / doubleVariableNext);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f\n",
                    iteration++, doubleVariableX, doubleVariableNext, evalFx, eval_dFx, doubleApproximateError);

            doubleVariableX = doubleVariableNext;
        }

        writer.println();
    }

    //Arguments (function #, guess 1, guess 2)
    public static void FalsePosition(int fx, double doubleA, double doubleB){
        double doubleC = 0, evalA = 0, evalB = 0, evalC = 0, doubleApproximateError = 1, prev = doubleC;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Ea");

        while(doubleApproximateError >= .01){
            evalA = evaluate(fx, false, doubleA);
            evalB = evaluate(fx, false, doubleB);

            doubleC = ((doubleA * evalB) - (doubleB * evalA)) / (evalB - evalA);
            evalC = evaluate(fx, false, doubleC);

            doubleApproximateError = Math.abs((doubleC - prev) / doubleC);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n",
                    iteration++, doubleA, doubleB, doubleC, evalA, evalB, evalC, doubleApproximateError);

            if((evalA >= 0 && evalC >= 0) || (evalA < 0 && evalC < 0)){
                doubleA = doubleC;
            }else
                doubleB = doubleC;

            prev = doubleC;
        }

        writer.println();
    }

    //Arguments: (function #, x - 1, x)
    public static void Secant(int fx, double x0, double x1){
        double x2 = 0, evalX0 = 0, evalX1 = 0, evalX2 = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "n", "x-1", "x", "x+1", "f(x-1)", "f(x)", "f(x+1)", "Ea");

        while(doubleApproximateError >= .01){
            evalX0 = evaluate(fx, false, x0);
            evalX1 = evaluate(fx, false, x1);

            x2 = x1 - (evalX1 * ((x1 - x0) / (evalX1 - evalX0)));
            evalX2 = evaluate(fx, false, x2);

            doubleApproximateError = Math.abs((x1 - x0) / x1);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n",
                    iteration++, x0, x1, x2, evalX0, evalX1, evalX2, doubleApproximateError);

            x0 = x1;
            x1 = x2;
        }

        writer.println();
    }

    //Arguments: (function #, initial guess for x, delta value)
    public static void ModifiedSecant(int fx, double x, double delta){
        double x1 = 0, deltaX = 0, evalX = 0, evalDx = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "n", "x", "Dx", "x+1", "f(x)", "f(x + Dx)", "Ea");

        while(doubleApproximateError >= .01){
            deltaX = x * delta;

            evalX = evaluate(fx,false, x);
            evalDx = evaluate(fx, false,x + deltaX);

            x1 = x - (evalX * (deltaX / (evalDx - evalX)));

            doubleApproximateError = Math.abs((x1 - x) / x1);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f\n",
                    iteration++, x, deltaX, x1, evalX, evalDx, doubleApproximateError);

            x = x1;
        }

        writer.println();
    }

    //Arguments:
    //  option selects function
    //  derivative indicates whether the derivative is evaluated
    //  x contains the value for variable x
    public static double evaluate(int option, boolean derivative, double x){
        double result = 0;

        if(option == 1 && !derivative){
            result = (2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5;
        }else if(option == 1){
            result = (6 * Math.pow(x, 2)) - (23.4 * x) + 17.7;
        }else if(option == 2 && !derivative){
            result = x + 10 - (x * Math.cosh(50.0 / x));
        }else if(option == 2){
            result = 1 - Math.cosh(50.0 / x) + ((50.0 * Math.sinh(50.0 / x)) / x);
        }

        return result;
    }
}