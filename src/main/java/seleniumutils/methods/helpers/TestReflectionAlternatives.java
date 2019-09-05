package seleniumutils.methods.helpers;

import com.hervian.lambda.Lambda;
import com.hervian.lambda.LambdaFactory;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.IntBinaryOperator;

public class TestReflectionAlternatives
{
    private static final int ITERATIONS = 50_000_000;
    private static final int WARM_UP = 10;

    public static void main(String... args) throws Throwable
    {
        // hold result to prevent too much optimizations
        final int[] dummy=new int[5];
        float direct_total=0,lambda_total=0,mthndl_total=0,reflect_total=0,factory_total=0;
        //Reflection Definitions
        Method reflected=TestReflectionAlternatives.class.getDeclaredMethod("myMethod", int.class, int.class);
        //Lambda Factory Definition
        Lambda lambdaFactory = LambdaFactory.create(reflected);
        //Method Handle definition
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh=lookup.unreflect(reflected);
        //Lambda definition
        IntBinaryOperator lambda=(IntBinaryOperator)LambdaMetafactory.metafactory(
                lookup, "applyAsInt", MethodType.methodType(IntBinaryOperator.class),
                mh.type(), mh, mh.type()).getTarget().invokeExact();
    //dry runs
    System.out.println("\t\tdirect\tlambda\tmthndl\treflect\tfactory");

        //warm up before benchmarking
        for (int i = 0; i < WARM_UP; i++) {
            dummy[0] += testDirect(dummy[0]);
            dummy[1] += testLambda(dummy[1], lambda);
            dummy[2] += testMH(dummy[1], mh);
            dummy[3] += testReflection(dummy[2], reflected);
            dummy[4] += testLambdaFactory(dummy[3], lambdaFactory);
        }
    for( int j=0;j<10;j++) {
        long t0 = System.nanoTime();
        dummy[0] += testDirect(dummy[0]);
        long t1 = System.nanoTime();
        dummy[1] += testLambda(dummy[1], lambda);
        long t2 = System.nanoTime();
        dummy[2] += testMH(dummy[1], mh);
        long t3 = System.nanoTime();
        dummy[3] += testReflection(dummy[2], reflected);
        long t4 = System.nanoTime();
        dummy[4] += testLambdaFactory(dummy[3], lambdaFactory);
        long t5 = System.nanoTime();
        System.out.printf("\t\t%.3fs\t%.3fs\t%.3fs\t%.3fs\t%.3fs \n",
                (t1 - t0) * 1e-9, (t2 - t1) * 1e-9, (t3 - t2) * 1e-9, (t4 - t3) * 1e-9, (t5 - t4) * 1e-9);
        direct_total+=(t1 - t0) * 1e-9;
        lambda_total+=(t2 - t1) * 1e-9;
        mthndl_total+=(t3 - t2) * 1e-9;
        reflect_total+=(t4 - t3) * 1e-9;
        factory_total+=(t5 - t4) * 1e-9;

    }
        System.out.println("---------------------------------------------------");
        System.out.printf("Total:\t%.3fs\t%.3fs\t%.3fs\t%.3fs\t%.3fs \n",direct_total,lambda_total,mthndl_total,reflect_total,factory_total);
        // do something with the results
        if(dummy[0]!=dummy[1] || dummy[0]!=dummy[2] || dummy[0]!=dummy[3] || dummy[0]!=dummy[4])
            throw new AssertionError();
    }

    private static int testMH(int v, MethodHandle mh) throws Throwable
    {
        //a method that always compares 1000 with 0: just to do a relational, ternary and arithmetic operation together
        for(int i=0; i<ITERATIONS; i++)
            v+=(int)mh.invokeExact(1000, v);
        return v;
    }

    private static int testReflection(int v, Method mh) throws Throwable
    {
        //a method that always compares 1000 with 0: just to do a relational, ternary and arithmetic operation together
        for(int i=0; i<ITERATIONS; i++)
            v+=(int)mh.invoke(null, 1000, v);
        return v;
    }

    private static int testDirect(int v)
    {
        //a method that always compares 1000 with 0: just to do a relational, ternary and arithmetic operation together
        for(int i=0; i<ITERATIONS; i++)
            v+=myMethod(1000, v);
        return v;
    }

    private static int testLambda(int v, IntBinaryOperator accessor)
    {
        for(int i=0; i<ITERATIONS; i++)
            v+=accessor.applyAsInt(1000, v);
        return v;
    }

    private static int testLambdaFactory(int v, Lambda accessor)
    {
        //a method that always compares 1000 with 0: just to do a relational, ternary and arithmetic operation together
        for(int i=0; i<ITERATIONS; i++)
            v+= accessor.invoke_for_int(1000, v);
        return v;
    }

    private static int myMethod(int a, int b)
    {
        return a<b? a: b;
    }
}
