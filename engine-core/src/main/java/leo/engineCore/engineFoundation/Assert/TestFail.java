package leo.engineCore.engineFoundation.Assert;

import com.google.common.base.Throwables;
import org.testng.Assert;

@SuppressWarnings("unused")
public class TestFail extends TestResult {
    private Exception e;

    public TestFail(Exception e) {
        super("", "", "");
        this.e = e;
    }

    public TestFail(String message){
        super("","","");
        e = new Exception(message);
    }

    @Override
    public void Assert() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test fail with reason: \r\n");
        Assert.fail(sb.append(Throwables.getStackTraceAsString(e)).toString());
    }
}