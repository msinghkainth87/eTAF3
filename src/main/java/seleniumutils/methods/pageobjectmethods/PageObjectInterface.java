package seleniumutils.methods.pageobjectmethods;

import java.util.Map;


public interface PageObjectInterface {
    void initializeElements();
    void fillPage(Map<String,Object> inputMap);
    void validatePage(Map<String,Object> inputMap);
    void navigateTo(PageObjectInterface page) throws Exception;

}
