package leo.engineCore.engineFoundation.Gear;


import leo.engineCore.engineFoundation.ApplicationContext;
import leo.engineCore.engineFoundation.Assert.TestFail;
import leo.engineCore.engineFoundation.Assert.TestPass;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.engineFoundation.Flow.Flow;
import leo.carnival.MyArrayUtils;
import leo.carnival.workers.impl.GearicUtils.DeepClone;
import leo.carnival.workers.prototype.Executor;

import static leo.carnival.workers.impl.GearicUtils.ClassUtils.loadClass;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Gear implements Executor<String, TestResult> {
    private String sourceClass;
    private String name;
    private Flow[] flows;

    private ApplicationContext applicationContext = new ApplicationContext();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public TestResult execute(String flowName) {
        applicationContext.setGearName(name);
        try {
            applicationContext.setMethodRepo(loadClass(sourceClass));
            Flow flow = MyArrayUtils.searchArray(flows, flowName);
            flow.execute(applicationContext);
            return new TestPass();
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    /**
     * Not update application context
     */
    public TestResult executeQuietly(String flowName) {
        applicationContext.setGearName(name);
        try {
            if (applicationContext.getMethodRepo() == null || applicationContext.getMethodRepo().isEmpty())
                applicationContext.setMethodRepo(loadClass(sourceClass));
            Flow flow = MyArrayUtils.searchArray(flows, flowName);
            flow.execute(new DeepClone<ApplicationContext>().process(applicationContext));
            return new TestPass();
        } catch (Exception e) {
            return new TestFail(e);
        }
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}