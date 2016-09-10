package leo.engineData.testData;


import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class TestDataImpl<T, G> implements TestData<T, G> {

    @Override
    public TestData update(Map<String, Object> profile) {
        return (TestData) InstanceUpdater.build().setWorker(MapValueUpdater.build(profile)).process(this);
    }

}