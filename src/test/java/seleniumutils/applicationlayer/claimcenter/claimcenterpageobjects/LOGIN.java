package seleniumutils.applicationlayer.claimcenter.claimcenterpageobjects;

import seleniumutils.methods.pageobjectmethods.BasePage;
import seleniumutils.methods.pageobjectmethods.PageObjectInterface;

import java.util.Map;

public class LOGIN extends BasePage {
    protected static LOGIN page;

    public static LOGIN getPage() {
        if (page == null) {
            page = new LOGIN();
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
