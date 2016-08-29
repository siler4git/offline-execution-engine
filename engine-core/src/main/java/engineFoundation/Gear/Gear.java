package engineFoundation.Gear;


import engineFoundation.ApplicationContext;
import engineFoundation.Assert.TestFail;
import engineFoundation.Assert.TestPass;
import engineFoundation.Assert.TestResult;
import engineFoundation.Flow.Flow;
import leo.carnival.MyArrayUtils;
import leo.carnival.workers.implementation.GearicUtils.DeepCloner;
import leo.carnival.workers.prototype.Executor;

import static leo.carnival.workers.implementation.GearicUtils.ClassUtils.loadClass;

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
            flow.execute(DeepCloner.process(applicationContext, ApplicationContext.class));
            return new TestPass();
        } catch (Exception e) {
            return new TestFail(e);
        }
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
