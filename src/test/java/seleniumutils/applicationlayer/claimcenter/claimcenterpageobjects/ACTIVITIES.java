package seleniumutils.applicationlayer.claimcenter.claimcenterpageobjects;

import seleniumutils.methods.ElementObject;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.PageObjectGenerator;
import seleniumutils.methods.pageobjectmethods.BasePage;
import seleniumutils.methods.pageobjectmethods.PageObjectInterface;

import java.util.HashMap;
import java.util.Map;

import static seleniumutils.methods.GlobalProperties.APPLICATION_PAGE_OBJECTS_PATH;

public class ACTIVITIES extends BasePage {
    protected static ACTIVITIES page;

    public static ACTIVITIES getPage() {
        if (page == null) {
            page = new ACTIVITIES();
        }
        return page;
    }

    @Override
    public void initializeElements() {
        super.initializeElements();
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
