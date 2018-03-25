package test.selenium;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertTrue;

//@Category(SeleniumQAIT.class)
public class SeleniumContextualImagesAdminQAIT {

    @Rule
    public TestName name = new TestName();

    private static final transient Log LOG = LogFactory.getLog(SeleniumContextualImagesAdminQAIT.class.getName());

    @Before
    public void setup() {

        try {
            Boolean foundLogoutElement = Pages.Browser.findLogoutElement();

            if (foundLogoutElement) {
                Pages.Browser.findLogoutElementAndClick();
            }
        } catch (Exception e) {
            LOG.debug(" caught exception, don't need to log out ");
        }
    }

    @Test
    public void testVisitAssetsPage() {

        LOG.info(" starting test case " + name.getMethodName());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Pages.HomePage.goToAsAdmin();
        assertTrue(Pages.HomePage.isAt());
        Pages.ContextualImagesAdminAssetsPage.goTo();
        assertTrue(Pages.ContextualImagesAdminAssetsPage.isAt());
        Pages.Browser.logoutAsAdmin();
        stopWatch.stop();
        LOG.debug(" spent time on the test case: " + stopWatch.toString());
        LOG.info(" finishing test case " + name.getMethodName());
    }
}
