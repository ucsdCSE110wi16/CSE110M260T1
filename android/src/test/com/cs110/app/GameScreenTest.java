package test.com.cs110.app;

import com.cs110.app.AndroidLauncher;
import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

import dalvik.annotation.TestTarget;

/**
 * Created by caeleanb on 3/10/2016.
 */
public class GameScreenTest  extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.cs110.app.AndroidLauncher";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public GameScreenTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        System.out.println("Here");
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        System.out.println("Here");
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testActivity() {
        solo.assertCurrentActivity("Current Activity", AndroidLauncher.class);
        //solo.clickOnScreen(600, 550);
        int height = getActivity().getResources().getDisplayMetrics().heightPixels;
        int width = getActivity().getResources().getDisplayMetrics().widthPixels;

        solo.sleep(5000);
        solo.clickOnScreen(1150, 900);
        solo.sleep(9000);
        solo.clickOnScreen(1400, 900);
        solo.sleep(9000);
        solo.clickOnScreen(1625, 900);
        solo.sleep(9000);
        solo.clickLongOnScreen(145, 913, 20000);
        solo.sleep(9000);

//        solo.clickOnScreen(1200, 800);
//        solo.sleep(200);
    }

//    public void test
//    public void testRun() {
//        //Wait for activity: 'com.example.ExampleActivty'
//        solo.waitForActivity("Android Launcher", 2000);
//        //Clear the EditText editText1
//        solo.clearEditText((android.widget.EditText) solo.getView("editText1"));
//        solo.enterText((android.widget.EditText) solo.getView("editText1"), "This is an example text");
//    }
}
