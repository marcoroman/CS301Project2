import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class RootLocater{

    static File file = new File("output.txt");
    static PrintWriter writer;

    public static void main(String[] args) throws FileNotFoundException {

        writer = new PrintWriter(file);

        double[] function1 = {-5, 17.7, -11.7, 2};
        double[] function1Derivative = {17.7, -23.4, 6};

        writer.println("Bisection Method");
        Bisection(function1, 0, 1);
        Bisection(function1, 1, 3);
        Bisection(function1, 3, 4);

        writer.println("Newton-Raphson Method");
        Newton_Raphson(function1, function1Derivative, 0);
        Newton_Raphson(function1, function1Derivative, 2);
        Newton_Raphson(function1, function1Derivative, 3);

        writer.println("False Position Method");
        FalsePosition(function1, 0, 1);
        FalsePosition(function1, 1, 3);
        FalsePosition(function1, 3, 4);

        writer.println("Secant Method");
        Secant(function1, 0, 1);
        Secant(function1, 1, 3);
        Secant(function1, 3, 4);

        writer.println("Modified Secant Method");
        ModifiedSecant(function1, 1, .01);
        ModifiedSecant(function1, 2, .01);
        ModifiedSecant(function1, 3, .01);

        writer.close();
    }

    public static void Bisection(double[] fx, double doubleA, double doubleB){
        double doubleC = 0, evalA = 0, evalB = 0, evalC = 0, doubleApproximateError = 1, prev = doubleC;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", "n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Ea");

        while(doubleApproximateError >= .01){
            doubleC = (doubleA + doubleB) / 2.0;

            doubleApproximateError = Math.abs((doubleC - prev) / doubleC);

            evalA = evaluate(fx, doubleA);
            evalB = evaluate(fx, doubleB);
            evalC = evaluate(fx, doubleC);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n", iteration++, doubleA, doubleB, doubleC, evalA, evalB, evalC, doubleApproximateError);

            if((evalA >= 0 && evalC >= 0) || (evalA < 0 && evalC < 0)){
                doubleA = doubleC;
            }else
                doubleB = doubleC;

            prev = doubleC;
        }

        writer.println();
    }

    public static void Newton_Raphson(double[] fx, double[] dfx, double start){
        double doubleVariableX = start, doubleVariableNext = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s\n", "n", "x", "x + 1", "f(x)", "df(x)", "Ea");

        while(doubleApproximateError >= .01){
            double evalFx = evaluate(fx, doubleVariableX);
            double eval_dFx = evaluate(dfx, doubleVariableX);

            doubleVariableNext = doubleVariableX - (evalFx / eval_dFx);

            doubleApproximateError = Math.abs((doubleVariableNext - doubleVariableX) / doubleVariableNext);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f\n", iteration++, doubleVariableX, doubleVariableNext, evalFx, eval_dFx, doubleApproximateError);

            doubleVariableX = doubleVariableNext;
        }

        writer.println();
    }

    public static void FalsePosition(double[] fx, double doubleA, double doubleB){
        double doubleC = 0, evalA = 0, evalB = 0, evalC = 0, doubleApproximateError = 1, prev = doubleC;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", "n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Ea");

        while(doubleApproximateError >= .01){
            evalA = evaluate(fx, doubleA);
            evalB = evaluate(fx, doubleB);

            doubleC = ((doubleA * evalB) - (doubleB * evalA)) / (evalB - evalA);
            evalC = evaluate(fx, doubleC);

            doubleApproximateError = Math.abs((doubleC - prev) / doubleC);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n", iteration++, doubleA, doubleB, doubleC, evalA, evalB, evalC, doubleApproximateError);

            if((evalA >= 0 && evalC >= 0) || (evalA < 0 && evalC < 0)){
                doubleA = doubleC;
            }else
                doubleB = doubleC;

            prev = doubleC;
        }

        writer.println();
    }

    public static void Secant(double[] fx, double x0, double x1){
        double x2 = 0, evalX0 = 0, evalX1 = 0, evalX2 = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", "n", "x-1", "x", "x+1", "f(x-1)", "f(x)", "f(x+1)", "Ea");

        while(doubleApproximateError >= .01){
            evalX0 = evaluate(fx, x0);
            evalX1 = evaluate(fx, x1);

            x2 = x1 - (evalX1 * ((x1 - x0) / (evalX1 - evalX0)));
            evalX2 = evaluate(fx, x2);

            doubleApproximateError = Math.abs((x1 - x0) / x1);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f %-10f\n", iteration++, x0, x1, x2, evalX0, evalX1, evalX2, doubleApproximateError);

            x0 = x1;
            x1 = x2;
        }

        writer.println();
    }

    public static void ModifiedSecant(double[] fx, double x, double delta){
        double x1 = 0, deltaX = 0, evalX = 0, evalDx = 0, doubleApproximateError = 1;
        int iteration = 0;

        writer.printf("%-5s %-10s %-10s %-10s %-10s %-10s %-10s\n", "n", "x", "Dx", "x+1", "f(x)", "f(x + Dx)", "Ea");

        while(doubleApproximateError >= .01){
            deltaX = x * delta;

            evalX = evaluate(fx, x);
            evalDx = evaluate(fx, x + deltaX);

            x1 = x - (evalX * (deltaX / (evalDx - evalX)));

            doubleApproximateError = Math.abs((x1 - x) / x1);

            writer.printf("%-5d %-10f %-10f %-10f %-10f %-10f %-10f\n", iteration++, x, deltaX, x1, evalX, evalDx, doubleApproximateError);

            x = x1;
        }

        writer.println();
    }

    public static double evaluate(double[] function, double x){
        double result = 0;

        for(int i = 0; i < function.length; ++i)
            result += function[i] * Math.pow(x, i);

        return result;
    }
}