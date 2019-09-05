package seleniumutils.methods.pageobjectmethods;

import seleniumutils.methods.ElementObject;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.PageObjectGenerator;

import java.util.HashMap;
import java.util.Map;

import static seleniumutils.methods.GlobalProperties.APPLICATION_PAGE_OBJECTS_PATH;
//TODO - Still in development
public class BasePage implements PageObjectInterface {
    /**
     *  Singleton instance
     */
    protected static BasePage page;

    public static BasePage getPage() {
        if (page == null) {
            page = new BasePage();
        }
        return page;
    }

    @Override
    public void initializeElements() {
        System.out.println("\n\ninitialized Activities page elements\n\n");
        HashMap<String, ElementObject> currentPageObjects= (HashMap<String, ElementObject>) PageObjectGenerator.getPageObjects(APPLICATION_PAGE_OBJECTS_PATH).get(this.getClass().getSimpleName().toUpperCase());
        currentPageObjects.entrySet().parallelStream().forEach((Map.Entry<String, ElementObject> entry) ->{
            try {
                if(entry.getValue() == null)
                    throw new TestCaseFailed("Malformed pageObjects excel sheet please debug");
                else{
                    ElementObject elemObj = entry.getValue();
                    elemObj.decideTypeAndAccessName();
                    if(!elemObj.getAccessType().equalsIgnoreCase("value"))
                        elemObj.locateElement(elemObj.getAccessType(),elemObj.getAccessName());
                }
            } catch (TestCaseFailed testCaseFailed) {
                testCaseFailed.printStackTrace();
            }

        });
    }
    @Override
    public void fillPage(Map<String, Object> inputMap) {

    }

    @Override
    public void validatePage(Map<String, Object> inputMap) {

    }

    @Override
    public void navigateTo(PageObjectInterface page) throws Exception {

    }
}
