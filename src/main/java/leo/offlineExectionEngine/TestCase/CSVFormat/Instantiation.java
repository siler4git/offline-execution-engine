package leo.offlineExectionEngine.TestCase.CSVFormat;

@SuppressWarnings("unused")
public interface Instantiation<T>
{
    T gen(String str) throws Exception;
}
