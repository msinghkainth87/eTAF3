package seleniumutils.methods.pageobjectmethods;

import com.hervian.lambda.Lambda;
import com.hervian.lambda.LambdaFactory;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.PageObjectGenerator;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static seleniumutils.methods.GlobalProperties.APPLICATION;

public class PageObjectCreator {
    //TODO WIP.  mainstream this
    //TODO Implement automated ElementObject class creation from excel sheet into fullQualified Class path
    public static PageObjectInterface createPageObject(String pageName) throws TestCaseFailed{
        PageObjectInterface page=null;

            String fullQualifiedClassName= "seleniumutils.applicationlayer."+ APPLICATION+"."+APPLICATION+"pageobjects." +
                    pageName.toUpperCase();
            /*ToDo Optimize reflection call to identify page object class and initiate its elements*/
            try {
                Class pageClass= Class.forName(fullQualifiedClassName);
                page=(PageObjectInterface) pageClass.getConstructor().newInstance();
                Method initElements = page.getClass().getDeclaredMethod("initializeElements");
                initElements.invoke(page);
            } catch (Exception e) {
                throw new TestCaseFailed("Unable to create Page Object or call initElements method. Please debug");
            }
        return page;
    }

    public static void main (String[] args){
        try {
            PageObjectCreator.createPageObject("LOGIN");
        } catch (TestCaseFailed testCaseFailed) {
            testCaseFailed.printStackTrace();
        }
    }
}
