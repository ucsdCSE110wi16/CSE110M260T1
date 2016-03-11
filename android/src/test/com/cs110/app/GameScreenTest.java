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

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "/Users/caeleanbarnes/Code/CSE110/test/CSE110M260T1/core/src/com/cs110/app/Screens/MenuScreen.java";

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
