package seleniumutils.methods.helpers;

import com.github.javafaker.Faker;
import com.hervian.lambda.Lambda;
import com.hervian.lambda.LambdaFactory;
import seleniumutils.methods.ApachePOIExcel;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class DataUtils {

    public static HashMap<String,HashMap<String,HashMap<String, String>>> pageObjects;

    public static String generateFakeData(String dataValue)throws Throwable{
        if (!(dataValue.startsWith("<%") && dataValue.endsWith("%>"))) {
            return dataValue;
        }
        String fakerType = HelperUtils.stringFetch(dataValue,"<%([^:]*):.*",1);
        String dataExpression = dataValue.substring(8, dataValue.length() - 2);
        Faker faker=new Faker(Locale.US, new Random());
        String result=null;
        switch(fakerType.toLowerCase()) {
            case "regex":
                result = faker.regexify(dataExpression);
                String x = faker.getClass().getDeclaredMethods().toString();
                break;
            case "numerify":
                result = faker.numerify(dataExpression);
                break;
            case "letterify":
                result = faker.letterify(dataExpression);
                break;
            default:
                if(fakerType.contains("faker.")) {
                    try {
                        String[] classDetails = fakerType.split("\\.");
                        Method fakerMethod = faker.getClass().getDeclaredMethod(classDetails[1]);
                        Lambda lambda_class = LambdaFactory.create(fakerMethod);
                        Object fakerClass = lambda_class.invoke_for_Object(faker);
                        Method fakeData = fakerClass.getClass().getDeclaredMethod(classDetails[2]);
                        Lambda lambda_method = LambdaFactory.create(fakeData);
                        result = (String) lambda_method.invoke_for_Object(fakerClass);
                        //System.out.printf("init_fkmth: %.6fs,\tref_class: %.6fs, \tinit_fkdta: %.6fs, \tref_res: %.6fs, \tlmd_class: %.6fs, \tlmd_initdt: %.6fs,  \tlmd_res: %.6fs \n",
                        //        (t1 - t0) * 1e-9, (t2 - t1) * 1e-9, (t3 - t2) * 1e-9, (t4 - t3) * 1e-9, (t6 - t5) * 1e-9, (t7 - t6) * 1e-9, (t9 - t8) * 1e-9);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else
                    result=dataValue;
        }
        return result;
    }
}
